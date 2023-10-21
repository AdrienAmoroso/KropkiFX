package com.kropkigame.view;

import com.kropkigame.controller.GameController;
import com.kropkigame.model.CellStatus;
import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Cell extends Pane {
    private int row;
    private int col;
    private int number; // The correct number for this cell.
    private CellStatus status;
    private Text textDisplay;
    private GameController controller;

    public Text getTextDisplay() {
        return this.textDisplay;
    }

    public void setTextDisplay(Text textDisplay) {
        this.textDisplay = textDisplay;
    }

    public Cell(int row, int col, GameController controller) {
        this.row = row;
        this.col = col;
        this.setPrefWidth(KropkiConstants.CELL_SIZE); // Adjust size as needed.
        this.setPrefHeight(KropkiConstants.CELL_SIZE);
        //this.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));


        textDisplay = new Text();
        textDisplay.setLayoutX(this.getPrefWidth() / 2);
        textDisplay.setLayoutY(this.getPrefHeight() / 2);
        this.controller = controller;

        this.getChildren().add(textDisplay);

        // Event listener for selection
        this.setOnMouseClicked(event -> {
            if (controller != null) {
                if (controller.getSelectedCell() != null) {
                    controller.getSelectedCell().setStyle(null);
                }
                controller.setSelectedCell(this);
            }
            this.setStyle("-fx-background-color: lightblue;");
        });
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
    }

    public CellStatus getStatus() {
        return this.status;
    }

    public void setStatus(CellStatus status) {
        this.status = status;
    }
}
