package com.kropkigame.utils;

import com.kropkigame.model.EdgePoint;
import com.kropkigame.model.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe utilisée pour la manipulation de fichiers.
 */
public class FileData {

    /**
     * Analyse une grille Kropki à partir d'un fichier.
     *
     * @param fileName le nom du fichier contenant les données de la grille
     * @return un objet Puzzle représentant la grille
     */
    public static Puzzle parseKropkiGrid(String fileName) {
        int[][] grid = null; // La taille de la grille sera déterminée dynamiquement
        int gridSize = 0;
        ArrayList<EdgePoint> edgePoints = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            boolean isBlackSection = false;
            boolean isWhiteSection = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty()) {
                    continue; // Ignore les lignes vides
                }

                if (line.equals("Points noirs :")) {
                    isBlackSection = true;
                    isWhiteSection = false;
                    continue;
                }

                if (line.equals("Points blancs :")) {
                    isBlackSection = false;
                    isWhiteSection = true;
                    continue;
                }

                // Utilise une expression régulière pour rechercher les lignes contenant uniquement des nombres
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
                } else {
                    // Essaye de correspondre au format d'écriture des points
                    pattern = Pattern.compile("\\((\\d+),(\\d+)\\)\\s*-\\s*\\((\\d+),(\\d+)\\)");
                    matcher = pattern.matcher(line);

                    if (matcher.matches()) {
                        int sourceRow = Integer.parseInt(matcher.group(1));
                        int sourceCol = Integer.parseInt(matcher.group(2));
                        int targetRow = Integer.parseInt(matcher.group(3));
                        int targetCol = Integer.parseInt(matcher.group(4));

                        String type = isBlackSection ? "black" : (isWhiteSection ? "white" : "unknown");
                        edgePoints.add(new EdgePoint(sourceRow, sourceCol, targetRow, targetCol, type));
                    }
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

            Puzzle puzzle = new Puzzle(grid, gridSize);
            puzzle.setEdgePoints(edgePoints);
            return puzzle;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}