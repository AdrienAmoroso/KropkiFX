package com.kropki.bot;

/**
 * L'interface BotSolver définit les méthodes de base pour un bot résolveur de jeu.
 * Elle est utilisée pour démarrer et arrêter le bot.
 */
public interface BotSolver {
    /**
     * Démarre le bot pour résoudre le jeu.
     * Cette méthode doit être implémentée pour démarrer le processus de résolution du jeu.
     */
    void startBot();

    /**
     * Arrête le bot.
     * Cette méthode doit être implémentée pour arrêter le processus de résolution du jeu.
     */
    void stopBot();
}