package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class GameBoardPanel extends GridPane {
    private Cell[][] cells;
    private HBox numberBar;
    private GridPane gridPane;

    public GameBoardPanel() {
        super();
        this.cells = new Cell[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        this.gridPane = createGridPane();
        this.numberBar = new HBox(10);

        this.setAlignment(Pos.CENTER);
        initializeNumberBar();
        this.add(gridPane, 0, 0);
        this.add(numberBar, 0, 1, 2, 1);

    }

    private void initializeNumberBar() {
        // Create a label for the number bar
        Label label = new Label("Select a number:");
        label.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold;");

        // Create a button for each number
        for (int j = 1; j <= KropkiConstants.GRID_SIZE; j++) {
            final int number = j;
            Button numberButton = new Button(String.valueOf(number));
            numberButton.setId("numberButton" + number); // Set ID for easier lookup
            numberButton.setStyle("-fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;");

            // Add the button to the number bar
            numberBar.getChildren().add(numberButton);
        }

        // Set spacing between the buttons
        numberBar.setSpacing(10);

        // Set a border around the number bar
        numberBar.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-padding: 10px;");

        // Add the label and the number buttons to the number bar
        numberBar.getChildren().add(0, label);
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                cells[row][col] = new Cell(row, col);
                gridPane.add(cells[row][col], col, row);
                gridPane.setGridLinesVisible(true);
                gridPane.setAlignment(Pos.CENTER);
            }
        }
        return gridPane;
    }

    public Cell getCell(int row, int col) {
        if (row < 0 || row >= KropkiConstants.GRID_SIZE || col < 0 || col >= KropkiConstants.GRID_SIZE) {
            throw new IllegalArgumentException("Invalid row or column index");
        }

        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
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
}