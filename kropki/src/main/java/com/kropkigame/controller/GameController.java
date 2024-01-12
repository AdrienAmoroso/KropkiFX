package com.kropkigame.controller;

import com.kropkigame.model.Puzzle;
import com.kropkigame.view.GameBoardPanel;

/**
 * Représente le contrôleur du jeu, qui contrôle le jeu en général.
 */
public class GameController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;
    private GameBoardController gameBoardController;

    /**
     * Obtient le modèle du jeu.
     * @return le modèle du jeu.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Définit le modèle du jeu.
     * @param model le modèle du jeu.
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Obtient la vue du jeu.
     * @return la vue du jeu.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Définit la vue du jeu.
     * @param view la vue du jeu.
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Obtient le contrôleur de cellule du jeu.
     * @return le contrôleur de cellule du jeu.
     */
    public CellController getCellController() {
        return this.cellController;
    }

    /**
     * Définit le contrôleur de cellule du jeu.
     * @param cellController le contrôleur de cellule du jeu.
     */
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    /**
     * Obtient le contrôleur du plateau de jeu.
     * @return le contrôleur du plateau de jeu.
     */
    public GameBoardController getGameBoardController() {
        return this.gameBoardController;
    }

    /**
     * Définit le contrôleur du plateau de jeu.
     * @param gameBoardController le contrôleur du plateau de jeu.
     */
    public void setGameBoardController(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    /**
     * Construit un contrôleur de jeu avec la taille de grille spécifiée.
     * @param gridSize la taille de la grille du jeu.
     */
    // public GameController(int gridSize) {
    //     // Initialise le jeu en créant les instances nécessaires
    //     this.model = new Puzzle();
    //     this.view = new GameBoardPanel(gridSize);
    //     this.cellController = new CellController(model, view);
    //     this.gameBoardController = new GameBoardController(model, view, cellController);
    // }

    /**
     * Construit un contrôleur de jeu avec le modèle et la vue spécifiés.
     * @param model le modèle du jeu.
     * @param view la vue du jeu.
     */
    public GameController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.cellController = new CellController(model, view);
        this.gameBoardController = new GameBoardController(model, view, cellController);
    }

    /**
     * Démarre le jeu.
     */
    public void startGame() {
        // Initialise le plateau de jeu
        this.gameBoardController.initializeGameBoard();
    }
}