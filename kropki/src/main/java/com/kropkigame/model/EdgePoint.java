package com.kropkigame.model;

public class EdgePoint {
    private int sourceRow;
    private int sourceCol;
    private int targetRow;
    private int targetCol;
    private String type;

    public EdgePoint(int sourceRow, int sourceCol, int targetRow, int targetCol, String type) {
        this.sourceRow = sourceRow;
        this.sourceCol = sourceCol;
        this.targetRow = targetRow;
        this.targetCol = targetCol;
        this.type = type;
    }

    public int getSourceRow() {
        return this.sourceRow;
    }

    public void setSourceRow(int sourceRow) {
        this.sourceRow = sourceRow;
    }

    public int getSourceCol() {
        return this.sourceCol;
    }

    public void setSourceCol(int sourceCol) {
        this.sourceCol = sourceCol;
    }

    public int getTargetRow() {
        return this.targetRow;
    }

    public void setTargetRow(int targetRow) {
        this.targetRow = targetRow;
    }

    public int getTargetCol() {
        return this.targetCol;
    }

    public void setTargetCol(int targetCol) {
        this.targetCol = targetCol;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
