package com.kropkigame.utils;

import com.kropkigame.model.Puzzle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileData {

        public static Puzzle createGridFromFile(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> gridLines = new ArrayList<>();
            List<String> blackDotLines = new ArrayList<>();
            List<String> whiteDotLines = new ArrayList<>();
            
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
                } else if (line.contains("-")) {
                    if (line.contains("noirs")) {
                        blackDotLines.add(line);
                    } else if (line.contains("blancs")) {
                        whiteDotLines.add(line);
                    }
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
            processBlackDots(puzzle, blackDotLines);
            processWhiteDots(puzzle, whiteDotLines);

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