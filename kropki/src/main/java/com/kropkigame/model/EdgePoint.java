package com.kropkigame.model;

/**
 * Représente un point de bord dans le jeu Kropki.
 * Un point de bord relie deux cellules sur le plateau de jeu.
 */
public class EdgePoint {
    private int sourceRow;
    private int sourceCol;
    private int targetRow;
    private int targetCol;
    private String type;

    /**
     * Construit un nouvel objet EdgePoint avec les coordonnées et le type spécifiés.
     *
     * @param sourceRow l'indice de ligne de la cellule source
     * @param sourceCol l'indice de colonne de la cellule source
     * @param targetRow l'indice de ligne de la cellule cible
     * @param targetCol l'indice de colonne de la cellule cible
     * @param type      le type du point de bord (noir ou blanc)
     */
    public EdgePoint(int sourceRow, int sourceCol, int targetRow, int targetCol, String type) {
        this.sourceRow = sourceRow;
        this.sourceCol = sourceCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.type = type;
    }

    /**
     * Retourne l'indice de ligne de la cellule source.
     *
     * @return l'indice de ligne de la cellule source
     */
    public int getSourceRow() {
        return this.sourceRow;
    }

    /**
     * Définit l'indice de ligne de la cellule source.
     *
     * @param sourceRow l'indice de ligne de la cellule source
     */
    public void setSourceRow(int sourceRow) {
        this.sourceRow = sourceRow;
    }

    /**
     * Retourne l'indice de colonne de la cellule source.
     *
     * @return l'indice de colonne de la cellule source
     */
    public int getSourceCol() {
        return this.sourceCol;
    }

    /**
     * Définit l'indice de colonne de la cellule source.
     *
     * @param sourceCol l'indice de colonne de la cellule source
     */
    public void setSourceCol(int sourceCol) {
        this.sourceCol = sourceCol;
    }

    /**
     * Retourne l'indice de ligne de la cellule cible.
     *
     * @return l'indice de ligne de la cellule cible
     */
    public int getTargetRow() {
        return this.targetRow;
    }

    /**
     * Définit l'indice de ligne de la cellule cible.
     *
     * @param targetRow l'indice de ligne de la cellule cible
     */
    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    /**
     * Retourne l'indice de colonne de la cellule cible.
     *
     * @return l'indice de colonne de la cellule cible
     */
    public int getTargetCol() {
        return this.targetCol;
    }

    /**
     * Définit l'indice de colonne de la cellule cible.
     *
     * @param targetCol l'indice de colonne de la cellule cible
     */
    public void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }

    /**
     * Retourne le type du point de bord.
     *
     * @return le type du point de bord
     */
    public String getType() {
        return this.type;
    }

    /**
     * Définit le type du point de bord.
     *
     * @param type le type du point de bord
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retourne une représentation en chaîne de caractères de l'objet EdgePoint.
     *
     * @return une représentation en chaîne de caractères de l'objet EdgePoint
     */
    @Override
    public String toString() {
        return "{" +
            " sourceRow='" + getSourceRow() + "'" +
            ", sourceCol='" + getSourceCol() + "'" +
            ", targetRow='" + getTargetRow() + "'" +
            ", targetCol='" + getTargetCol() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}