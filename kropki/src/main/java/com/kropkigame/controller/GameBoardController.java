package com.kropkigame.controller;

import java.util.*;

import com.kropkigame.model.EdgePoint;
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
 * The GameBoardController class is responsible for controlling the game board and handling user interactions.
 * It manages the model, view, and cell controller of the game board.
 */
public class GameBoardController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;

    /**
     * Gets the model of the game board.
     * @return the model of the game board.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Sets the model of the game board.
     * @param model the model of the game board.
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Gets the view of the game board.
     * @return the view of the game board.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Sets the view of the game board.
     * @param view the view of the game board.
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Gets the cell controller of the game board.
     * @return the cell controller of the game board.
     */
    public CellController getCellController() {
        return this.cellController;
    }

    /**
     * Sets the cell controller of the game board.
     * @param cellController the cell controller of the game board.
     */
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    /**
     * Constructs a game board controller with the specified model, view, and cell controller.
     * @param model
     * @param view
     * @param cellController
     */
    public GameBoardController(Puzzle model, GameBoardPanel view, CellController cellController) {
        this.model = model;
        this.view = view;
        this.cellController = cellController;
    }

    /**
     * Initializes the game board.
     */
    public void initializeGameBoard() {
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                // Configure the cell's appearance and properties
                cell.setNumber(0);
                cell.getTextDisplay().setText("");
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCellSelection(event, cell));
            }
        }

        for (int i = 1; i <= model.getGridSize(); i++) {
            final int number = i;
            Button numberButton = (Button) view.lookup("#numberButton" + number);
            if (numberButton != null) {
                // Attach the event handler for button click to each number button
                numberButton.setOnAction(event -> handleNumberButtonClicked(number));
            }
        }

        view.getHelpSwitch().setOnMouseClicked(e -> {
            view.getHelpSwitch().setValue(!(view.getHelpSwitch().getValue()));
            view.getHelpSwitch().paintSwitch();
        });
        
        view.getHelpSwitch().paintSwitch();

    }

    /**
     * Handles the event when a number button is clicked.
     * @param number
     */
    public void handleNumberButtonClicked(int number) {
        // Delegate number button click to the CellController
        cellController.handleNumberButtonClicked(number);
        
        if (view.getHelpSwitch().getValue()) {
            provideHelp();
        }

        if (isGridFull()) {
            if (checkGameStatus()) {
                showVictoryMessage();
            } else {
                showIncorrectGridMessage();
            }
        }
    }

    /**
     * Handles the event when a cell is selected.
     * @param event
     * @param cell
     */
    public void handleCellSelection(MouseEvent event, Cell cell) {
        // Delegate cell selection to the CellController
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
    
            // Obtenez la cellule sélectionnée par le joueur
            Cell selectedCell = cellController.getSelectedCell();
            if (selectedCell != null && selectedCell.getNumber() != 0) {
                int selectedRow = selectedCell.getRow();
                int selectedCol = selectedCell.getCol();
    
                // Utiliser la valeur de la cellule sélectionnée pour remplir les cellules adjacentes
                filledACell = fillAdjacentCellsBasedOnPoints(selectedRow, selectedCol);
            }
    
            // Parcourir toutes les cellules pour la logique de determineMissingValue
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    Cell thisCell = view.getCell(row, col);
    
                    // Si thisCell est vide, vérifiez si une valeur manquante peut être déterminée
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
     * Draws the edge points on the game board.
     * @param edgePoints
     */
    public void drawEdgePoints(ArrayList<EdgePoint> edgePoints) {
        Platform.runLater(() -> {
            double radius = 7; // Defines the dot size
            clearEdgePoints(); // Clears the old dots
            
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
                    point.setFill(Color.BLACK); // Dot fill color
                    point.setStroke(Color.WHITE); // Dot border color
                } else if (edgePoint.getType().equals("white")) {
                    point.setFill(Color.WHITE);
                    point.setStroke(Color.BLACK);
                }

                // Add the Circle to the view
                view.getChildren().add(point);
            }
        });
    }

    /*public void updateGameBoard() {
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                // Update the cell's appearance based on the model
                int number = model.getNumber(row, col);
                cell.setNumber(number);
                // Add any other update logic here
            }
        }
    }*/

    /**
     * Checks if the game is won.
     * @return true if the game is won, false otherwise.
     */
    public boolean checkGameStatus() {
        for (int row = 0; row < model.getGridSize(); row++) {
            for (int col = 0; col < model.getGridSize(); col++) {
                Cell cell = view.getCell(row, col);
                int number = cell.getNumber();
    
                if (number != model.getNumber(row, col)) {
                    return false; // If one cell does not match, the game is not won
                }
            }
        }
    
        return true; // Toutes les cellules correspondent, la partie est gagnée
    }

    /**
     * Checks if the game board is fully filled by the user.
     * @return true if the game board is full, false otherwise.
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
    
        return true; // Toutes les cellules sont remplies, la grille est pleine
    }
    
    /**
     * Resets the game.
     */
    public void resetGame() {
        // Reset the model to start a new game
        // Update the view to reflect the reset game
        initializeGameBoard();
    }

    /**
     * Adds a resize listener to the stage.
     * @param stage
     */
    public void addResizeListener(Stage stage) {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            // Draw again the dots when the stage is resized
            drawEdgePoints(model.getEdgePoints());
        };

        // Add listeners to the stage's width and height properties
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    /**
     * Clears the edge points from the game board.
     */
    public void clearEdgePoints() {
        // Delete all the dots from the view
        view.getChildren().removeIf(node -> node instanceof Circle);
    }    

    /**
     * Shows a victory message when the grid is correct.
     */
    public void showVictoryMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Vous avez résolu le puzzle.");

        alert.showAndWait();
    }

    /**
     * Shows an incorrect grid message when the grid is incorrect.
     */
    public void showIncorrectGridMessage() {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Incorrect !");
        alert.setHeaderText(null);
        alert.setContentText("Le puzzle n'est pas résolu.");

        alert.showAndWait();
    }
}

