package com.kropkigame.utils;

/**
 * Classe utilisée pour stocker les actions de l'utilisateur (quel chiffre il a rentré et à quelle position).
 */
public class Action {
    private final int row;
    private final int col;
    private final int number;

    public Action(int row, int col, int number) {
        this.row = row;
        this.col = col;
        this.number = number;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getNumber() {
        return number;
    }
}
