package com.kropkigame.model;

import java.util.ArrayList;

public class Puzzle implements KropkiConstants {
    private int[][] numbers;
    private int gridSize;
    private ArrayList<EdgePoint> edgePoints;

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

    public Puzzle(int[][] numbers, int gridSize) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    public Puzzle(int[][] numbers, int gridSize, ArrayList<EdgePoint> edgePoints) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = edgePoints;
    }

    public int getNumber(int row, int col) {
        if (row < 0 || row >= this.gridSize || col < 0 || col >= this.gridSize) {
            throw new IndexOutOfBoundsException("Les indices ne doivent pas dépasser la taille de la grille.");
        }
        
        return this.numbers[row][col];
    }
    
    public int[][] getNumbers() {
        int[][] numbersList = this.numbers.clone();
        return numbersList;
    }

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public int getGridSize() {
        return this.gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public ArrayList<EdgePoint> getEdgePoints() {
        return this.edgePoints;
    }

    public void setEdgePoints(ArrayList<EdgePoint> edgePoints) {
        this.edgePoints = edgePoints;
    }

    public void addEdgePoint(EdgePoint edgePoint) {
        if (!edgePoints.contains(edgePoint)) {
            edgePoints.add(edgePoint);
        }
    }

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
