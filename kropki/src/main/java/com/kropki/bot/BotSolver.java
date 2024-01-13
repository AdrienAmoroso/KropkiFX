package com.kropki.bot;

/**
 * L'interface BotSolver définit les méthodes de base pour un bot résolveur de jeu.
 * Elle est utilisée pour démarrer et arrêter le bot.
 */
public interface BotSolver {
    /**
     * Démarre le bot pour résoudre le jeu.
     */
    void startBot();

    /**
     * Arrête le bot.
     */
    void stopBot();
}