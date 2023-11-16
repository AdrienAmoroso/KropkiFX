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

public class GameBoardController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;

    public Puzzle getModel() {
        return this.model;
    }

    public void setModel(Puzzle model) {
        this.model = model;
    }

    public GameBoardPanel getView() {
        return this.view;
    }

    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    public CellController getCellController() {
        return this.cellController;
    }

    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    public GameBoardController(Puzzle model, GameBoardPanel view, CellController cellController) {
        this.model = model;
        this.view = view;
        this.cellController = cellController;
    }

    public void initializeGameBoard() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                // Configure the cell's appearance and properties
                cell.setNumber(0);
                cell.getTextDisplay().setText("");
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCellSelection(event, cell));
            }
        }

        for (int i = 1; i <= KropkiConstants.GRID_SIZE; i++) {
            final int number = i;
            Button numberButton = (Button) view.lookup("#numberButton" + number);
            if (numberButton != null) {
                // Attach the event handler for button click to each number button
                numberButton.setOnAction(event -> handleNumberButtonClicked(number));
            }
        }
    }

    public void handleNumberButtonClicked(int number) {
        // Delegate number button click to the CellController
        cellController.handleNumberButtonClicked(number);
        
        if (isGridFull()) {
            if (checkGameStatus()) {
                showVictoryMessage();
            } else {
                showIncorrectGridMessage();
            }
        }
    }

    public void handleCellSelection(MouseEvent event, Cell cell) {
        // Delegate cell selection to the CellController
        cellController.handleCellSelected(event, cell);
    }

    public void drawEdgePoints(ArrayList<EdgePoint> edgePoints) {
        Platform.runLater(() -> {
            double radius = 5; // Définit la taille du point
            clearEdgePoints(); // Efface les anciens points
            
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
                    point.setStroke(Color.BLACK); // Couleur de la bordure du point
                } else if (edgePoint.getType().equals("white")) {
                    point.setFill(Color.WHITE);
                    point.setStroke(Color.BLACK);
                }

                // Ajoutez le cercle à la vue
                view.getChildren().add(point);
            }
        });
    }

    /*public void updateGameBoard() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                // Update the cell's appearance based on the model
                int number = model.getNumber(row, col);
                cell.setNumber(number);
                // Add any other update logic here
            }
        }
    }*/

    public boolean checkGameStatus() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                int number = cell.getNumber();
    
                if (number != model.getNumber(row, col)) {
                    return false; // Si une cellule ne correspond pas, la partie n'est pas gagnée
                }
            }
        }
    
        return true; // Toutes les cellules correspondent, la partie est gagnée
    }

    public boolean isGridFull() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                int number = cell.getNumber();
    
                if (number == 0) {
                    return false; // Si une cellule est vide, la grille n'est pas pleine
                }
            }
        }
    
        return true; // Toutes les cellules sont remplies, la grille est pleine
    }
    
    public void resetGame() {
        // Reset the model to start a new game
        // Update the view to reflect the reset game
        initializeGameBoard();
    }

    public void addResizeListener(Stage stage) {
        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) -> {
            // Redessinez les points lorsqu'il y a un changement de taille de la scène
            drawEdgePoints(model.getEdgePoints());
        };

        // Ajoutez des écouteurs pour la largeur et la hauteur de la scène
        stage.widthProperty().addListener(stageSizeListener);
        stage.heightProperty().addListener(stageSizeListener);
    }

    public void clearEdgePoints() {
        // Supprimez tous les cercles de la vue
        view.getChildren().removeIf(node -> node instanceof Circle);
    }    

    public void showVictoryMessage() {
        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Vous avez résolu le puzzle.");

        alert.showAndWait();
    }

    public void showIncorrectGridMessage() {
        Alert alert = new Alert(AlertType.ERROR);

        alert.setTitle("Incorrect !");
        alert.setHeaderText(null);
        alert.setContentText("Le puzzle n'est pas résolu.");

        alert.showAndWait();
    }
}

