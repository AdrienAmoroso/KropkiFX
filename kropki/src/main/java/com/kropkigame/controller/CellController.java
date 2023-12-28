package com.kropkigame.controller;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;

import javafx.scene.input.MouseEvent;

/**
 * Représente le contrôleur pour chaque cellule dans la grille.
 */
public class CellController {
    private Cell selectedCell; // Pour suivre quelle cellule est actuellement sélectionnée
    private Puzzle model;
    private GameBoardPanel view;

    /**
     * Renvoie la cellule actuellement sélectionnée.
     * @return la cellule actuellement sélectionnée.
     */
    public Cell getSelectedCell() {
        return this.selectedCell;
    }

    /**
     * Définit la cellule actuellement sélectionnée.
     * @param selectedCell la cellule à sélectionner.
     */
    public void setSelectedCell(Cell cell) {
        this.selectedCell = cell;
    }

    /**
     * Renvoie le modèle.
     * @return le modèle.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Définit le modèle.
     * @param model le modèle à définir.
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Renvoie la vue.
     * @return la vue.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Définit la vue.
     * @param view la vue à définir.
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Construit un contrôleur de cellule avec le modèle et la vue spécifiés.
     * @param model le modèle.
     * @param view la vue.
     */
    public CellController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.selectedCell = null;
    }

    /**
     * Gère l'événement lorsqu'une cellule est sélectionnée.
     * @param event l'événement de la souris.
     * @param cell la cellule qui a été sélectionnée.
     */
    public void handleCellSelected(MouseEvent event, Cell cell) { 
        if (selectedCell != null) {
            // Réinitialise le style de la cellule précédemment sélectionnée en fonction de son état
            if (selectedCell.getIsError()) {
                selectedCell.setStyle(KropkiConstants.CELL_ERROR_STYLE);
            } else {
                selectedCell.setStyle(KropkiConstants.CELL_BORDER_STYLE);
            }
        }

        selectedCell = cell; // Définit la cellule actuelle comme la cellule sélectionnée
        highlightSelection(cell); // Met en évidence la cellule sélectionnée
    }

    /**
     * Gère l'événement lorsque le joueur clique sur un bouton pour rentrer un nombre dans une cellule.
     * @param number le nombre qui a été rentré.
     */
    public void handleNumberButtonClicked(int number) {
        if (selectedCell != null) {
            selectedCell.setNumber(number);
        }
    }

    /**
     * Met en évidence la cellule spécifiée.
     * @param cell la cellule à mettre en évidence.
     */
    private void highlightSelection(Cell cell) {
        if(cell.getIsError()) {
            cell.setStyle(KropkiConstants.CELL_ERROR_SELECTED_STYLE);
        } else {
            cell.setStyle(KropkiConstants.CELL_SELECTED_STYLE);
        }
    }
}