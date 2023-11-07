package com.kropkigame.model;

import java.util.ArrayList;

public class Puzzle {
    private int[][] numbers;
    private ArrayList<EdgePoint> edgePoints;

    public Puzzle() {
        // Initialize an empty Kropki grid problem:
        this.numbers = new int[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        
        // Setting the numbers to all zeros
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                numbers[i][j] = 0;
            }
        }

        // Initialize an empty list of edge points
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    public Puzzle(int[][] numbers) {
        this.numbers = numbers;
    }

    public int getNumber(int row, int col) {
        if (row < 0 || row >= KropkiConstants.GRID_SIZE || col < 0 || col >= KropkiConstants.GRID_SIZE) {
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < this.numbers.length; row++) {
            for (int col = 0; col < this.numbers[0].length; col++) {
                builder.append(this.numbers[row][col]);
                if (col < this.numbers[0].length - 1) {
                    builder.append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
