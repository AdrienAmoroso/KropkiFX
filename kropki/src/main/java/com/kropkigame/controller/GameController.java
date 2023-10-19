package com.kropkigame.controller;

import com.kropkigame.model.Puzzle;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.CellStatus;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class GameController {
    private Puzzle model;
    private GameBoardPanel view;
    private Cell selectedCell; // To track which cell is currently selected

    public GameController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;

        attachEventHandlers();
    }

    private void attachEventHandlers() {
        // Attach mouse click event handlers for each cell
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> handleCellSelected(event, cell));
            }
        }

        // TODO: Attach event handlers for the number buttons/choices below the grid
    }

    private void handleCellSelected(MouseEvent event, Cell cell) {
        if (selectedCell != null) {
            // Reset the style of the previously selected cell (you can adjust the style as
            // desired)
            selectedCell.setStyle(null);
        }

        cell.setStyle("-fx-border-color: blue;"); // Highlight the selected cell
        selectedCell = cell; // Set the current cell as the selected cell

        // TODO: When a number is chosen from the list/buttons below, call a method to
        // set the number to this selected cell
    }

    public void handleNumberChosen(int number) {
        if (selectedCell == null) {
            return; // No cell is selected
        }

        if (isValidMove(selectedCell.getRow(), selectedCell.getCol(), number)) {
            selectedCell.setNumber(number);
            selectedCell.setStatus(CellStatus.CORRECT_GUESS);
            resetHighlight(selectedCell.getRow(), selectedCell.getCol());
        } else {
            selectedCell.setStatus(CellStatus.WRONG_GUESS);
            showErrorMessage("Invalid Move!");
            highlightRow(selectedCell.getRow());
            highlightColumn(selectedCell.getCol());
        }
    }

    // ... [Rest of your methods remain unchanged] ...

    private boolean isValidMove(int row, int col, int number) {
        // Check row
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            if (view.getCell(row, i).getNumber() == number) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            if (view.getCell(i, col).getNumber() == number) {
                return false;
            }
        }

        // Check 2x2 box
        int boxStartRow = row - row % 2; // For 4x4, 2 is the size of the small box. For 9x9, you'd use 3.
        int boxStartCol = col - col % 2;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (view.getCell(boxStartRow + i, boxStartCol + j).getNumber() == number) {
                    return false;
                }
            }
        }

        return true;
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void highlightRow(int row) {
        for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
            Cell currentCell = view.getCell(row, col);
            currentCell.setStyle("-fx-background-color: red;");
        }
    }

    private void highlightColumn(int col) {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            Cell currentCell = view.getCell(row, col);
            currentCell.setStyle("-fx-background-color: red;");
        }
    }

    private void resetHighlight(int row, int col) {
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            view.getCell(row, i).setStyle(null);
            view.getCell(i, col).setStyle(null);
        }
    }

}
