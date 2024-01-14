package com.kropkigame.bot;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kropkigame.controller.CellController;
import com.kropkigame.controller.GameBoardController;
import com.kropkigame.model.Puzzle;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.GameBoardPanel;

import javafx.application.Application;
import javafx.stage.Stage;

public class BotSolverTest {

    private GameBoardController gameBoardController;

    @BeforeClass
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

    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // Pas besoin de faire quoi que ce soit ici
        }
    }

    @Before
    public void setUp() throws Exception {
        Puzzle model = FileData.parseKropkiGrid("data\\4x4\\kropki_4x4_level1.txt");
        int gridSize = model.getGridSize();
        GameBoardPanel view = new GameBoardPanel(gridSize, null);

        gameBoardController = new GameBoardController(model, view, new CellController(model, view));
        gameBoardController.initializeGameBoard();
    }

    @Test
    public void testBotSolverSolvesPuzzle() throws InterruptedException {
        // Arrange
        BotSolverImpl botSolver = new BotSolverImpl(gameBoardController);
        botSolver.startBot();

        // Attente pour permettre au bot de résoudre le puzzle
        Thread.sleep(5000); // Augmenter ce temps si nécessaire

        // Act & Assert
        assertTrue("Le bot doit résoudre le puzzle", gameBoardController.checkGameStatus());
    }
}
