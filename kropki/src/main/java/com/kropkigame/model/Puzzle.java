package com.kropkigame.model;

import java.util.ArrayList;

/**
 * Represents a Kropki puzzle.
 */
public class Puzzle implements KropkiConstants {
    private int[][] numbers;
    private int gridSize;
    private ArrayList<EdgePoint> edgePoints;

    /**
     * Constructs an empty Kropki puzzle with a default grid size of 4x4.
     */
    public Puzzle() {
        // Initialize an empty Kropki grid problem:
        this.gridSize = 4;
        this.numbers = new int[gridSize][gridSize];
        
        // Setting the numbers to all zeros
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                numbers[i][j] = 0;
            }
        }

        // Initialize an empty list of edge points
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    /**
     * Constructs a Kropki puzzle with the specified numbers and grid size.
     * 
     * @param numbers The numbers of the puzzle grid.
     * @param gridSize The size of the puzzle grid.
     */
    public Puzzle(int[][] numbers, int gridSize) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    /**
     * Constructs a Kropki puzzle with the specified numbers, grid size, and edge points.
     * 
     * @param numbers The numbers of the puzzle grid.
     * @param gridSize The size of the puzzle grid.
     * @param edgePoints The edge points of the puzzle.
     */
    public Puzzle(int[][] numbers, int gridSize, ArrayList<EdgePoint> edgePoints) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = edgePoints;
    }

    /**
     * Gets the number at the specified row and column in the puzzle grid.
     * 
     * @param row The row index.
     * @param col The column index.
     * @return The number at the specified position.
     * @throws IndexOutOfBoundsException if the row or column index is out of bounds.
     */
    public int getNumber(int row, int col) {
        if (row < 0 || row >= this.gridSize || col < 0 || col >= this.gridSize) {
            throw new IndexOutOfBoundsException("Les indices ne doivent pas dépasser la taille de la grille.");
        }
        
        return this.numbers[row][col];
    }
    
    /**
     * Gets a copy of the numbers in the puzzle grid.
     * 
     * @return A copy of the numbers in the puzzle grid.
     */
    public int[][] getNumbers() {
        int[][] numbersList = this.numbers.clone();
        return numbersList;
    }

    /**
     * Sets the numbers in the puzzle grid.
     * 
     * @param numbers The numbers to set.
     */
    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    /**
     * Gets the size of the puzzle grid.
     * 
     * @return The size of the puzzle grid.
     */
    public int getGridSize() {
        return this.gridSize;
    }

    /**
     * Sets the size of the puzzle grid.
     * 
     * @param gridSize The size of the puzzle grid.
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Gets the edge points of the puzzle.
     * 
     * @return The edge points of the puzzle.
     */
    public ArrayList<EdgePoint> getEdgePoints() {
        return this.edgePoints;
    }

    /**
     * Sets the edge points of the puzzle.
     * 
     * @param edgePoints The edge points to set.
     */
    public void setEdgePoints(ArrayList<EdgePoint> edgePoints) {
        this.edgePoints = edgePoints;
    }

    /**
     * Adds an edge point to the puzzle.
     * 
     * @param edgePoint The edge point to add.
     */
    public void addEdgePoint(EdgePoint edgePoint) {
        if (!edgePoints.contains(edgePoint)) {
            edgePoints.add(edgePoint);
        }
    }

    /**
     * Checks if a black edge point exists between the specified source and target positions.
     * 
     * @param sourceRow The row index of the source position.
     * @param sourceCol The column index of the source position.
     * @param targetRow The row index of the target position.
     * @param targetCol The column index of the target position.
     * @return true if a black edge point exists, false otherwise.
     */
    public boolean existsBlackEdgePoint(int sourceRow, int sourceCol, int targetRow, int targetCol) {
        for (EdgePoint edgePoint : edgePoints) {
            if (edgePoint.getType().equals("black")) {
                if (edgePoint.getSourceRow() == sourceRow && edgePoint.getSourceCol() == sourceCol
                        && edgePoint.getTargetRow() == targetRow && edgePoint.getTargetCol() == targetCol) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a white edge point exists between the specified source and target positions.
     * 
     * @param sourceRow The row index of the source position.
     * @param sourceCol The column index of the source position.
     * @param targetRow The row index of the target position.
     * @param targetCol The column index of the target position.
     * @return true if a white edge point exists, false otherwise.
     */
    public boolean existsWhiteEdgePoint(int sourceRow, int sourceCol, int targetRow, int targetCol) {
        for (EdgePoint edgePoint : edgePoints) {
            if (edgePoint.getType().equals("white")) {
                if (edgePoint.getSourceRow() == sourceRow && edgePoint.getSourceCol() == sourceCol
                        && edgePoint.getTargetRow() == targetRow && edgePoint.getTargetCol() == targetCol) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns a string representation of the puzzle.
     * 
     * @return A string representation of the puzzle.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Puzzle (" + gridSize + "x" + gridSize + ") Numbers:\n");
        for (int row = 0; row < this.numbers.length; row++) {
            for (int col = 0; col < this.numbers[0].length; col++) {
                builder.append(this.numbers[row][col]);
                if (col < this.numbers[0].length - 1) {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }

        builder.append("Edge Points:\n");
        for (EdgePoint edgePoint : this.edgePoints) {
            builder.append(edgePoint.toString()).append("\n");
        }

        return builder.toString();
    }
}
