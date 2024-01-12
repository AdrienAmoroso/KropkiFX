package com.kropki.bot;

import com.kropkigame.controller.GameBoardController;
import com.kropkigame.view.Cell;
import com.kropkigame.view.GameBoardPanel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class BotSolverImpl implements BotSolver {
    private GameBoardController gameBoardController;
    private GameBoardPanel view;
    private boolean isRunning;

    public BotSolverImpl(GameBoardController gameBoardController, GameBoardPanel view) {
        this.gameBoardController = gameBoardController;
        this.view = view;
    }

    @Override
    public void startBot() {
        isRunning = true;
        new Thread(this::runBot).start();
    }

    @Override
    public void stopBot() {
        isRunning = false;
    }

    private void runBot() {
        long startTime = System.currentTimeMillis();
    
        // Placer ici un chiffre valide automatiquement
        gameBoardController.resetGame();
        int correctRow = 0;
        int correctCol = 0;
        view.getCell(correctRow, correctCol).setNumber(gameBoardController.getModel().getNumber(correctRow, correctCol));
    
        try {
            Thread.sleep(1000); // Réglable selon la vitesse souhaitée
            Platform.runLater(() -> {
                if (solveWithBacktracking(0, 0)) {
                    if (isRunning) { // Vérifier si le bot est toujours en cours d'exécution
                        if (gameBoardController.getTimeline() != null) { // Arrêt du chrono
                            gameBoardController.getTimeline().stop();
                        }

                        long endTime = System.currentTimeMillis();
                        System.out.println("[BotSolver] Solution trouvée en " + (endTime - startTime) + " ms!");
                        showVictoryMessage(startTime, endTime);
                    }
                    stopBot(); // Arrête le bot si une solution est trouvée
                } else {
                    System.out.println("[BotSolver] Aucune solution trouvée.");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean solveWithBacktracking(int row, int col) {
        System.out.println("[Backtracking] Cellule actuelle: [" + row + ", " + col + "]");

        if (row == view.getGridSize()) { // Si toutes les lignes sont parcourues
            return true;
        }

        int nextRow = (col == view.getGridSize() - 1) ? row + 1 : row;
        int nextCol = (col == view.getGridSize() - 1) ? 0 : col + 1;

        if (view.getCell(row, col).getNumber() != 0) {
            System.out.println("[Backtracking] Cellule déjà remplie.");
            return solveWithBacktracking(nextRow, nextCol);
        }

        for (int num = 1; num <= view.getGridSize(); num++) {
            System.out.println("[Backtracking] Essai de placement: " + num + " dans [" + row + ", " + col + "]");
            if (isValidPlacement(row, col, num)) {
                view.getCell(row, col).setNumber(num);
                if (solveWithBacktracking(nextRow, nextCol)) {
                    return true;
                }
                System.out.println("[Backtracking] Retour en arrière, effacement de la cellule [" + row + ", " + col + "]");
                view.getCell(row, col).setNumber(0); // Effacer et revenir en arrière
            }
        }

        System.out.println("[Backtracking] Aucun placement valide trouvé pour la cellule [" + row + ", " + col + "]");
        return false; // Aucun numéro valide trouvé pour cette cellule
    }

    private boolean isValidPlacement(int row, int col, int num) {
        System.out.println("[isValidPlacement] Vérification du placement de " + num + " en [" + row + ", " + col + "]");
    
        // Vérifier si le numéro est déjà dans la même ligne ou colonne
        for (int i = 0; i < view.getGridSize(); i++) {
            if (view.getCell(row, i).getNumber() == num || view.getCell(i, col).getNumber() == num) {
                System.out.println("[isValidPlacement] Numéro " + num + " déjà présent dans la ligne ou la colonne");
                return false;
            }
        }
    
        // Vérifier les contraintes des points noirs et blancs pour les cellules adjacentes
        for (int[] direction : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
            int adjRow = row + direction[0];
            int adjCol = col + direction[1];
    
            if (isValidCell(adjRow, adjCol)) {
                Cell adjacentCell = view.getCell(adjRow, adjCol);
                int adjacentNumber = adjacentCell.getNumber();
                System.out.println("[isValidPlacement] Cellule adjacente [" + adjRow + ", " + adjCol + "] = " + adjacentNumber);

                // Vérifier les points noirs
                if (gameBoardController.getModel().existsBlackEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    || gameBoardController.getModel().existsBlackEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    // Vérifie si la règle du point noir est respectée ou si la cellule adjacente est vide
                    if (adjacentNumber != 0 && !((num == 1 && adjacentNumber == 2) || (num == 2 && adjacentNumber == 1) || (num == 2 * adjacentNumber) || (adjacentNumber == 2 * num))) {
                        System.out.println("[isValidPlacement] Règle du point noir non respectée pour " + num + " en [" + row + ", " + col + "]");
                        return false;
                    }
                }

                // Vérifier les points blancs
                if (gameBoardController.getModel().existsWhiteEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    || gameBoardController.getModel().existsWhiteEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    // Vérifie si la règle du point blanc est respectée ou si la cellule adjacente est vide
                    if (adjacentNumber != 0 && Math.abs(num - adjacentNumber) != 1) {
                        System.out.println("[isValidPlacement] Règle du point blanc non respectée pour " + num + " en [" + row + ", " + col + "]");
                        return false;
                    }
                }

                // Vérifier l'absence de point
                if (!gameBoardController.getModel().existsBlackEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1) && !gameBoardController.getModel().existsWhiteEdgePoint(row + 1, col + 1, adjRow + 1, adjCol + 1)
                    && !gameBoardController.getModel().existsBlackEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1) && !gameBoardController.getModel().existsWhiteEdgePoint(adjRow + 1, adjCol + 1, row + 1, col + 1)) {
                    if (adjacentNumber != 0 && (Math.abs(num - adjacentNumber) == 1 || num == 2 * adjacentNumber || adjacentNumber == 2 * num)) {
                        System.out.println("[isValidPlacement] Règle de l'absence de point non respectée pour " + num + " en [" + row + ", " + col + "]");
                        return false;
                    }
                }

            }
        }
    
        System.out.println("[isValidPlacement] Placement valide pour " + num + " en [" + row + ", " + col + "]");
        return true; // Si toutes les vérifications sont passées, le placement est valide
    }
    
    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < view.getGridSize() && col >= 0 && col < view.getGridSize();
    }

    private void showVictoryMessage(long startTime, long endTime) {
        long time = endTime - startTime; // Temps d'exécution de l'algo en ms

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle("Victoire !");
        alert.setHeaderText(null);
        alert.setContentText("Félicitations ! Le bot a résolu le puzzle en " + time + "ms.");
        alert.showAndWait();
    }
    
}
