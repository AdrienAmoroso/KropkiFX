package com.kropkigame.view;

import com.kropkigame.controller.GameController;
import com.kropkigame.model.KropkiConstants;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class GameBoardPanel extends GridPane {
    private Cell[][] cells;
    private GameController controller;
    private HBox numberBar;

    public GameBoardPanel() {
        super();
        cells = new Cell[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        numberBar = new HBox(10);

        this.setGridLinesVisible(true);
        this.setAlignment(Pos.CENTER);

        initializeBoard();
        attachEventHandlers();
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    private void initializeBoard() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col, controller);
                gridPane.add(cells[row][col], col, row);
            }
        }
        return gridPane;
    }

    private void initializeBoard() {
        // It seems the board is already initialized in createGridPane(), so this method
        // might be redundant.
        // You can decide to remove or modify this method based on your needs.
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

    @Override
    public void resize(double width, double height) {
        super.resize(width, height);
        double cellSize = Math.min(width / KropkiConstants.GRID_SIZE, height / KropkiConstants.GRID_SIZE);
        this.setPrefSize(cellSize * KropkiConstants.GRID_SIZE, cellSize * KropkiConstants.GRID_SIZE);
    }

    // ... Other methods for the GameBoardPanel
}