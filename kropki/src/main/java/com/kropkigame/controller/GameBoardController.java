package com.kropkigame.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import com.kropkigame.bot.BotSolver;
import com.kropkigame.bot.BotSolverImpl;
import com.kropkigame.help.GameHelper;
import com.kropkigame.help.GameHelperImpl;
import com.kropkigame.model.EdgePoint;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.utils.Action;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * La classe GameBoardController est responsable de contrôler le plateau de jeu et de gérer les interactions utilisateur.
 * Elle gère le modèle, la vue et le contrôleur de cellule du plateau de jeu, notamment au niveau du respect des règles du jeu.
 */
public class GameBoardController implements GameBoardActions {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;
    private Stack<Action> actions = new Stack<>();
    private Duration time = Duration.ZERO;
    private Timeline timeline;
    private BotSolver botSolver;
    private GameHelper gameHelper;

    /**
     * Obtient le modèle du plateau de jeu.
     * @return le modèle du plateau de jeu.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Définit le modèle du plateau de jeu.
     * @param model le modèle du plateau de jeu.
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Obtient la vue du plateau de jeu.
     * @return la vue du plateau de jeu.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Définit la vue du plateau de jeu.
     * @param view la vue du plateau de jeu.
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Obtient le contrôleur de cellule du plateau de jeu.
     * @return le contrôleur de cellule du plateau de jeu.
     */
    public CellController getCellController() {
        return this.cellController;
    }

    /**
     * Définit le contrôleur de cellule du plateau de jeu.
     * @param cellController le contrôleur de cellule du plateau de jeu.
     */
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    /**
     * Obtient la pile d'actions de l'utilisateur.
     * @return la pile d'actions de l'utilisateur.
     */
    public Stack<Action> getActions() {
        return this.actions;
    }

    /**
     * Définit la pile d'actions de l'utilisateur.
     * @param actions la pile d'actions de l'utilisateur.
     */
    public void setActions(Stack<Action> actions) {
        this.actions = actions;
    }

    /**
     * Obtient le chronomètre.
     * @return le chronomètre.
     */
    public Duration getTime() {
        return this.time;
    }

    /**
     * Définit le chronomètre.
     * @param time le chronomètre.
     */
    public void setTime(Duration time) {
        this.time = time;
    }

    /**
     * Obtient la timeline.
     * @return la timeline.
     */
    public Timeline getTimeline() {
        return this.timeline;
    }

    /**
     * Définit la timeline.
     * @param timeline la timeline.
     */
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    /**
     * Obtient le bot résolveur de jeu.
     * @return le bot résolveur de jeu.
     */
    public BotSolver getBotSolver() {
        return this.botSolver;
    }

    /**
     * Définit le bot résolveur de jeu.
     * @param botSolver le bot résolveur de jeu.
     */
    public void setBotSolver(BotSolver botSolver) {
        this.botSolver = botSolver;
    }

    /**
     * Obtient l'aide de jeu.
     * @return l'aide de jeu.
     */
    public GameHelper getGameHelper() {
        return this.gameHelper;
    }

    /**
     * Définit l'aide de jeu.
     * @param gameHelper l'aide de jeu.
     */
    public void setGameHelper(GameHelper gameHelper) {
        this.gameHelper = gameHelper;
    }

    /**
     * Construit un contrôleur de plateau de jeu avec le modèle, la vue et le contrôleur de cellule spécifiés.
     * @param model le modèle du plateau de jeu.
     * @param view la vue du plateau de jeu.
     * @param cellController le contrôleur de cellule du plateau de jeu.
     */
    public GameBoardController(Puzzle model, GameBoardPanel view, CellController cellController) {
        this.model = model;
        this.view = view;
        this.cellController = cellController;
        this.actions = new Stack<>();
        this.time = Duration.ZERO;
        this.timeline = null;
        this.botSolver = new BotSolverImpl(this);
        this.gameHelper = new GameHelperImpl(this);
    }

    /**
     * Initialise le plateau de jeu.
     */
    public void initializeGameBoard() {
        // Parcours de chaque cellule du plateau de jeu
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                // Configuration de l'apparence et des propriétés de la cellule
                cell.setNumber(0);
                cell.getTextDisplay().setText("");
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCellSelection(event, cell));
            }
        }

        // Configuration des boutons de nombre
        for (int i = 1; i <= model.getGridSize(); i++) {
            final int number = i;
            Button numberButton = (Button) view.lookup("#numberButton" + number);
            if (numberButton != null) {
                numberButton.setOnAction(event -> handleNumberButtonClicked(number));
                numberButton.setOnMouseEntered(e -> numberButton.setStyle(KropkiConstants.NUMBER_BUTTON_HOVER_STYLE));
                numberButton.setOnMouseExited(e -> numberButton.setStyle(KropkiConstants.NUMBER_BUTTON_STYLE));
            }
        }

        // Configuration du chronomètre
        this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        // Configuration du bouton de réinitialisation
        view.getResetButton().setOnAction(event -> resetGame());

        // Configuration du bouton de retour
        view.getBackButton().setOnAction(event -> handleBackButton());

        // Configuration du switch d'aide
        view.getHelpSwitch().setOnMouseClicked(e -> {
            boolean isHelpActive = view.getHelpSwitch().getValue();
            view.getHelpSwitch().setValue(!isHelpActive);
            toggleHelp(!isHelpActive);
            view.getHelpSwitch().paintHelpSwitch();
        });
        
        view.getHelpSwitch().paintHelpSwitch();

        // Configuration du switch du bot
        view.getBotSwitch().setOnMouseClicked(e -> {
            boolean isBotActive = view.getBotSwitch().getValue();
            view.getBotSwitch().setValue(!isBotActive);
            toggleBot(!isBotActive);
            view.getBotSwitch().paintBotSwitch();
        });

        view.getBotSwitch().paintBotSwitch();
    }

    /**
     * Dessine les points sur la grille de jeu.
     * @param edgePoints
     */
    public void drawEdgePoints(ArrayList<EdgePoint> edgePoints) {
        Platform.runLater(() -> {
            double radius = 7; // Définit la taille des points
            clearEdgePoints(); // Efface les anciens points (cas de redimensionnement de la fenêtre)
            
            for (EdgePoint edgePoint : edgePoints) {
                int sourceRow = edgePoint.getSourceRow()-1;
                int sourceCol = edgePoint.getSourceCol()-1;
                int targetRow = edgePoint.getTargetRow()-1;
                int targetCol = edgePoint.getTargetCol()-1;

                Cell sourceCell = view.getCell(sourceRow, sourceCol);
                Cell targetCell = view.getCell(targetRow, targetCol);

                Bounds sourceBounds = sourceCell.localToScene(sourceCell.getBoundsInLocal());
                Bounds targetBounds = targetCell.localToScene(targetCell.getBoundsInLocal());

                double x1 = (sourceBounds.getMinX() + sourceBounds.getMaxX()) / 2;
                double y1 = (sourceBounds.getMinY() + sourceBounds.getMaxY()) / 2;
                double x2 = (targetBounds.getMinX() + targetBounds.getMaxX()) / 2;
                double y2 = (targetBounds.getMinY() + targetBounds.getMaxY()) / 2;

                double centerX = (x1 + x2) / 2;
                double centerY = (y1 + y2) / 2;

                Circle point = new Circle(centerX, centerY, radius);

                if (edgePoint.getType().equals("black")) {
                    point.setFill(Color.BLACK); // Couleur de remplissage du point
                    point.setStroke(Color.WHITE); // Couleur de bordure du point
                } else if (edgePoint.getType().equals("white")) {
                    point.setFill(Color.WHITE);
                    point.setStroke(Color.BLACK);
                }

                // Ajouter le point (objet Circle) à la vue
                view.getChildren().add(point);
            }
        });
    }

    /**
     * Démarre ou arrête le bot en fonction de l'état du switch.
     * @param isBotActive vrai si le bot est actif, faux sinon. 
     */
    public void toggleBot(boolean isBotActive) {
        if (isBotActive) {
            view.disableUserInteraction();
            botSolver.startBot();
        } else {
            view.enableUserInteraction();
            botSolver.stopBot();
        }
        view.getBotSwitch().setValue(isBotActive);
        view.getBotSwitch().paintBotSwitch();
    }

    /**
     * Démarre ou arrête l'aide en fonction de l'état du switch.
     * @param isBotActive vrai si l'aide est active, faux sinon. 
     */
    public void toggleHelp(boolean isHelpActive) {
        if (isHelpActive) {
            view.disableBotInteraction();
        } else {
            view.enableBotInteraction();
        }
        view.getHelpSwitch().setValue(isHelpActive);
        view.getHelpSwitch().paintHelpSwitch();
    }

    /**
     * Gère l'événement lorsque l'on clique sur un bouton de numéro.
     * @param number le numéro du bouton cliqué.
     */
    public void handleNumberButtonClicked(int number) {
        // Délégation du clic sur le bouton de nombre au CellController
        cellController.handleNumberButtonClicked(number);

        // Vérification des erreurs
        highlightErrors();

        if (view.getHelpSwitch().getValue()) {
            gameHelper.provideHelp();
        }

        if (isGridFull()) {
            if (checkGameStatus()) {
                if (timeline != null) {
                    timeline.stop();
                }
                showVictoryMessage();
            }
        }

        // Enregistrement de l'action effectuée par l'utilisateur
        Cell selectedCell = cellController.getSelectedCell();
        if (selectedCell != null) {
            recordAction(selectedCell.getRow(), selectedCell.getCol(), number);
        }
    }

    /**
     * Gère l'événement lorsqu'une cellule est sélectionnée.
     * @param event l'événement de sélection de la cellule.
     * @param cell la cellule sélectionnée.
     */
    public void handleCellSelection(MouseEvent event, Cell cell) {
        // Délégation de la sélection de la cellule au CellController
        cellController.handleCellSelected(event, cell);
    }

    /**
     * Efface les points du plateau de jeu.
     */
    public void clearEdgePoints() {
        // Supprime tous les points de la vue
        view.getChildren().removeIf(node -> node instanceof Circle);
    }   

    /**
     * Met à jour le chronomètre.
     */
    private void updateTimer() {
        time = time.add(Duration.seconds(1));
        view.getTimerLabel().setText(formatDuration(time));
    }

    /**
     * Permet de formater le temps écoulé.
     * @param duration le temps à formater.
     * @return le temps formaté.
     */    
    private String formatDuration(Duration duration) {
        long seconds = (long) duration.toSeconds();
        return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);
    }

    /**
     * Vérifie si le jeu est gagné.
     * @return vrai si le jeu est gagné, faux sinon.
     */
    public boolean checkGameStatus() {
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                int number = cell.getNumber();
    
                if (number != model.getNumber(row, col)) {
                    return false; // Si une cellule ne correspond pas, le jeu n'est pas gagné
                }
            }
        }
    
        return true; // Toutes les cellules correspondent, le jeu est gagné
    }

    /**
     * Vérifie si la grille de jeu est entièrement remplie par l'utilisateur.
     * @return vrai si le plateau de jeu est plein, faux sinon.
     */
    public boolean isGridFull() {
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                int number = cell.getNumber();
    
                if (number == 0) {
                    return false; // Si une cellule est vide, la grille n'est pas pleine
                }
            }
        }
    
        return true; // Toutes les cellules sont remplies, la grile est pleine
    }

    /**
     * Affiche un message de victoire lorsque la grille est correcte.
     */
    public void showVictoryMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Vous avez résolu le puzzle en " + formatDuration(time) + ".");
        alert.showAndWait();
    }

    /**
     * Enregistre une action lorsqu'un chiffre est entré.
     * @param row la ligne de la cellule.
     * @param col la colonne de la cellule.
     * @param number le nombre entré.
     */
    public void recordAction(int row, int col, int number) {
        actions.push(new Action(row, col, number));
    }

    /**
     * Ajoute un écouteur de redimensionnement à la scène.
     * @param stage la scène à laquelle ajouter l'écouteur.
     */
    public void addResizeListener(Stage stage) {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            // Dessine à nouveau les points lorsque la scène est redimensionnée
            drawEdgePoints(model.getEdgePoints());
        };

        // Ajoute des écouteurs aux propriétés de largeur et de hauteur de la scène
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    /**
     * Vérifie les doublons et s'arrête à la première erreur trouvée.
     *
     * @param rowErrors Tableau pour stocker les erreurs de ligne.
     * @param colErrors Tableau pour stocker les erreurs de colonne.
     * @return true si une erreur est trouvée, false sinon.
     */
    private boolean checkForDuplicates(boolean[][] rowErrors, boolean[][] colErrors) {
        int gridSize = model.getGridSize();
        boolean foundError = false;
    
        for (int i = 0; i < gridSize && !foundError; i++) {
            // Crée des ensembles pour suivre les valeurs uniques dans la ligne et la colonne actuelles
            Set<Integer> rowValues = new HashSet<>();
            Set<Integer> colValues = new HashSet<>();
            for (int j = 0; j < gridSize && !foundError; j++) {
                int rowValue = view.getCell(i, j).getNumber();
                int colValue = view.getCell(j, i).getNumber();
    
                // Si la valeur de la ligne n'est pas 0 et qu'elle est déjà présente dans l'ensemble des valeurs de la ligne, on signale l'erreur
                if (rowValue != 0 && !rowValues.add(rowValue)) {
                    rowErrors[i][j] = true;
                    foundError = true;
                }
                // Si la valeur de la colonne n'est pas 0 et qu'elle est déjà présente dans l'ensemble des valeurs de la colonne, on signale l'erreur
                if (colValue != 0 && !colValues.add(colValue)) {
                    colErrors[j][i] = true;
                    foundError = true;
                }
            }
        }
        // Retourne si une erreur a été trouvée
        return foundError;
    }

    /**
     * Vérifie les erreurs liées aux points noirs et blancs et s'arrête à la première erreur trouvée.
     *
     * @param pointErrors Tableau pour stocker les erreurs de points.
     * @return true si une erreur est trouvée, false sinon.
     */
    private boolean checkForPointErrors(boolean[][] pointErrors) {
        int gridSize = model.getGridSize();
        boolean foundError = false;
    
        for (int row = 0; row < gridSize && !foundError; row++) {
            for (int col = 0; col < gridSize && !foundError; col++) {
                // Récupère la cellule et sa valeur à la position (row, col)
                Cell cell = view.getCell(row, col);
                int cellValue = cell.getNumber();
    
                // Parcourt chaque direction à partir de la cellule actuelle (droite, bas, gauche, haut)
                for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                    // Calcule les coordonnées de la cellule adjacente
                    int adjacentRow = row + direction[0];
                    int adjacentCol = col + direction[1];
    
                    // Vérifie si la cellule adjacente est dans la grille
                    if (adjacentRow >= 0 && adjacentRow < gridSize && adjacentCol >= 0 && adjacentCol < gridSize) {
                        // Récupère la cellule adjacente et sa valeur
                        Cell adjacentCell = view.getCell(adjacentRow, adjacentCol);
                        int adjacentCellValue = adjacentCell.getNumber();
    
                        if (cellValue > 0 && adjacentCellValue > 0) {
                            // Vérifie si un point blanc ou noir existe entre les deux cellules
                            boolean hasWhitePoint = model.existsWhiteEdgePoint(row + 1, col + 1, adjacentRow + 1, adjacentCol + 1)
                                    || model.existsWhiteEdgePoint(adjacentRow + 1, adjacentCol + 1, row + 1, col + 1);
                            boolean hasBlackPoint = model.existsBlackEdgePoint(row + 1, col + 1, adjacentRow + 1, adjacentCol + 1)
                                    || model.existsBlackEdgePoint(adjacentRow + 1, adjacentCol + 1, row + 1, col + 1);
    
                            // Si un point blanc existe et que les valeurs des cellules ne sont pas consécutives, on signale l'erreur
                            if (hasWhitePoint && Math.abs(cellValue - adjacentCellValue) != 1) {
                                pointErrors[row][col] = true;
                                pointErrors[adjacentRow][adjacentCol] = true;
                                foundError = true;
                                break;
                            }
                            // Si un point noir existe et que l'une des valeurs n'est pas le double de l'autre, on signale l'erreur
                            else if (hasBlackPoint && !(cellValue == 2 * adjacentCellValue || adjacentCellValue == 2 * cellValue)) {
                                pointErrors[row][col] = true;
                                pointErrors[adjacentRow][adjacentCol] = true;
                                foundError = true;
                                break;
                            }
    
                            // Si aucune règle de point n'est applicable mais que les valeurs des cellules appliquent une règle 
                            // liée aux points noirs ou blancs, on signale l'erreur
                            if (!hasWhitePoint && !hasBlackPoint) {
                                if (Math.abs(cellValue - adjacentCellValue) == 1 || cellValue == 2 * adjacentCellValue || adjacentCellValue == 2 * cellValue) {
                                    pointErrors[row][col] = true;
                                    pointErrors[adjacentRow][adjacentCol] = true;
                                    foundError = true;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return foundError;
    } 
    
    /**
     * Met en évidence la première erreur trouvée dans la ligne ou la colonne entière.
     *
     * @param rowErrors Tableau pour stocker les erreurs de ligne.
     * @param colErrors Tableau pour stocker les erreurs de colonne.
     * @param pointErrors Tableau pour stocker les erreurs de points.
     */
    private void highlightFirstError(boolean[][] rowErrors, boolean[][] colErrors, boolean[][] pointErrors) {
        int gridSize = model.getGridSize();
        boolean errorHighlighted = false;
    
        // Vérifier et mettre en évidence les erreurs de doublons dans les lignes
        for (int row = 0; row < gridSize && !errorHighlighted; row++) {
            for (int col = 0; col < gridSize; col++) {
                if (rowErrors[row][col]) {
                    // Mettre en évidence toute la ligne
                    for (int c = 0; c < gridSize; c++) {

                        if (view.getCell(row, c).equals(cellController.getSelectedCell())) {
                            view.getCell(row, c).setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
                        } else {
                            view.getCell(row, c).setStyle(KropkiConstants.CELL_ERROR_STYLE);
                        }

                        view.getCell(row, c).setIsError(true);
                    }
                    errorHighlighted = true; // Arrêter après avoir mis en évidence la première erreur
                    break; // Sortir de la boucle dès la première erreur trouvée
                }
            }
        }
    
        // Vérifier et mettre en évidence les erreurs de doublons dans les colonnes
        for (int col = 0; col < gridSize && !errorHighlighted; col++) {
            for (int row = 0; row < gridSize; row++) {
                if (colErrors[row][col]) {
                    // Mettre en évidence toute la colonne
                    for (int r = 0; r < gridSize; r++) {

                        if (view.getCell(r, col).equals(cellController.getSelectedCell())) {
                            view.getCell(r, col).setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
                        } else {
                            view.getCell(r, col).setStyle(KropkiConstants.CELL_ERROR_STYLE);
                        }

                        view.getCell(r, col).setIsError(true);
                    }
                    errorHighlighted = true; // Arrêter après avoir mis en évidence la première erreur
                    break; // Sortir de la boucle dès la première erreur trouvée
                }
            }
        }
    
        // Vérifier et mettre en évidence les erreurs de points
        if (!errorHighlighted) {
            for (int row = 0; row < gridSize && !errorHighlighted; row++) {
                for (int col = 0; col < gridSize && !errorHighlighted; col++) {

                    // Si une erreur de point a été trouvée à cette position
                    if (pointErrors[row][col]) {

                        // Parcourt chaque direction à partir de la cellule actuelle (droite, bas, gauche, haut)
                        for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                            // Calcule les coordonnées de la cellule adjacente
                            int adjRow = row + direction[0];
                            int adjCol = col + direction[1];

                            // Vérifie si la cellule adjacente est dans la grille et si une erreur liée aux points a été trouvée à cette position
                            if (adjRow >= 0 && adjRow < gridSize && adjCol >= 0 && adjCol < gridSize && pointErrors[adjRow][adjCol]) {
                                Cell cell = view.getCell(row, col);
                                Cell adjacentCell = view.getCell(adjRow, adjCol);

                                if (cell.equals(cellController.getSelectedCell())) {
                                    cell.setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
                                } else {
                                    cell.setStyle(KropkiConstants.CELL_ERROR_STYLE);
                                }

                                cell.setIsError(true);

                                if (adjacentCell.equals(cellController.getSelectedCell())) {
                                    adjacentCell.setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
                                } else {
                                    adjacentCell.setStyle(KropkiConstants.CELL_ERROR_STYLE);
                                }

                                adjacentCell.setIsError(true);

                                // Indique qu'une erreur a été mise en évidence
                                errorHighlighted = true;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Appelée pour vérifier et mettre en évidence les erreurs.
     */
    public void highlightErrors() {
        resetCellStyles(); // Réinitialiser les styles avant de marquer les erreurs
    
        boolean[][] rowErrors = new boolean[model.getGridSize()][model.getGridSize()];
        boolean[][] colErrors = new boolean[model.getGridSize()][model.getGridSize()];
        boolean[][] pointErrors = new boolean[model.getGridSize()][model.getGridSize()];
    
        boolean foundRowOrColError = checkForDuplicates(rowErrors, colErrors);
        boolean foundPointError = checkForPointErrors(pointErrors);
    
        if (foundRowOrColError || foundPointError) {
            highlightFirstError(rowErrors, colErrors, pointErrors);
        } else {
            resetErrorState(); // S'il n'y a plus d'erreurs, réinitialiser l'état d'erreur de toutes les cellules
        }
    }

    /**
     * Réinitialise le style de toutes les cellules.
     */
    private void resetCellStyles() {
        int gridSize = model.getGridSize();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = view.getCell(row, col);
                cell.setIsError(false); // Assurez-vous de réinitialiser l'état d'erreur

                if (!cell.equals(cellController.getSelectedCell())) {
                    cell.setStyle(KropkiConstants.CELL_BORDER_STYLE);
                } else {
                    cell.setStyle(KropkiConstants.CELL_SELECTED_STYLE);
                }            
            }
        }
    }
    
    /**
     * Réinitialise l'état d'erreur de toutes les cellules.
     */
    private void resetErrorState() {
        int gridSize = model.getGridSize();
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = view.getCell(row, col);

                if (!cell.equals(cellController.getSelectedCell())) {
                    cell.setStyle(KropkiConstants.CELL_BORDER_STYLE);
                } else {
                    cell.setStyle(KropkiConstants.CELL_SELECTED_STYLE);
                }
                cell.setIsError(false);
            }
        }
    }

    /**
     * Réinitialise le jeu.
     */
    @Override
    public void resetGame() {
        int gridSize = model.getGridSize();

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell cell = view.getCell(row, col);
                cell.setNumber(0);
                cell.getTextDisplay().setText("");
            }
        }

        // Réinitialise les styles et états des cellules
        resetCellStyles();
        resetErrorState();

        // Réinitialise le chronomètre
        time = Duration.ZERO;
        view.getTimerLabel().setText(formatDuration(time));
        if (timeline != null) {
            timeline.playFromStart();
        }
    }

     /**
     * Gère l'évènement qui permet d'effacer le dernier chiffre entré par l'utilisateur.
     */
    @Override
    public void handleBackButton() {
        if (!actions.isEmpty()) {
            Action lastAction = actions.pop(); // Récupère la dernière action effectuée par l'utilisateur
            Cell cell = view.getCell(lastAction.getRow(), lastAction.getCol());
            cell.setNumber(0);
            cell.getTextDisplay().setText("");
            highlightErrors();
        }
    }
}

