package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Represents the game board panel, which contains the grid of cells and the number bar.
 */
public class GameBoardPanel extends BorderPane {
    private Cell[][] cells;
    private int gridSize;
    private HBox numberBar;
    private GridPane gridPane;
    private HelpButton helpButton;

    /**
     * Constructs a game board panel with the specified grid size.
     * @param gridSize the size of the grid.
     */
    public GameBoardPanel(int gridSize) {
        this.gridSize = gridSize;
        this.cells = new Cell[gridSize][gridSize];
        this.gridPane = createGridPane(gridSize);
        this.numberBar = createNumberBar(gridSize);
        this.helpButton = new HelpButton();

        VBox contentVBox = new VBox(10);
        contentVBox.setAlignment(Pos.CENTER);

        contentVBox.getChildren().addAll(helpButton, gridPane);

        this.setStyle(KropkiConstants.GAMEBOARD_STYLE);
        this.setCenter(contentVBox);
        this.setBottom(numberBar);
    }

    /**
     * Creates the grid pane.
     * @param gridSize
     * @return the grid pane.
     */
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

    /**
     * Creates the number bar.
     * @param gridSize
     * @return the number bar.
     */
    private HBox createNumberBar(int gridSize) {
        this.numberBar = new HBox(10);

        // Create a button for each number
        for (int j = 1; j <= gridSize; j++) {
            final int number = j;
            Button numberButton = new Button(String.valueOf(number));
            numberButton.setId("numberButton" + number);
            numberButton.setStyle(KropkiConstants.NUMBER_BUTTON_STYLE);
            numberButton.setMaxWidth(Double.MAX_VALUE);

            // Add the button to the number bar
            numberBar.getChildren().add(numberButton);

            HBox.setHgrow(numberButton, Priority.ALWAYS);           
        }

        // Set spacing between the buttons
        numberBar.setSpacing(10);

        // Set a border around the number bar
        numberBar.setStyle(KropkiConstants.NUMBER_BAR_STYLE);

        return numberBar;
    }
    
    /**
     * Returns the cell at the specified row and column.
     * @param row the row of the cell.
     * @param col the column of the cell.
     * @return the cell at the specified row and column.
     */
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

    /**
     * Returns the grid pane.
     * @return the grid pane.
     */
    public GridPane getGridPane() {
        return this.gridPane;
    }

    /**
     * Returns the number bar.
     * @return the number bar.
     */
    public HBox getNumberBar() {
        return this.numberBar;
    }

    /**
     * Returns the grid size.
     * @return the grid size.
     */
    public int getGridSize() {
        return this.gridSize;
    }

    /**
     * Returns the help button.
     * @return the help button.
     */
    public HelpButton getHelpButton() {
        return this.getHelpButton();
    }

}