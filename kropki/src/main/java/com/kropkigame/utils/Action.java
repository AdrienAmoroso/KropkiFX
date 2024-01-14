package com.kropkigame.utils;

/**
 * Classe utilisée pour stocker les actions de l'utilisateur (quel chiffre il a rentré et à quelle position).
 */
public class Action {
    private final int row;
    private final int col;
    private final int number;

    /**
     * Constructeur de la classe Action.
     * @param row La ligne où l'utilisateur a entré le chiffre.
     * @param col La colonne où l'utilisateur a entré le chiffre.
     * @param number Le chiffre que l'utilisateur a entré.
     */
    public Action(int row, int col, int number) {
        this.row = row;
        this.col = col;
        this.number = number;
    }

    /**
     * Récupère la ligne où l'utilisateur a entré le chiffre.
     * @return La ligne où l'utilisateur a entré le chiffre.
     */
    public int getRow() {
        return row;
    }

    /**
     * Récupère la colonne où l'utilisateur a entré le chiffre.
     * @return La colonne où l'utilisateur a entré le chiffre.
     */
    public int getCol() {
        return col;
    }

    /**
     * Récupère le chiffre que l'utilisateur a entré.
     * @return Le chiffre que l'utilisateur a entré.
     */
    public int getNumber() {
        return number;
    }
}