package com.kropkigame.controller;

import java.util.*;

import com.kropkigame.model.EdgePoint;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

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

/**
 * La classe GameBoardController est responsable de contrôler le plateau de jeu et de gérer les interactions utilisateur.
 * Elle gère le modèle, la vue et le contrôleur de cellule du plateau de jeu, notamment au niveau du respect des règles du jeu.
 */
public class GameBoardController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;

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
     * Construit un contrôleur de plateau de jeu avec le modèle, la vue et le contrôleur de cellule spécifiés.
     * @param model le modèle du plateau de jeu.
     * @param view la vue du plateau de jeu.
     * @param cellController le contrôleur de cellule du plateau de jeu.
     */
    public GameBoardController(Puzzle model, GameBoardPanel view, CellController cellController) {
        this.model = model;
        this.view = view;
        this.cellController = cellController;
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

        // Parcours de chaque bouton de nombre
        for (int i = 1; i <= model.getGridSize(); i++) {
            final int number = i;
            Button numberButton = (Button) view.lookup("#numberButton" + number);
            if (numberButton != null) {
                // Attachement du gestionnaire d'événements gérant le fait de rentrer un nombre dans la cellule sélectionnée (clic)
                numberButton.setOnAction(event -> handleNumberButtonClicked(number));
            }
        }

        // Configuration du switch d'aide
        view.getHelpSwitch().setOnMouseClicked(e -> {
            view.getHelpSwitch().setValue(!(view.getHelpSwitch().getValue()));
            view.getHelpSwitch().paintSwitch();
        });
        
        view.getHelpSwitch().paintSwitch();

    }

    /**
     * Gère l'événement lorsqu'un bouton de numéro est cliqué.
     * @param number le numéro du bouton cliqué.
     */
    public void handleNumberButtonClicked(int number) {
        // Délégation du clic sur le bouton de nombre au CellController
        cellController.handleNumberButtonClicked(number);

        // Vérification des erreurs
        highlightErrors();
        
        if (view.getHelpSwitch().getValue()) {
            provideHelp();
        }

        if (isGridFull()) {
            if (checkGameStatus()) {
                showVictoryMessage();
            }
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
     * Fournit une aide en remplissant automatiquement certaines cases de la grille,
     * en fonction des règles de Kropki.
     */
    public void provideHelp() {
        int gridSize = model.getGridSize();
        boolean filledACell;
    
        do {
            filledACell = false; // Réinitialiser pour chaque itération
    
            // Obtenir la cellule sélectionnée par le joueur
            Cell selectedCell = cellController.getSelectedCell();
            if (selectedCell != null && selectedCell.getNumber() != 0) {
                int selectedRow = selectedCell.getRow();
                int selectedCol = selectedCell.getCol();
    
                // Utiliser la valeur de la cellule sélectionnée pour remplir les cellules adjacentes
                filledACell = fillAdjacentCellsBasedOnPoints(selectedRow, selectedCol);
            }
    
            // Parcourir toutes les cellules pour la logique de remplissage des lignes / colonnes
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    Cell thisCell = view.getCell(row, col);
    
                    // Si thisCell est vide, vérifier si une valeur manquante peut être déterminée
                    if (thisCell.getNumber() == 0) {
                        int missingValue = determineMissingValue(row, col);
                        if (missingValue > 0) {
                            thisCell.setNumber(missingValue); // Remplir la case avec la valeur manquante
                            filledACell = true; // Marquer qu'une cellule a été remplie
                        }
                    }
                }
            }
        } while (filledACell); // Continuer tant qu'au moins une case est remplie à chaque itération
    }
    
    /**
     * Remplit les cellules adjacentes à la cellule spécifiée en fonction des points adjacents.
     * @param row
     * @param col
     * @return true si au moins une cellule a été remplie, false sinon
     */
    private boolean fillAdjacentCellsBasedOnPoints(int row, int col) {
        int gridSize = model.getGridSize();
        boolean filled = false;
    
        // Parcourir les cellules adjacentes
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if ((dRow == 0) != (dCol == 0)) { // Ignorer la cellule elle-même et les diagonales
                    int adjacentRow = row + dRow;
                    int adjacentCol = col + dCol;
    
                    if (adjacentRow >= 0 && adjacentRow < gridSize && adjacentCol >= 0 && adjacentCol < gridSize) {
                        int existingValue = view.getCell(adjacentRow, adjacentCol).getNumber();
                        int valueFromPoints = determineValueFromPoints(row, col, adjacentRow, adjacentCol);
    
                        if (valueFromPoints > 0 && existingValue != valueFromPoints) {
                            view.getCell(adjacentRow, adjacentCol).setNumber(valueFromPoints);
                            filled = true;
                        }
                    }
                }
            }
        }
    
        return filled;
    }

    /**
     * Détermine la valeur d'une cellule en fonction des points adjacents.
     * @param row
     * @param col
     * @return la valeur de la cellule
     */
    private int determineValueFromPoints(int row, int col, int adjacentRow, int adjacentCol) {
        int gridSize = model.getGridSize();
        int currentValue = view.getCell(row, col).getNumber();
        int cellRow = row + 1;
        int cellCol = col + 1;
        int adjacentCellRow = adjacentRow + 1;
        int adjacentCellCol = adjacentCol + 1;
    
        // Logique pour un point noir
        if (model.existsBlackEdgePoint(cellRow, cellCol, adjacentCellRow, adjacentCellCol) || model.existsBlackEdgePoint(adjacentCellRow, adjacentCellCol, cellRow, cellCol)) {
            if (currentValue == 1) {
                return 2;
            } else if (currentValue == gridSize) {
                return gridSize / 2;
            }
        }
        // Logique pour un point blanc
        else if (model.existsWhiteEdgePoint(cellRow, cellCol, adjacentCellRow, adjacentCellCol) || model.existsWhiteEdgePoint(adjacentCellRow, adjacentCellCol, cellRow, cellCol)) {
            if (currentValue == 1) {
                return 2;
            } else if (currentValue == gridSize) {
                return gridSize - 1;
            }
        }
    
        return 0; // Retourner 0 si aucune valeur déterminée
    }

    
    /**
     * Détermine la valeur d'une cellule en fonction des nombres dans la ligne et la colonne.
     * @param row
     * @param col
     * @return la valeur de la cellule
     */
    private int determineMissingValue(int row, int col) {
        int size = model.getGridSize();
        int missingValueForRow = size * (size + 1) / 2; // La somme de tous les nombres attendus dans une ligne
        int missingValueForColumn = missingValueForRow; // La somme de tous les nombres attendus dans une colonne
        int filledCellsInRow = 0; // Compteur pour le nombre de cellules remplies dans la ligne
        int filledCellsInColumn = 0; // Compteur pour le nombre de cellules remplies dans la colonne

        for (int i = 0; i < size; i++) {
            if (view.getCell(row, i).getNumber() != 0) {
                missingValueForRow -= view.getCell(row, i).getNumber(); // Soustraire les valeurs de la ligne
                filledCellsInRow++;
            }
            if (view.getCell(i, col).getNumber() != 0) {
                missingValueForColumn -= view.getCell(i, col).getNumber(); // Soustraire les valeurs de la colonne
                filledCellsInColumn++;
            }
        }

        // Compléter la ligne ou la colonne seulement s'il y a "gridSize - 1" chiffres rentrés
        if (filledCellsInRow == size - 1 && missingValueForRow > 0 && missingValueForRow <= size) {
            return missingValueForRow;
        } else if (filledCellsInColumn == size - 1 && missingValueForColumn > 0 && missingValueForColumn <= size) {
            return missingValueForColumn;
        }

        return 0; // Retourner 0 si aucune valeur déterminée
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
     * Réinitialise le jeu.
     */
    public void resetGame() {
        // Réinitialise le modèle pour commencer une nouvelle partie
        // Met à jour la vue pour afficher le jeu réinitialisé
        initializeGameBoard();
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
     * Efface les points du plateau de jeu.
     */
    public void clearEdgePoints() {
        // Supprime tous les points de la vue
        view.getChildren().removeIf(node -> node instanceof Circle);
    }    

    /**
     * Affiche un message de victoire lorsque la grille est correcte.
     */
    public void showVictoryMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Vous avez résolu le puzzle.");

        alert.showAndWait();
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
            Set<Integer> rowValues = new HashSet<>();
            Set<Integer> colValues = new HashSet<>();
            for (int j = 0; j < gridSize && !foundError; j++) {
                int rowValue = view.getCell(i, j).getNumber();
                int colValue = view.getCell(j, i).getNumber();

                if (rowValue != 0 && !rowValues.add(rowValue)) {
                    rowErrors[i][j] = true;
                    foundError = true;
                }
                if (colValue != 0 && !colValues.add(colValue)) {
                    colErrors[j][i] = true;
                    foundError = true;
                }
            }
        }
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
    
        // Parcourir toutes les cellules de la grille
        for (int row = 0; row < gridSize && !foundError; row++) {
            for (int col = 0; col < gridSize && !foundError; col++) {
                Cell cell = view.getCell(row, col);
                int cellValue = cell.getNumber();
    
                // Parcourir les cellules adjacentes pour vérifier les règles de points noirs et blancs
                for (int[] direction : new int[][]{{0, 1}, {1, 0}}) { // Représente les directions droite et bas
                    int adjacentRow = row + direction[0];
                    int adjacentCol = col + direction[1];
    
                    if (adjacentRow < gridSize && adjacentCol < gridSize) {
                        Cell adjacentCell = view.getCell(adjacentRow, adjacentCol);
                        int adjacentCellValue = adjacentCell.getNumber();
    
                        if (cellValue > 0 && adjacentCellValue > 0) {
                            if (model.existsWhiteEdgePoint(row + 1, col + 1, adjacentRow + 1, adjacentCol + 1)) {
                                // Vérifier la règle du point blanc (les chiffres doivent être consécutifs)
                                if (Math.abs(cellValue - adjacentCellValue) != 1) {
                                    pointErrors[row][col] = true;
                                    pointErrors[adjacentRow][adjacentCol] = true;
                                    foundError = true;
                                    break;
                                }
                            } else if (model.existsBlackEdgePoint(row + 1, col + 1, adjacentRow + 1, adjacentCol + 1)) {
                                // Vérifier la règle du point noir (un chiffre doit être le double de l'autre)
                                if (!(cellValue == 2 * adjacentCellValue || adjacentCellValue == 2 * cellValue)) {
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
                    if (pointErrors[row][col]) {
                        // Mettre en évidence les deux cellules séparées par un point
                        view.getCell(row, col).setStyle(KropkiConstants.CELL_ERROR_STYLE);

                        // Supposons que pointErrors contient déjà la paire de cellules erronées
                        for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                            int adjRow = row + direction[0];
                            int adjCol = col + direction[1];
                            if (adjRow >= 0 && adjRow < gridSize && adjCol >= 0 && adjCol < gridSize && pointErrors[adjRow][adjCol]) {

                                if(view.getCell(adjRow, adjCol).equals(cellController.getSelectedCell())) {
                                    view.getCell(adjRow, adjCol).setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
                                } else {
                                    view.getCell(adjRow, adjCol).setStyle(KropkiConstants.CELL_ERROR_STYLE);
                                }

                                view.getCell(adjRow, adjCol).setIsError(true);
                                errorHighlighted = true;
                                break; // Sortir de la boucle dès la première erreur trouvée
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
}

