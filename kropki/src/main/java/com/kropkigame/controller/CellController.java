package com.kropkigame.controller;

import com.kropkigame.model.EdgePoint;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.scene.input.MouseEvent;

/**
 * Represents the controller for each Cell in the grid.
 */
public class CellController {
    private Cell selectedCell; // To track which cell is currently selected
    private Puzzle model;
    private GameBoardPanel view;

    /**
     * Returns the currently selected cell.
     * @return the currently selected cell.
     */
    public Cell getSelectedCell() {
        return this.selectedCell;
    }

    /**
     * Sets the currently selected cell.
     * @param selectedCell
     */
    public void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    /**
     * Returns the model.
     * @return the model.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Sets the model.
     * @param model
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Returns the view.
     * @return the view.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Sets the view.
     * @param view
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Constructs a cell controller with the specified model and view.
     * @param model the model.
     * @param view the view.
     */
    public CellController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.selectedCell = null;
    }

    /**
     * Handles the event when a cell is selected.
     * @param event the mouse event.
     * @param cell the cell that was selected.
     */
    public void handleCellSelected(MouseEvent event, Cell cell) { 
        if (selectedCell != null) {
            // Reset the style of the previously selected cell (you can adjust the style as
            // desired)
            selectedCell.setStyle(KropkiConstants.CELL_BORDER_STYLE);
        }

        highlightSelection(cell); // Highlight the selected cell
        selectedCell = cell; // Set the current cell as the selected cell
    }

    /**
     * Handles the event when a number button is clicked.
     * @param number the number that was clicked.
     */
    public void handleNumberButtonClicked(int number) {
        if (selectedCell != null) {
            selectedCell.setNumber(number);
        }

        //updateGameBoard();
    }

    /**
     * Updates the game board to highlight cells that violate the rules.
     */
    public void updateGameBoard() {
        if (selectedCell != null) {
            int row = selectedCell.getRow();
            int col = selectedCell.getCol();
            int number = selectedCell.getNumber();
    
            if (checkNumberInRow(row, col, number)) {
                highlightRow(row);
                return;
            }
    
            if (checkNumberInCol(row, col, number)) {
                highlightCol(col);
                return;
            }
    
            if (checkBlackDotRule(row, col, number)) {
                highlightConnectedCellsByDot(selectedCell, "black");
                return;
            }
    
            if (checkWhiteDotRule(row, col, number)) {
                highlightConnectedCellsByDot(selectedCell, "white");
                return;
            }

            resetCellBorders();
        }
    }

    /**
     * Checks if the number of the specified cell is already in the row.
     * @param row
     * @param col
     * @param number
     * @return true if the number is already in the row, false otherwise.
     */
    private boolean checkNumberInRow(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (int j = 0; j < model.getGridSize(); j++) {
                if (view.getCell(row, j).getNumber() == number && j != col) {
                    ruleChecked = true;  
                    break;          
                }
            }
        }

        return ruleChecked;
    }

    /**
     * Checks if the number of the specified cell is already in the column.
     * @param row
     * @param col
     * @param number
     * @return true if the number is already in the column, false otherwise.
     */
    private boolean checkNumberInCol(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (int i = 0; i < model.getGridSize(); i++) {
                if (view.getCell(i, col).getNumber() == number && i != row) {
                    ruleChecked = true;
                    break;
                }
            }
        }

        return ruleChecked;
    }

    /**
     * Checks if the black dot rule is violated.
     * @param row
     * @param col
     * @param number
     * @return true if the black dot rule is violated, false otherwise.
     */
    private boolean checkBlackDotRule(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (EdgePoint edgePoint : model.getEdgePoints()) {
                if (edgePoint.getType().equals("black")) {
                    if (edgePoint.getSourceRow()-1 == row && edgePoint.getSourceCol()-1 == col) {
                        Cell otherCell = view.getCell(edgePoint.getTargetRow()-1, edgePoint.getTargetCol()-1);
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (number == otherNumber * 2 || otherNumber == number * 2)) {
                            ruleChecked = true;
                            break;
                        }
                    }
    
                    if (edgePoint.getTargetRow()-1 == row && edgePoint.getTargetCol()-1 == col) {
                        Cell otherCell = view.getCell(edgePoint.getSourceRow()-1, edgePoint.getSourceCol()-1);
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (number == otherNumber * 2 || otherNumber == number * 2)) {
                            ruleChecked = true;
                            break;
                        }
                    }
                }
            
            }
        }
    
        return ruleChecked;
    }
    
    /**
     * Checks if the white dot rule is violated.
     * @param row
     * @param col
     * @param number
     * @return true if the white dot rule is violated, false otherwise.
     */
    private boolean checkWhiteDotRule(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (EdgePoint edgePoint : model.getEdgePoints()) {
                if (edgePoint.getType().equals("white")) {
                    if (edgePoint.getSourceRow()-1 == row && edgePoint.getSourceCol()-1 == col) {
                        Cell otherCell = view.getCell(edgePoint.getTargetRow()-1, edgePoint.getTargetCol()-1);
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (Math.abs(number - otherNumber) != 1)) {
                            ruleChecked = true;
                            break;
                        }
                    }
    
                    if (edgePoint.getTargetRow()-1 == row && edgePoint.getTargetCol()-1 == col) {
                        Cell otherCell = view.getCell(edgePoint.getSourceRow()-1, edgePoint.getSourceCol()-1);
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (Math.abs(number - otherNumber) != 1)) {
                            ruleChecked = true;
                            break;
                        }
                    }
                }
            }
        }
    
        return ruleChecked;
    }

    /**
     * Highlights the specified row.
     * @param row
     */
    private void highlightRow(int row) {
        for (int j = 0; j < model.getGridSize(); j++) {
            highlightCellBorders(view.getCell(row, j));
        }
    }
    
    /**
     * Highlights the specified column.
     * @param col
     */
    private void highlightCol(int col) {
        for (int i = 0; i < model.getGridSize(); i++) {
            highlightCellBorders(view.getCell(i, col));
        }
    }

    /**
     * Highlights the cells connected to the specified cell by the specified dot type.
     * @param cell
     * @param dotType
     */
    private void highlightConnectedCellsByDot(Cell cell, String dotType) {
        int row = cell.getRow();
        int col = cell.getCol();

        for (EdgePoint edgePoint : model.getEdgePoints()) {
            if (edgePoint.getType().equals(dotType)) {
                if (edgePoint.getSourceRow()-1 == row && edgePoint.getSourceCol()-1 == col) {
                    highlightCellBorders(view.getCell(edgePoint.getSourceRow()-1, edgePoint.getSourceCol()-1));
                    highlightCellBorders(view.getCell(edgePoint.getTargetRow()-1, edgePoint.getTargetCol()-1));
                }

                if (edgePoint.getTargetRow()-1 == row && edgePoint.getTargetCol()-1 == col) {
                    highlightCellBorders(view.getCell(edgePoint.getSourceRow()-1, edgePoint.getSourceCol()-1));
                    highlightCellBorders(view.getCell(edgePoint.getTargetRow()-1, edgePoint.getTargetCol()-1));
                }
            }
        }
    }

    /**
     * Highlights the borders of the specified cell.
     * @param cell
     */
    private void highlightCellBorders(Cell cell) {
        cell.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    /**
     * Highlights the specified cell.
     * @param cell
     */
    private void highlightSelection(Cell cell) {
        cell.setStyle("-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: blue;");
    }

    /**
     * Resets the borders of all cells.
     */
    private void resetCellBorders() {
        for (int i = 0; i < model.getGridSize(); i++) {
            for (int j = 0; j < model.getGridSize(); j++) {
                view.getCell(i, j).setStyle(null);
            }
        }
    }
}