package com.kropkigame.controller;

import com.kropkigame.model.Puzzle;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.CellStatus;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.scene.control.Alert;
import javafx.scene.input.KeyEvent;

public class GameController {
    private Puzzle model;
    private GameBoardPanel view;

    public GameController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;

        setupGameBoard();
        attachEventHandlers();
    }

    private void setupGameBoard() {
        // Since no numbers are given initially, there's no need to pre-populate any
        // cells.
        // Instead, we just ensure all cells are editable and empty, which is handled by
        // the GameBoardPanel's initialization.
    }

    private void attachEventHandlers() {
        // Attach event handlers for each cell
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                cell.addEventHandler(KeyEvent.KEY_TYPED, event -> handleKeyTyped(event, cell));

            }
        }
    }

    private void handleKeyTyped(KeyEvent event, Cell cell) {
        // Ensure the input is a character (KEY_RELEASED can fire for non-character
        // keys)
        if (!event.getCode().isDigitKey()) {
            return;
        }

        String input = event.getCharacter();
        int number;
        if (!Character.isDigit(input.charAt(0)))
            return;

        try {
            number = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            cell.setText(""); // Clear the cell if non-number is entered
            return;
        }

        // Validate the number based on Kropki game logic
        // Validate the number based on Kropki game logic
        if (isValidMove(cell.getRow(), cell.getCol(), number)) {
            cell.setText(String.valueOf(number));
            cell.setStatus(CellStatus.CORRECT_GUESS);
            resetHighlight(cell.getRow(), cell.getCol()); // Reset previous highlights
            System.out.println("Is Valide");
            // TODO: If all cells are filled correctly, declare win
        } else {
            cell.setStatus(CellStatus.WRONG_GUESS);
            showErrorMessage("Invalid Move!");
            highlightRow(cell.getRow());
            highlightColumn(cell.getCol());
            System.out.println("Is False");
        }

        event.consume(); // Consume the event
    }

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
