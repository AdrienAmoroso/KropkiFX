package com.kropkigame.model;

import java.util.ArrayList;

/**
 * Représente un puzzle Kropki.
 */
public class Puzzle implements KropkiConstants {
    private int[][] numbers;
    private int gridSize;
    private ArrayList<EdgePoint> edgePoints;

    /**
     * Construit un puzzle Kropki vide avec une taille de grille par défaut de 4x4.
     */
    public Puzzle() {
        // Initialise un problème de grille Kropki vide:
        this.gridSize = 4;
        this.numbers = new int[gridSize][gridSize];
        
        // Met tous les nombres à zéro
        for (int i = 0; i < this.gridSize; i++) {
            for (int j = 0; j < this.gridSize; j++) {
                numbers[i][j] = 0;
            }
        }

        // Initialise une liste vide de points de bord
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    /**
     * Construit un puzzle Kropki avec les nombres et la taille de grille spécifiés.
     * 
     * @param numbers Les nombres de la grille de puzzle.
     * @param gridSize La taille de la grille de puzzle.
     */
    public Puzzle(int[][] numbers, int gridSize) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = new ArrayList<EdgePoint>();
    }

    /**
     * Construit un puzzle Kropki avec les nombres, la taille de grille et les points de bord spécifiés.
     * 
     * @param numbers Les nombres de la grille de puzzle.
     * @param gridSize La taille de la grille de puzzle.
     * @param edgePoints Les points de bord du puzzle.
     */
    public Puzzle(int[][] numbers, int gridSize, ArrayList<EdgePoint> edgePoints) {
        this.gridSize = gridSize;
        this.numbers = numbers;
        this.edgePoints = edgePoints;
    }

    /**
     * Obtient le nombre à la ligne et à la colonne spécifiées dans la grille de puzzle.
     * 
     * @param row L'indice de ligne.
     * @param col L'indice de colonne.
     * @return Le nombre à la position spécifiée.
     * @throws IndexOutOfBoundsException si l'indice de ligne ou de colonne est hors limites.
     */
    public int getNumber(int row, int col) {
        if (row < 0 || row >= this.gridSize || col < 0 || col >= this.gridSize) {
            throw new IndexOutOfBoundsException("Les indices ne doivent pas dépasser la taille de la grille.");
        }
        
        return this.numbers[row][col];
    }
    
    /**
     * Obtient une copie des nombres dans la grille de puzzle.
     * 
     * @return Une copie des nombres dans la grille de puzzle.
     */
    public int[][] getNumbers() {
        int[][] numbersList = this.numbers.clone();
        return numbersList;
    }

    /**
     * Définit les nombres dans la grille de puzzle.
     * 
     * @param numbers Les nombres à définir.
     */
    public void setNumbers(int[][] numbers) {
        this.numbers = numbers;
    }

    /**
     * Obtient la taille de la grille de puzzle.
     * 
     * @return La taille de la grille de puzzle.
     */
    public int getGridSize() {
        return this.gridSize;
    }

    /**
     * Définit la taille de la grille de puzzle.
     * 
     * @param gridSize La taille de la grille de puzzle.
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Obtient les points du puzzle.
     * 
     * @return Les points du puzzle.
     */
    public ArrayList<EdgePoint> getEdgePoints() {
        return this.edgePoints;
    }

    /**
     * Définit les points du puzzle.
     * 
     * @param edgePoints Les points à définir.
     */
    public void setEdgePoints(ArrayList<EdgePoint> edgePoints) {
        this.edgePoints = edgePoints;
    }

    /**
     * Ajoute un point au puzzle.
     * 
     * @param edgePoint Le point de bord à ajouter.
     */
    public void addEdgePoint(EdgePoint edgePoint) {
        if (!edgePoints.contains(edgePoint)) {
            edgePoints.add(edgePoint);
        }
    }

    /**
     * Vérifie si un point noir existe entre les cellules source et cible spécifiées.
     * 
     * @param sourceRow L'indice de ligne de la position source.
     * @param sourceCol L'indice de colonne de la position source.
     * @param targetRow L'indice de ligne de la position cible.
     * @param targetCol L'indice de colonne de la position cible.
     * @return true si un point noir existe, false sinon.
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
     * Vérifie si un point blanc existe entre les cellules source et cible spécifiées.
     * 
     * @param sourceRow L'indice de ligne de la position source.
     * @param sourceCol L'indice de colonne de la position source.
     * @param targetRow L'indice de ligne de la position cible.
     * @param targetCol L'indice de colonne de la position cible.
     * @return true si un point blanc existe, false sinon.
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
     * Retourne une représentation en chaîne de caractères du puzzle.
     * 
     * @return Une représentation en chaîne de caractères du puzzle.
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
