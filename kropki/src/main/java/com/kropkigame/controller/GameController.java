package com.kropkigame.controller;

import com.kropkigame.model.Puzzle;
import com.kropkigame.view.GameBoardPanel;

public class GameController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;
    private GameBoardController gameBoardController;

    public Puzzle getModel() {
        return this.model;
    }

    public void setModel(Puzzle model) {
        this.model = model;
    }

    public GameBoardPanel getView() {
        return this.view;
    }

    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    public CellController getCellController() {
        return this.cellController;
    }

    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    public GameBoardController getGameBoardController() {
        return this.gameBoardController;
    }

    public void setGameBoardController(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    public GameController(int gridSize) {
        // Initialise le jeu en créant les instances nécessaires
        this.model = new Puzzle();
        this.view = new GameBoardPanel(gridSize);
        this.cellController = new CellController(model, view);
        this.gameBoardController = new GameBoardController(model, view, cellController);
    }

    public GameController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.cellController = new CellController(model, view);
        this.gameBoardController = new GameBoardController(model, view, cellController);
    }

    public void startGame() {
        // Initialise le plateau de jeu
        this.gameBoardController.initializeGameBoard();
        // Affiche le plateau de jeu à l'écran
        // D'autres actions d'initialisation si nécessaire
    }

    public void endGame(boolean isVictory) {
        // Gère la fin du jeu en fonction du résultat (victoire ou défaite)
        if (isVictory) {
            // Affiche un message de victoire
            System.out.println("Victoire !");
        } else {
            // Affiche un message de défaite
            System.out.println("Défaite !");
        }

        // Propose de recommencer ou de quitter le jeu
        // Réinitialise le jeu si l'utilisateur choisit de recommencer
        // Quitte le jeu si l'utilisateur choisit de quitter
    }

    // Autres méthodes pour gérer les règles spécifiques au jeu, les niveaux de difficulté, etc.
}

