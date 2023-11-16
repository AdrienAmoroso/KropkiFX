package com.kropkigame.controller;

import com.kropkigame.model.EdgePoint;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

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
    }

    public void handleCellSelected(MouseEvent event, Cell cell) { 
        if (selectedCell != null) {
            // Reset the style of the previously selected cell (you can adjust the style as
            // desired)
            selectedCell.setStyle(null);
        }

        highlightSelection(cell); // Highlight the selected cell
        selectedCell = cell; // Set the current cell as the selected cell
    }

    public void handleNumberButtonClicked(int number) {
        if (selectedCell != null) {
            selectedCell.setNumber(number);
        }

        //updateGameBoard();
    }

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

    private boolean checkNumberInRow(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                if (view.getCell(row, j).getNumber() == number && j != col) {
                    ruleChecked = true;  
                    break;          
                }
            }
        }

        return ruleChecked;
    }

    private boolean checkNumberInCol(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
                if (view.getCell(i, col).getNumber() == number && i != row) {
                    ruleChecked = true;
                    break;
                }
            }
        }

        return ruleChecked;
    }

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

    private void highlightRow(int row) {
        for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
            highlightCellBorders(view.getCell(row, j));
        }
    }
    
    private void highlightCol(int col) {
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            highlightCellBorders(view.getCell(i, col));
        }
    }

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

    private void highlightCellBorders(Cell cell) {
        cell.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void highlightSelection(Cell cell) {
        cell.setStyle("-fx-border-color: grey; -fx-border-width: 1px; -fx-background-color: lightblue;");
    }

    private void resetCellBorders() {
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                view.getCell(i, j).setStyle(null);
            }
        }
    }
}