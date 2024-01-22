// package com.kropkigame.bot;

// import static org.awaitility.Awaitility.await;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import java.util.concurrent.TimeUnit;

// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;

// import com.kropkigame.controller.CellController;
// import com.kropkigame.controller.GameBoardController;
// import com.kropkigame.model.Puzzle;
// import com.kropkigame.utils.FileData;
// import com.kropkigame.view.GameBoardPanel;

// import javafx.application.Application;
// import javafx.stage.Stage;

// /**
//  * Classe de test pour le bot résolveur.
//  * Cette classe teste si le bot est capable de résoudre des puzzles de différentes tailles.
//  */
// @TestMethodOrder(MethodOrderer.MethodName.class)
// public class BotSolverTest {

//     private GameBoardController gameBoardController;

//     @BeforeAll
//     /**
//      * Méthode d'initialisation pour la classe de test.
//      * Cette méthode est exécutée une fois avant tous les tests.
//      * Elle initialise le toolkit JavaFX.
//      */
//     public static void setUpClass() throws InterruptedException {
//         // Initialise le toolkit JavaFX
//         Thread t = new Thread("JavaFX Init Thread") {
//             public void run() {
//                 Application.launch(AsNonApp.class, new String[0]);
//             }
//         };
//         t.setDaemon(true);
//         t.start();
//         Thread.sleep(500);
//     }

//     /**
//      * Classe d'application non réelle pour initialiser le toolkit JavaFX.
//      */
//     public static class AsNonApp extends Application {
//         @Override
//         public void start(Stage primaryStage) throws Exception {
//             // Pas besoin de faire quoi que ce soit ici
//         }
//     }

//     /**
//      * Initialise le contrôleur du plateau de jeu avec le puzzle spécifié.
//      * @param puzzleFilePath Le chemin d'accès au fichier du puzzle.
//      */
//     private void initializeGameBoardController(String puzzleFilePath) {
//         Puzzle model = FileData.parseKropkiGrid(puzzleFilePath);
//         int gridSize = model.getGridSize();
//         GameBoardPanel view = new GameBoardPanel(gridSize, null);
//         gameBoardController = new GameBoardController(model, view, new CellController(model, view));
//         gameBoardController.initializeGameBoard();
//     }

//     /**
//      * Teste le solveur de bot pour le puzzle spécifié.
//      * @param puzzleFilePath Le chemin d'accès au fichier du puzzle.
//      */
//     private void runBotSolverTest() throws InterruptedException {
//         BotSolverImpl botSolver = new BotSolverImpl(gameBoardController);
//         botSolver.startBot();
//         await().atMost(3, TimeUnit.SECONDS).until(() -> gameBoardController.checkGameStatus());
//         assertTrue(gameBoardController.checkGameStatus(), "Le bot doit résoudre le puzzle");
//     }

//     @Test
//     /**
//      * Teste si le bot peut résoudre un puzzle 4x4.
//      */
//     public void testBotSolverSolves4x4Puzzle() throws InterruptedException {
//         initializeGameBoardController("data\\4x4\\kropki_4x4_level1.txt");
//         runBotSolverTest();
//     }

//     @Test
//     /**
//      * Teste si le bot peut résoudre un puzzle 5x5.
//      */
//     public void testBotSolverSolves5x5Puzzle() throws InterruptedException {
//         initializeGameBoardController("data\\5x5\\kropki_5x5_level1.txt");
//         runBotSolverTest();
//     }

//     @Test
//     /**
//      * Teste si le bot peut résoudre un puzzle 6x6.
//      */
//     public void testBotSolverSolves6x6Puzzle() throws InterruptedException {
//         initializeGameBoardController("data\\6x6\\kropki_6x6_level1.txt");
//         runBotSolverTest();
//     }

//     @Test
//     /**
//      * Teste si le bot peut résoudre un puzzle 7x7.
//      */
//     public void testBotSolverSolves7x7Puzzle() throws InterruptedException {
//         initializeGameBoardController("data\\7x7\\kropki_7x7_level1.txt");
//         runBotSolverTest();
//     }

//     @Test
//     /**
//      * Teste si le bot peut résoudre un puzzle 8x8.
//      */
//     public void testBotSolverSolves8x8Puzzle() throws InterruptedException {
//         initializeGameBoardController("data\\8x8\\kropki_8x8_level1.txt");
//         runBotSolverTest();
//     }

//     @AfterEach
//     /**
//      * Méthode de nettoyage après chaque test.
//      * Cette méthode est exécutée après chaque test.
//      * Elle réinitialise le contrôleur du plateau de jeu.
//      */
//     public void tearDown() {
//         gameBoardController = null;
//     }
// }