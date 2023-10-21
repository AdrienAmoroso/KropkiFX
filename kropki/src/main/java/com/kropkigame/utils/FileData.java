package com.kropkigame.utils;

import com.kropkigame.model.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileData {

    public static Puzzle createGridFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> gridLines = new ArrayList<>();
            
            boolean readingGrid = true;
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("Points noirs :")) {
                    readingGrid = false;
                    continue;
                } else if (line.equals("Points blancs :")) {
                    readingGrid = false;
                    continue;
                }
                
                if (readingGrid) {
                    gridLines.add(line);
                }
            }

            reader.close();

            // Parse the grid values and create the puzzle
            int gridSize = gridLines.size();
            int[][] numbers = new int[gridSize][gridSize];

            for (int i = 0; i < gridSize; i++) {
                String[] values = gridLines.get(i).split(" ");
                for (int j = 0; j < gridSize; j++) {
                    numbers[i][j] = Integer.parseInt(values[j]);
                }
            }

            Puzzle puzzle = new Puzzle(numbers);

            // Process black dots and white dots
            //processBlackDots(puzzle, blackDotLines);
            //processWhiteDots(puzzle, whiteDotLines);

            return puzzle;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Puzzle parseKropkiGrid(String fileName) {
        int[][] grid = null; // Grid size will be determined dynamically
        int gridSize = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue; // Ignore empty lines
                }

                // Utilisation d'une expression régulière pour rechercher les lignes contenant uniquement des chiffres
                Pattern pattern = Pattern.compile("^(\\d+\\s+)+\\d+$");
                Matcher matcher = pattern.matcher(line);

                if (matcher.matches()) {
                    String[] values = line.split("\\s+");

                    if (grid == null) {
                        gridSize = values.length;
                        grid = new int[gridSize][gridSize];
                    }

                    if (values.length != gridSize) {
                        System.err.println("Nombre incorrect de colonnes dans la grille.");
                        return null;
                    }

                    for (int col = 0; col < gridSize; col++) {
                        grid[row][col] = Integer.parseInt(values[col]);
                    }
                    row++;
                }
            }

            if (grid == null) {
                System.err.println("Aucune donnée de grille trouvée.");
                return null;
            }

            if (row != gridSize) {
                System.err.println("Nombre incorrect de lignes dans la grille.");
                return null;
            }
            
            Puzzle puzzle = new Puzzle(grid);
            return puzzle;
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
        

    private static void processBlackDots(Puzzle puzzle, List<String> dotLines) {
        for (String line : dotLines) {
            // Parse the line and add black dots to the corresponding cells
            // For example, parse (1,1) - (1,2) and add a black dot between those cells
        }
    }

    private static void processWhiteDots(Puzzle puzzle, List<String> dotLines) {
        for (String line : dotLines) {
            // Parse the line and add white dots to the corresponding cells
            // For example, parse (1,3) - (1,4) and add a white dot between those cells
        }
    }

}