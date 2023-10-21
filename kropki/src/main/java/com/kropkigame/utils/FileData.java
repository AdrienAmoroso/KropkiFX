package com.kropkigame.utils;

import com.kropkigame.model.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileData {

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
}