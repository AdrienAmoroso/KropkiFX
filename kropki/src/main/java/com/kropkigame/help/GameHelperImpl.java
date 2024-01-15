package com.kropkigame.help;

import java.util.HashSet;
import java.util.Set;

import com.kropkigame.controller.GameBoardController;
import com.kropkigame.view.Cell;

/**
 * Implémentation de l'interface GameHelper.
 * Cette classe permet de fournir une aide au joueur en remplissant automatiquement
 * certaines cases de la grille, en fonction des règles de Kropki.
 */
public class GameHelperImpl implements GameHelper {
    private GameBoardController gameBoardController;

    public GameHelperImpl(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    /**
     * Fournit une aide en remplissant automatiquement certaines cases de la grille,
     * en fonction des règles de Kropki.
     */
    /*public void provideHelp() {
        int gridSize = gameBoardController.getModel().getGridSize();
        boolean filledACell;
    
        do {
            filledACell = false; // Réinitialiser pour chaque itération
    
            // Obtenir la cellule sélectionnée par le joueur
            Cell selectedCell = gameBoardController.getCellController().getSelectedCell();
            if (selectedCell != null && selectedCell.getNumber() != 0) {
                int selectedRow = selectedCell.getRow();
                int selectedCol = selectedCell.getCol();
    
                // Utiliser la valeur de la cellule sélectionnée pour remplir les cellules adjacentes
                filledACell = fillAdjacentCellsBasedOnPoints(selectedRow, selectedCol);
            }
    
            // Parcourir toutes les cellules pour la logique de remplissage des lignes / colonnes
            for (int row = 0; row < gridSize; row++) {
                for (int col = 0; col < gridSize; col++) {
                    Cell thisCell = gameBoardController.getView().getCell(row, col);
    
                    // Si thisCell est vide, vérifier si une valeur manquante peut être déterminée
                    if (thisCell.getNumber() == 0) {
                        if (canDetermineValueFromLineAndColumn(row, col)) {
                            filledACell = true; // Marquer qu'une cellule a été remplie
                        }
                    }

                    // Si thisCell est vide, vérifier si une valeur manquante peut être déterminée
                    if (thisCell.getNumber() == 0) {
                        int missingValue = determineMissingValue(row, col);
                        if (missingValue > 0) {
                            thisCell.setNumber(missingValue); // Remplir la case avec la valeur manquante
                            filledACell = true; // Marquer qu'une cellule a été remplie
                        }
                    }
                }
            }
        } while (filledACell); // Continuer tant qu'au moins une case est remplie à chaque itération
    }*/

    @Override
    public void provideHelp() {
        int gridSize = gameBoardController.getModel().getGridSize();
        
        // Parcourir toutes les cellules pour la logique de remplissage des lignes / colonnes
        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                Cell thisCell = gameBoardController.getView().getCell(row, col);
        
                // Si thisCell est vide, vérifier si une valeur manquante peut être déterminée
                if (thisCell.getNumber() == 0) {
                    int missingValue = determineMissingValue(row, col);
                    if (missingValue > 0) {
                        thisCell.setNumber(missingValue); // Remplir la case avec la valeur manquante
                    }
                }
            }
        }

        // Aide pour la cellule sélectionnée avec des points noirs et blancs
        Cell selectedCell = gameBoardController.getCellController().getSelectedCell();
        if (selectedCell != null && selectedCell.getNumber() != 0) {
            int selectedRow = selectedCell.getRow();
            int selectedCol = selectedCell.getCol();

            fillAdjacentCellsBasedOnPoints(selectedRow, selectedCol);
        }
    }

    /**
     * Permet de savoir si on peut déterminer la valeur manquante dans une
     * configuration de jeu précise.
     * @param row la ligne de la cellule.
     * @param col la colonne de la cellule.
     * @return vrai si une valeur unique est possible, faux sinon.
     */
    public boolean canDetermineValueFromLineAndColumn(int row, int col) {
        Set<Integer> possibleValues = new HashSet<>();
        for (int i = 1; i <= gameBoardController.getModel().getGridSize(); i++) {
            possibleValues.add(i);
        }
    
        // Retirer les valeurs déjà présentes dans la ligne et la colonne
        for (int i = 0; i < gameBoardController.getModel().getGridSize(); i++) {
            possibleValues.remove(gameBoardController.getView().getCell(row, i).getNumber());
            possibleValues.remove(gameBoardController.getView().getCell(i, col).getNumber());
        }
    
        // Si seulement une valeur est possible, retourner true
        if (possibleValues.size() == 1) {
            gameBoardController.getView().getCell(row, col).setNumber(possibleValues.iterator().next());
            return true;
        }
        return false;
    }   

    /**
     * Remplit les cellules adjacentes à la cellule spécifiée en fonction des points adjacents.
     * @param row
     * @param col
     * @return true si au moins une cellule a été remplie, false sinon
     */
    private boolean fillAdjacentCellsBasedOnPoints(int row, int col) {
        int gridSize = gameBoardController.getModel().getGridSize();
        boolean filled = false;
    
        // Parcourir les cellules adjacentes
        for (int dRow = -1; dRow <= 1; dRow++) {
            for (int dCol = -1; dCol <= 1; dCol++) {
                if ((dRow == 0) != (dCol == 0)) { // Ignorer la cellule elle-même et les diagonales
                    int adjacentRow = row + dRow;
                    int adjacentCol = col + dCol;
    
                    if (adjacentRow >= 0 && adjacentRow < gridSize && adjacentCol >= 0 && adjacentCol < gridSize) {
                        int existingValue = gameBoardController.getView().getCell(adjacentRow, adjacentCol).getNumber();
                        int valueFromPoints = determineValueFromPoints(row, col, adjacentRow, adjacentCol);
    
                        if (valueFromPoints > 0 && existingValue != valueFromPoints) {
                            gameBoardController.getView().getCell(adjacentRow, adjacentCol).setNumber(valueFromPoints);
                            filled = true;
                        }
                    }
                }
            }
        }
    
        return filled;
    }

    /**
     * Détermine la valeur d'une cellule en fonction des points adjacents.
     * @param row
     * @param col
     * @return la valeur de la cellule
     */
    private int determineValueFromPoints(int row, int col, int adjacentRow, int adjacentCol) {
        int gridSize = gameBoardController.getModel().getGridSize();
        int currentValue = gameBoardController.getView().getCell(row, col).getNumber();
        int cellRow = row + 1;
        int cellCol = col + 1;
        int adjacentCellRow = adjacentRow + 1;
        int adjacentCellCol = adjacentCol + 1;
    
        // Logique pour un point noir
        if (gameBoardController.getModel().existsBlackEdgePoint(cellRow, cellCol, adjacentCellRow, adjacentCellCol) || gameBoardController.getModel().existsBlackEdgePoint(adjacentCellRow, adjacentCellCol, cellRow, cellCol)) {
            if (currentValue == 1) {
                return 2;
            } else if (currentValue == gridSize) {
                return gridSize / 2;
            }
        }
        // Logique pour un point blanc
        else if (gameBoardController.getModel().existsWhiteEdgePoint(cellRow, cellCol, adjacentCellRow, adjacentCellCol) || gameBoardController.getModel().existsWhiteEdgePoint(adjacentCellRow, adjacentCellCol, cellRow, cellCol)) {
            if (currentValue == 1) {
                return 2;
            } else if (currentValue == gridSize) {
                return gridSize - 1;
            }
        }
    
        return 0; // Retourner 0 si aucune valeur déterminée
    }

    /**
     * Détermine la valeur d'une cellule en fonction des nombres dans la ligne et la colonne.
     * @param row
     * @param col
     * @return la valeur de la cellule
     */
    private int determineMissingValue(int row, int col) {
        int size = gameBoardController.getModel().getGridSize();
        int missingValueForRow = size * (size + 1) / 2; // La somme de tous les nombres attendus dans une ligne
        int missingValueForColumn = missingValueForRow; // La somme de tous les nombres attendus dans une colonne
        int filledCellsInRow = 0; // Compteur pour le nombre de cellules remplies dans la ligne
        int filledCellsInColumn = 0; // Compteur pour le nombre de cellules remplies dans la colonne

        for (int i = 0; i < size; i++) {
            if (gameBoardController.getView().getCell(row, i).getNumber() != 0) {
                missingValueForRow -= gameBoardController.getView().getCell(row, i).getNumber(); // Soustraire les valeurs de la ligne
                filledCellsInRow++;
            }
            if (gameBoardController.getView().getCell(i, col).getNumber() != 0) {
                missingValueForColumn -= gameBoardController.getView().getCell(i, col).getNumber(); // Soustraire les valeurs de la colonne
                filledCellsInColumn++;
            }
        }

        // Compléter la ligne ou la colonne seulement s'il y a "gridSize - 1" chiffres rentrés
        if (filledCellsInRow == size - 1 && missingValueForRow > 0 && missingValueForRow <= size) {
            return missingValueForRow;
        } else if (filledCellsInColumn == size - 1 && missingValueForColumn > 0 && missingValueForColumn <= size) {
            return missingValueForColumn;
        }

        return 0; // Retourner 0 si aucune valeur déterminée
    }
}