package com.kropkigame.model;

public class Puzzle {
    private int[][] numbers = new int[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
    private boolean[][] isGiven = new boolean[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];

    public Puzzle() {
        // Initialize an empty Kropki grid problem:

        // Setting the numbers to all zeros
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                numbers[i][j] = 0;
            }
        }

        // Setting isGiven to all false
        for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
            for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                isGiven[i][j] = false;
            }
        }
    }

    // ... The rest of the methods remain unchanged.

    public int getNumber(int row, int col) {
        return numbers[row][col];
    }

    public boolean isGiven(int row, int col) {
        return isGiven[row][col];
    }

    public int[][] getNumbers() {
        return this.numbers;
    }

    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    public boolean[][] getIsGiven() {
        return this.isGiven;
    }

    public void setIsGiven(boolean[][] isGiven) {
        this.isGiven = isGiven;
    }

    // ... More methods as needed for puzzle manipulation.
}
