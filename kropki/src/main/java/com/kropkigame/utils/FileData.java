package com.kropkigame.utils;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileData {
    public static GameBoardPanel createGridFromFile(String filePath) throws FileNotFoundException {
        int[][] grid = new int[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        boolean[][] isGiven = new boolean[KropkiConstants.GRID_SIZE][KropkiConstants.GRID_SIZE];
        File file = new File(filePath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.startsWith("Grille")) {
                for (int i = 0; i < KropkiConstants.GRID_SIZE; i++) {
                    line = scanner.nextLine();
                    String[] numbers = line.split(" ");
                    for (int j = 0; j < KropkiConstants.GRID_SIZE; j++) {
                        grid[i][j] = Integer.parseInt(numbers[j]);
                    }
                }
            } else if (line.startsWith("Points noirs")) {
                while (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    if (line.startsWith("Points blancs")) {
                        break;
                    }
                    String[] points = line.split(" - ");
                    String[] point1 = points[0].split(",");
                    String[] point2 = points[1].split(",");
                    int row1 = Integer.parseInt(point1[0].substring(1)) - 1;
                    int col1 = Integer.parseInt(point1[1].substring(0, point1[1].length() - 1)) - 1;
                    int row2 = Integer.parseInt(point2[0].substring(1)) - 1;
                    int col2 = Integer.parseInt(point2[1].substring(0, point2[1].length() - 1)) - 1;
                    if (row1 == row2) {
                        if (col1 < col2) {
                            grid[row1][col1 + 1] = grid[row1][col2] + 1;
                            isGiven[row1][col1 + 1] = true;
                        } else {
                            grid[row1][col2 + 1] = grid[row1][col1] + 1;
                            isGiven[row1][col2 + 1] = true;
                        }
                    } else {
                        if (row1 < row2) {
                            grid[row1 + 1][col1] = grid[row2][col1] + 1;
                            isGiven[row1 + 1][col1] = true;
                        } else {
                            grid[row2 + 1][col1] = grid[row1][col1] + 1;
                            isGiven[row2 + 1][col1] = true;
                        }
                    }
                }
            }
        }

        scanner.close();

        GameBoardPanel gameBoardPanel = new GameBoardPanel();
        for (int row = 0; row < KropkiConstants.GRID_SIZE; row++) {
            for (int col = 0; col < KropkiConstants.GRID_SIZE; col++) {
                Cell cell = gameBoardPanel.getCell(row, col);
                cell.setNumber(grid[row][col]);
            }
        }

        return gameBoardPanel;
    }
}