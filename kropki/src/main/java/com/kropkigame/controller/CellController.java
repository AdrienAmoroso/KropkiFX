package com.kropkigame.controller;

import com.kropkigame.model.EdgePoint;
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

    public void handleNumberButtonClicked(int number) {
        if (selectedCell != null) {
            selectedCell.setNumber(number);
            checkKropkiSudokuRules(selectedCell);
        }
    }

    public boolean checkKropkiSudokuRules(Cell cell) {
        int row = cell.getRow();
        int col = cell.getCol();
        int number = cell.getNumber();
    
        if (number != 0) { // On vérifie uniquement si la cellule a un nombre différent de 0
            if (!checkNumberInRow(row, number)) {
                highlightRow(cell);
            }
    
            if (!checkNumberInCol(col, number)) {
                highlightCol(cell);
            }
    
            if (!checkBlackDotRule(row, col, number) || !checkWhiteDotRule(row, col, number)) {
                dotError(cell);
            }
        }
    
        return true;
    }

    private boolean checkNumberInRow(int row, int number) {
        for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
            if (view.getCell(row, j).getNumber() == number && number != 0 && view.getCell(row, j).getNumber() != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNumberInCol(int col, int number) {
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            if (view.getCell(i, col).getNumber() == number && number != 0 && view.getCell(i, col).getNumber() != 0) {
                return true;
            }
        }
        return false;
    }

    private boolean checkBlackDotRule(int row, int col, int number) {
        boolean ruleChecked = false;

        if (number != 0) {
            for (EdgePoint edgePoint : model.getEdgePoints()) {
                if (edgePoint.getType().equals("black")) {
                    if (edgePoint.getSourceRow() == row && edgePoint.getSourceCol() == col) {
                        Cell otherCell = view.getCell(edgePoint.getTargetRow(), edgePoint.getTargetCol());
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (number == otherNumber * 2 || otherNumber == number * 2)) {
                            ruleChecked = true;
                        }
                    }
    
                    if (edgePoint.getTargetRow() == row && edgePoint.getTargetCol() == col) {
                        Cell otherCell = view.getCell(edgePoint.getSourceRow(), edgePoint.getSourceCol());
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (number == otherNumber * 2 || otherNumber == number * 2)) {
                            ruleChecked = true;
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
                    if (edgePoint.getSourceRow() == row && edgePoint.getSourceCol() == col) {
                        Cell otherCell = view.getCell(edgePoint.getTargetRow(), edgePoint.getTargetCol());
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (Math.abs(number - otherNumber) != 1)) {
                            ruleChecked = true;
                        }
                    }
    
                    if (edgePoint.getTargetRow() == row && edgePoint.getTargetCol() == col) {
                        Cell otherCell = view.getCell(edgePoint.getSourceRow(), edgePoint.getSourceCol());
                        int otherNumber = otherCell.getNumber();
    
                        if (otherNumber != 0 && (Math.abs(number - otherNumber) != 1)) {
                            ruleChecked = true;
                        }
                    }
                }
            }
        }
    
        return ruleChecked;
    }

    private void highlightRow(Cell cell) {
        int row = cell.getRow();
        
        for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
            Cell adjacentCell = view.getCell(row, j);
            adjacentCell.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }

    private void highlightCol(Cell cell) {
        int col = cell.getCol();
        
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            Cell adjacentCell = view.getCell(i, col);
            adjacentCell.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
        }
    }

    private void dotError(Cell cell) {
        cell.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }
}