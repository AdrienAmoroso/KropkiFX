package com.kropkigame.controller;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.kropkigame.model.Puzzle;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.GameBoardPanel;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe de test pour le contrôleur du plateau de jeu.
 * Cette classe teste si le contrôleur du plateau de jeu fonctionne correctement.
 */
public class GameBoardControllerTest {

    private GameBoardController gameBoardController;

    @BeforeAll
    /**
     * Méthode d'initialisation pour la classe de test.
     * Cette méthode est exécutée une fois avant tous les tests.
     * Elle initialise le toolkit JavaFX.
     */
    public static void setUpClass() throws InterruptedException {
        // Initialise le toolkit JavaFX
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
        Thread.sleep(500);
    }

    /**
     * Classe d'application non réelle pour initialiser le toolkit JavaFX.
     */
    public static class AsNonApp extends Application {
        @Override
        /**
         * Méthode de démarrage pour l'application non réelle.
         * Cette méthode ne fait rien.
         */
        public void start(Stage primaryStage) throws Exception {
            // Pas besoin de faire quoi que ce soit ici
        }
    }

    @BeforeEach
    /**
     * Méthode d'initialisation pour chaque test.
     * Cette méthode est exécutée avant chaque test.
     * Elle initialise le contrôleur du plateau de jeu.
     */
    public void setUp() throws Exception {
        Puzzle model = FileData.parseKropkiGrid("data\\4x4\\kropki_4x4_level1.txt");
        int gridSize = model.getGridSize();
        GameBoardPanel view = new GameBoardPanel(gridSize, null);

        gameBoardController = new GameBoardController(model, view, new CellController(model, view));
        gameBoardController.initializeGameBoard();
    } 
    
    @Test
    /**
     * Teste si le contrôleur du plateau de jeu détecte correctement un état gagnant.
     */
    public void testCheckGameStatusTrue() {
        // Arrange
        for (int i = 0; i < gameBoardController.getModel().getGridSize(); i++) {
            for (int j = 0; j < gameBoardController.getModel().getGridSize(); j++) {
                gameBoardController.getView().getCell(i, j).setNumber(gameBoardController.getModel().getNumber(i, j));
            }
        }

        // Act
        boolean status = gameBoardController.checkGameStatus(); // Le jeu est dans un état gagnant

        // Assert
        assertTrue(status, "Le jeu devrait être dans un état gagnant");
    }

    @Test
    /**
     * Teste si le contrôleur du plateau de jeu détecte correctement un état non gagnant.
     */
    public void testCheckGameStatusFalse() {
        // Arrange
        for (int i = 0; i < gameBoardController.getModel().getGridSize() -1; i++) {
            for (int j = 0; j < gameBoardController.getModel().getGridSize() -1; j++) {
                gameBoardController.getView().getCell(i, j).setNumber(gameBoardController.getModel().getNumber(i, j));
            }
        }

        // Act
        boolean status = gameBoardController.checkGameStatus(); // Le jeu est dans un état gagnant

        // Assert
        assertFalse(status, "Le jeu devrait être dans un état non gagnant");
    }

    @AfterEach
    /**
     * Méthode de nettoyage pour chaque test.
     * Cette méthode est exécutée après chaque test.
     * Elle nettoie le contrôleur du plateau de jeu.
     */
    public void tearDown() throws Exception {
        gameBoardController = null;
    }
}
