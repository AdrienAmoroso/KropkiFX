package com.kropkigame.controller;

import com.kropkigame.model.Puzzle;
import com.kropkigame.view.GameBoardPanel;

/**
 * Represents the game controller, which controls the game in general.
 */
public class GameController {
    private Puzzle model;
    private GameBoardPanel view;
    private CellController cellController;
    private GameBoardController gameBoardController;

    /**
     * Gets the model of the game.
     * @return the model of the game.
     */
    public Puzzle getModel() {
        return this.model;
    }

    /**
     * Sets the model of the game.
     * @param model the model of the game.
     */
    public void setModel(Puzzle model) {
        this.model = model;
    }

    /**
     * Gets the view of the game.
     * @return the view of the game.
     */
    public GameBoardPanel getView() {
        return this.view;
    }

    /**
     * Sets the view of the game.
     * @param view the view of the game.
     */
    public void setView(GameBoardPanel view) {
        this.view = view;
    }

    /**
     * Gets the cell controller of the game.
     * @return the cell controller of the game.
     */
    public CellController getCellController() {
        return this.cellController;
    }

    /**
     * Sets the cell controller of the game.
     * @param cellController the cell controller of the game.
     */
    public void setCellController(CellController cellController) {
        this.cellController = cellController;
    }

    /**
     * Gets the game board controller of the game.
     * @return the game board controller of the game.
     */
    public GameBoardController getGameBoardController() {
        return this.gameBoardController;
    }

    /**
     * Sets the game board controller of the game.
     * @param gameBoardController the game board controller of the game.
     */
    public void setGameBoardController(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    /**
     * Constructs a game controller with the specified grid size.
     * @param gridSize the grid size of the game.
     */
    public GameController(int gridSize) {
        // Initialise le jeu en créant les instances nécessaires
        this.model = new Puzzle();
        this.view = new GameBoardPanel(gridSize);
        this.cellController = new CellController(model, view);
        this.gameBoardController = new GameBoardController(model, view, cellController);
    }

    /**
     * Constructs a game controller with the specified model and view.
     * @param model the model of the game.
     * @param view the view of the game.
     */
    public GameController(Puzzle model, GameBoardPanel view) {
        this.model = model;
        this.view = view;
        this.cellController = new CellController(model, view);
        this.gameBoardController = new GameBoardController(model, view, cellController);
    }

    /**
     * Starts the game.
     */
    public void startGame() {
        // Initialize the board game
        this.gameBoardController.initializeGameBoard();
        // Displays the game board
        // Other methods to start the game
    }

    /**
     * Ends the game.
     * @param isVictory true if the player wins the game, false otherwise.
     */
    public void endGame(boolean isVictory) {
        // Displays a message depending on whether the player wins or loses the game
        if (isVictory) {
            // Display the victory message
            System.out.println("Victoire !");
        } else {
            // Display the defeat message
            System.out.println("Défaite !");
        }

        // Offers
        // Reset the game if the player chooses to play again
        // Leaves the game if the player chooses to leave
    }

    // Other methods .
}

