package com.kropkigame.view;

import com.kropkigame.model.CellStatus;

import javafx.scene.control.TextField;

public class Cell extends TextField {
    private int row;
    private int col;
    private int number; // The correct number for this cell.
    private CellStatus status;

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.setPrefWidth(40); // Adjust size as needed.
        this.setPrefHeight(40);

        // You can add event listeners here if needed.
        // For example, to listen for changes in text:
        // this.textProperty().addListener(...);
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getNumber() {
        if (getText().isEmpty()) {
            return 0;
        }
        return Integer.parseInt(getText());
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CellStatus getStatus() {
        return this.status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }

}
