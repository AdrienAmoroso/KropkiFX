package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class GameBoardPanel extends BorderPane {
    private Cell[][] cells;
    private int gridSize;
    private HBox numberBar;
    private GridPane gridPane;

    public GameBoardPanel(int gridSize) {
        this.gridSize = gridSize;
        this.cells = new Cell[gridSize][gridSize];
        this.gridPane = createGridPane(gridSize);
        this.numberBar = createNumberBar(gridSize);

        this.setCenter(gridPane);
        this.setBottom(numberBar);
    }

    private GridPane createGridPane(int gridSize) {
        this.gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        gridPane.setAlignment(Pos.CENTER);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col] = new Cell(row, col);
                gridPane.add(cells[row][col], col, row);
            }
        }
        return gridPane;
    }

    private HBox createNumberBar(int gridSize) {
        this.numberBar = new HBox(10);

        // Create a button for each number
        for (int j = 1; j <= gridSize; j++) {
            final int number = j;
            Button numberButton = new Button(String.valueOf(number));
            numberButton.setId("numberButton" + number); // Set ID for easier lookup
            numberButton.setStyle(KropkiConstants.NUMBER_BUTTON_STYLE);
            numberButton.setMaxWidth(Double.MAX_VALUE);

            // Add the button to the number bar
            numberBar.getChildren().add(numberButton);

            HBox.setHgrow(numberButton, Priority.ALWAYS);           
        }

        // Set spacing between the buttons
        numberBar.setSpacing(10);

        // Set a border around the number bar
        numberBar.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        return numberBar;
    }  
    
    public Cell getCell(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Invalid row or column index");
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (cells[i][j].getRow() == row && cells[i][j].getCol() == col) {
                    return cells[i][j];
                }
            }
        }
        
        return null;
    }

    public GridPane getGridPane() {
        return this.gridPane;
    }

    public HBox getNumberBar() {
        return this.numberBar;
    }
}