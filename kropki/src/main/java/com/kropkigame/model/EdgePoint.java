package com.kropkigame.model;

/**
 * Represents an edge point in the Kropki game.
 * An edge point connects two cells on the game board.
 */
public class EdgePoint {
    private int sourceRow;
    private int sourceCol;
    private int targetRow;
    private int targetCol;
    private String type;

    /**
     * Constructs a new EdgePoint object with the specified coordinates and type.
     *
     * @param sourceRow the row index of the source cell
     * @param sourceCol the column index of the source cell
     * @param targetRow the row index of the target cell
     * @param targetCol the column index of the target cell
     * @param type      the type of the edge point (black or white)
     */
    public EdgePoint(int sourceRow, int sourceCol, int targetRow, int targetCol, String type) {
        this.sourceRow = sourceRow;
        this.sourceCol = sourceCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.type = type;
    }

    /**
     * Returns the row index of the source cell.
     *
     * @return the row index of the source cell
     */
    public int getSourceRow() {
        return this.sourceRow;
    }

    /**
     * Sets the row index of the source cell.
     *
     * @param sourceRow the row index of the source cell
     */
    public void setSourceRow(int sourceRow) {
        this.sourceRow = sourceRow;
    }

    /**
     * Returns the column index of the source cell.
     *
     * @return the column index of the source cell
     */
    public int getSourceCol() {
        return this.sourceCol;
    }

    /**
     * Sets the column index of the source cell.
     *
     * @param sourceCol the column index of the source cell
     */
    public void setSourceCol(int sourceCol) {
        this.sourceCol = sourceCol;
    }

    /**
     * Returns the row index of the target cell.
     *
     * @return the row index of the target cell
     */
    public int getTargetRow() {
        return this.targetRow;
    }

    /**
     * Sets the row index of the target cell.
     *
     * @param targetRow the row index of the target cell
     */
    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    /**
     * Returns the column index of the target cell.
     *
     * @return the column index of the target cell
     */
    public int getTargetCol() {
        return this.targetCol;
    }

    /**
     * Sets the column index of the target cell.
     *
     * @param targetCol the column index of the target cell
     */
    public void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }

    /**
     * Returns the type of the edge point.
     *
     * @return the type of the edge point
     */
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type of the edge point.
     *
     * @param type the type of the edge point
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns a string representation of the EdgePoint object.
     *
     * @return a string representation of the EdgePoint object
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
