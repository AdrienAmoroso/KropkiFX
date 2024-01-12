package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * Représente une cellule dans le plateau de jeu Kropki.
 */
public class Cell extends Pane {
    private int row;
    private int col;
    private int number; // Le numéro correct pour cette cellule.
    private Text textDisplay;
    private boolean isError;

    /**
     * Renvoie l'objet Text qui affiche le nombre dans la cellule.
     * @return l'objet Text qui affiche le nombre dans la cellule.
     */
    public Text getTextDisplay() {
        return this.textDisplay;
    }

    /**
     * Définit l'objet Text qui affiche le nombre dans la cellule.
     * @param textDisplay l'objet Text qui affiche le nombre dans la cellule.
     */
    public void setTextDisplay(Text textDisplay) {
        this.textDisplay = textDisplay;
    }

    /**
     * Construit une cellule avec la ligne et la colonne spécifiées.
     * @param row la ligne de la cellule.
     * @param col la colonne de la cellule.
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.isError = false;

        this.setPrefWidth(KropkiConstants.CELL_SIZE);
        this.setPrefHeight(KropkiConstants.CELL_SIZE); 
        this.setStyle(KropkiConstants.CELL_BORDER_STYLE);

        textDisplay = new Text();
        this.getChildren().add(textDisplay);
    }

    /**
     * Renvoie la ligne de la cellule.
     * @return la ligne de la cellule.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Définit la ligne de la cellule.
     * @param row la ligne de la cellule.
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Renvoie la colonne de la cellule.
     * @return la colonne de la cellule.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * Définit la colonne de la cellule.
     * @param col la colonne de la cellule.
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Renvoie le numéro de la cellule.
     * @return le numéro de la cellule.
     */
    public int getNumber() {
        return number;
    }

    /**
     * Définit le nombre de la cellule.
     * @param number le nombre de la cellule.
     */
    public void setNumber(int number) {
        this.number = number;
        if (number == 0) {
            textDisplay.setText("");
        } else {
            textDisplay.setText(String.valueOf(number));
        }
        textDisplay.setStyle(KropkiConstants.CELL_TEXT_STYLE);

        // Centre TextDisplay dans Pane
        double x = (this.getPrefWidth() - textDisplay.getBoundsInLocal().getWidth()) / 2;
        double y = (this.getPrefHeight() - textDisplay.getBoundsInLocal().getHeight()) / 2;
        textDisplay.relocate(x, y);
    }

    /**
     * @return vrai si la cellule a une erreur, faux sinon.
     */
    public boolean getIsError() {
        return this.isError;
    }

    /**
     * Définit le statut d'erreur de la cellule.
     * @param isError le statut d'erreur de la cellule.
     */
    public void setIsError(boolean isError) {
        this.isError = isError;
    }
}