package com.kropkigame.controller;

/**
 * L'interface GameBoardActions définit les actions qui peuvent être effectuées sur le plateau de jeu.
 */
public interface GameBoardActions {
    /**
     * Réinitialise le jeu à son état initial.
     */
    void resetGame();

    /**
     * Gère l'action du bouton de retour en arrière.
     */
    void handleBackButton();
}