package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Represents a cell in the Kropki game board.
 */
public class Cell extends Pane {
    private int row;
    private int col;
    private int number; // The correct number for this cell.
    private Text textDisplay;

    /**
     * Returns the Text object that displays the number in the cell.
     * @return the Text object that displays the number in the cell.
     */
    public Text getTextDisplay() {
        return this.textDisplay;
    }

    /**
     * Sets the Text object that displays the number in the cell.
     * @param textDisplay the Text object that displays the number in the cell.
     */
    public void setTextDisplay(Text textDisplay) {
        this.textDisplay = textDisplay;
    }

    /**
     * Constructs a cell with the specified row and column.
     * @param row the row of the cell.
     * @param col the column of the cell.
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.setPrefWidth(KropkiConstants.CELL_SIZE);
        this.setPrefHeight(KropkiConstants.CELL_SIZE); 

        textDisplay = new Text();
        this.getChildren().add(textDisplay);
    }

    /**
     * Constructs a cell with the specified row, column, and number.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @param number the number of the cell.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Sets the row of the cell.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Returns the column of the cell.
     * @return the column of the cell.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Sets the column of the cell.
     * @param col the column of the cell.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Returns the number of the cell.
     * @return the number of the cell.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Sets the number of the cell.
     * @param number the number of the cell.
     */
    public void setNumber(int number) {
        this.number = number;
        textDisplay.setText(String.valueOf(number));
        textDisplay.setStyle(KropkiConstants.CELL_TEXT_STYLE);

        // Center TextDisplay in Pane
        double x = (this.getPrefWidth() - textDisplay.getBoundsInLocal().getWidth()) / 2;
        double y = (this.getPrefHeight() - textDisplay.getBoundsInLocal().getHeight()) / 2;
        textDisplay.relocate(x, y);
    }
}
