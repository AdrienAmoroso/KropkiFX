package com.kropki.bot;

import com.kropkigame.controller.GameBoardController;
import com.kropkigame.view.Cell;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * La classe BotSolverImpl implémente l'interface BotSolver.
 * Elle est utilisée pour résoudre automatiquement le jeu Kropki.
 * Elle utilise l'algorithme de backtracking pour trouver une solution.
 */
public class BotSolverImpl implements BotSolver {
    private GameBoardController gameBoardController;
    private boolean isRunning;

    /**
     * Constructeur de la classe BotSolverImpl.
     * @param gameBoardController Le contrôleur du plateau de jeu.
     * @param view La vue du plateau de jeu.
     */
    public BotSolverImpl(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
        this.isRunning = false;
    }

    /**
     * Démarre le bot pour résoudre le jeu.
     */
    @Override
    public void startBot() {
        isRunning = true;
        new Thread(this::runBot).start();
    }

    /**
     * Arrête le bot.
     */
    @Override
    public void stopBot() {
        isRunning = false;
    }

    /**
     * Exécute le bot pour résoudre le jeu.
     * Utilise l'algorithme de backtracking pour trouver une solution.
     */
    private void runBot() {
        long startTime = System.currentTimeMillis();
    
        // Placer ici un chiffre valide automatiquement
        Platform.runLater(() -> {
            gameBoardController.resetGame();
            int correctRow = 0;
            int correctCol = 0;
            gameBoardController.getView().getCell(correctRow, correctCol).setNumber(gameBoardController.getModel().getNumber(correctRow, correctCol));
        });
    
        try {
            Thread.sleep(1000); // Réglable selon la vitesse souhaitée
            Platform.runLater(() -> {
                if (solveWithBacktracking(0, 0)) {
                    if (isRunning) { // Vérifier si le bot est toujours en cours d'exécution
                        if (gameBoardController.getTimeline() != null) { // Arrêt du chrono
                            gameBoardController.getTimeline().stop();
                        }

                        long endTime = System.currentTimeMillis();
                        showBotVictoryMessage(startTime, endTime);
                    }
                    stopBot(); // Arrête le bot si une solution est trouvée
                } else {
                    showNoSolutionFoundMessage();
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tente de résoudre le jeu en utilisant l'algorithme de backtracking.
     * @param row La ligne actuelle.
     * @param col La colonne actuelle.
     * @return true si une solution a été trouvée, false sinon.
     */
    private boolean solveWithBacktracking(int row, int col) {
        if (row == gameBoardController.getView().getGridSize()) { // Si toutes les lignes sont parcourues
            return true;
        }

        int nextRow = (col == gameBoardController.getView().getGridSize() - 1) ? row + 1 : row;
        int nextCol = (col == gameBoardController.getView().getGridSize() - 1) ? 0 : col + 1;

        if (gameBoardController.getView().getCell(row, col).getNumber() != 0) {
            return solveWithBacktracking(nextRow, nextCol);
        }

        for (int num = 1; num <= gameBoardController.getView().getGridSize(); num++) {
            if (isValidPlacement(row, col, num)) {
                gameBoardController.getView().getCell(row, col).setNumber(num);
                if (solveWithBacktracking(nextRow, nextCol)) {
                    return true;
                }
                gameBoardController.getView().getCell(row, col).setNumber(0); // Effacer et revenir en arrière
            }
        }

        return false; // Aucun numéro valide trouvé pour cette cellule
    }

    /**
     * Vérifie si le placement d'un nombre dans une cellule est valide.
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @param num Le nombre à placer.
     * @return true si le placement est valide, false sinon.
     */
    private boolean isValidPlacement(int row, int col, int num) {
    
        // Vérifier si le numéro est déjà dans la même ligne ou colonne
        for (int i = 0; i < gameBoardController.getView().getGridSize(); i++) {
            if (gameBoardController.getView().getCell(row, i).getNumber() == num || gameBoardController.getView().getCell(i, col).getNumber() == num) {
                return false;
            }
        }
    
        // Vérifier les contraintes des points noirs et blancs pour les cellules adjacentes
        for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
            int adjRow = row + direction[0];
            int adjCol = col + direction[1];
    
            if (isValidCell(adjRow, adjCol)) {
                Cell adjacentCell = gameBoardController.getView().getCell(adjRow, adjCol);
                int adjacentNumber = adjacentCell.getNumber();

                // Vérifier les points noirs
                if (gameBoardController.getModel().existsBlackEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    || gameBoardController.getModel().existsBlackEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    // Vérifie si la règle du point noir est respectée ou si la cellule adjacente est vide
                    if (adjacentNumber != 0 && !((num == 1 && adjacentNumber == 2) || (num == 2 && adjacentNumber == 1) || (num == 2 * adjacentNumber) || (adjacentNumber == 2 * num))) {
                        return false;
                    }
                }

                // Vérifier les points blancs
                if (gameBoardController.getModel().existsWhiteEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    || gameBoardController.getModel().existsWhiteEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    // Vérifie si la règle du point blanc est respectée ou si la cellule adjacente est vide
                    if (adjacentNumber != 0 && Math.abs(num - adjacentNumber) != 1) {
                        return false;
                    }
                }

                // Vérifier l'absence de point
                if (!gameBoardController.getModel().existsBlackEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1) && !gameBoardController.getModel().existsWhiteEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    && !gameBoardController.getModel().existsBlackEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1) && !gameBoardController.getModel().existsWhiteEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    if (adjacentNumber != 0 && (Math.abs(num - adjacentNumber) == 1 || num == 2 * adjacentNumber || adjacentNumber == 2 * num)) {
                        return false;
                    }
                }

            }
        }
    
        return true; // Si toutes les vérifications sont passées, le placement est valide
    }
    
    /**
     * Vérifie si une cellule est valide.
     * @param row La ligne de la cellule.
     * @param col La colonne de la cellule.
     * @return true si la cellule est valide, false sinon.
     */
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < gameBoardController.getView().getGridSize() && col >= 0 && col < gameBoardController.getView().getGridSize();
    }

    /**
     * Affiche un message de victoire lorsque le bot a résolu le jeu.
     * @param startTime Le temps de début de l'exécution du bot.
     * @param endTime Le temps de fin de l'exécution du bot.
     */
    private void showBotVictoryMessage(long startTime, long endTime) {
        long time = endTime - startTime; // Temps d'exécution de l'algo en ms

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Le bot a résolu le puzzle en " + time + "ms.");
        alert.showAndWait();
    }
    
    /**
     * Affiche un message d'erreur lorsque le bot n'a pas réussi à résoudre le jeu.
     */
    private void showNoSolutionFoundMessage() {
        Alert alert = new Alert(AlertType.WARNING);

        alert.setTitle("Echec");
        alert.setHeaderText(null);
        alert.setContentText("Le bot n'a pas réussi à résoudre la grille.");
        alert.showAndWait();
    }
}
