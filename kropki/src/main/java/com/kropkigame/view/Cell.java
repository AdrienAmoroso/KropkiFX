package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Cell extends Pane {
    private int row;
    private int col;
    private int number; // The correct number for this cell.
    private Text textDisplay;

    public Text getTextDisplay() {
        return this.textDisplay;
    }

    public void setTextDisplay(Text textDisplay) {
        this.textDisplay = textDisplay;
    }

    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.setPrefWidth(KropkiConstants.CELL_SIZE);
        this.setPrefHeight(KropkiConstants.CELL_SIZE); 

        textDisplay = new Text();
        this.getChildren().add(textDisplay);
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
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        textDisplay.setText(String.valueOf(number));
        textDisplay.setStyle(KropkiConstants.CELL_TEXT_STYLE);

        // Centrer le Text dans le Pane
        double x = (this.getPrefWidth() - textDisplay.getBoundsInLocal().getWidth()) / 2;
        double y = (this.getPrefHeight() - textDisplay.getBoundsInLocal().getHeight()) / 2;
        textDisplay.relocate(x, y);
    }
}
