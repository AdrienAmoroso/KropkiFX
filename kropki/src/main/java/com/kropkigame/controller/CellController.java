package com.kropkigame.controller;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class CellController {
    private Cell selectedCell; // To track which cell is currently selected
    private Puzzle model;
    private GameBoardPanel view;

    public Cell getSelectedCell() {
        return this.selectedCell;
    }

    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

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

    public CellController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.selectedCell = null;
        initializeCellListeners();
    }

    private void initializeCellListeners() {
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = view.getCell(row, col);
                cell.setOnMouseClicked(event->handleCellSelected(event, cell));
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

    public void handleCellSelected(MouseEvent event, Cell cell) {
        if (selectedCell != null) {
            // Reset the style of the previously selected cell (you can adjust the style as
            // desired)
            selectedCell.setStyle(null);
        }

        cell.setStyle("-fx-background-color: lightblue;"); // Highlight the selected cell
        selectedCell = cell; // Set the current cell as the selected cell
    }

    public void setNumber(Cell cell, int number) {
        cell.setNumber(number);
    }

    public void handleNumberButtonClicked(int number) {
        if (selectedCell != null) {
            selectedCell.setNumber(number);
        }
    }
}
