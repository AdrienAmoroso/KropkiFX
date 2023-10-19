package com.kropkigame.view;

import com.kropkigame.controller.GameController;
import com.kropkigame.model.KropkiConstants;
import javafx.scene.layout.GridPane;

public class GameBoardPanel extends GridPane {
    private Cell[][] cells;
    private GameController controller;

    public GameBoardPanel() {
        cells = new Cell[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        this.setGridLinesVisible(true);

        initializeBoard();
    }

    public void setController(GameController controller) {
        this.controller = controller;
        attachEventHandlers(); // Assuming you'd want to attach events after setting the controller
    }

    private void initializeBoard() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                this.add(cells[row][col], col, row); // Adding to the GridPane
            }
        }
    }

    public Cell getCell(int row, int col) {
        if (row >= 0 && row < KropkiConstants.GRID_SIZE && col >= 0 && col < KropkiConstants.GRID_SIZE) {
            return cells[row][col];
        } else {
            throw new IllegalArgumentException("Invalid row or column index");
        }
    }

    private void attachEventHandlers() {
        // If you need to attach specific events to cells that involve the controller
        // you would do it here. E.g. handling the selection logic.
    }

    // ... Other methods for the GameBoardPanel
}
