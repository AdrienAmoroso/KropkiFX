package com.kropkigame.view;

/**
 * L'interface SceneSwitcher définit les méthodes pour changer de scène dans l'interface utilisateur du jeu.
 */
public interface SceneSwitcher {
    /**
     * Change la scène pour afficher le jeu avec la difficulté et le numéro de niveau spécifiés.
     * @param difficulty La difficulté du jeu.
     * @param levelNumber Le numéro du niveau.
     */
    void switchToGame(String difficulty, int levelNumber);

    /**
     * Change la scène pour afficher la sélection de la difficulté.
     */
    void switchToDifficultySelection();

    /**
     * Change la scène pour afficher le premier menu.
     */
    void switchToFirstMenu();

    /**
     * Change la scène pour afficher la sélection du niveau avec la difficulté spécifiée.
     * @param difficulty La difficulté du jeu.
     */
    void showLevelSelection(String difficulty);
}