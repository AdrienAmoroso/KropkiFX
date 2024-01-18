package com.kropkigame.main;

import com.kropkigame.controller.GameController;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.DifficultySelectionMenu;
import com.kropkigame.view.FirstMenu;
import com.kropkigame.view.GameBoardPanel;
import com.kropkigame.view.LevelSelectionMenu;
import com.kropkigame.view.SceneSwitcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * La classe principale de l'application Kropki Game.
 * Cette classe étend la classe Application et est responsable du démarrage du jeu.
 */
public class Main extends Application implements SceneSwitcher {

    private Stage primaryStage;

    @Override
    /**
     * Cette méthode est appelée au démarrage de l'application.
     * 
     * @param primaryStage le stage principal de l'application.
     */
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kropki Game");

        Font.loadFont(getClass().getResourceAsStream(KropkiConstants.TIMER_LABEL_FONT_PATH), 12);

        FirstMenu firstMenu = new FirstMenu(this);

        Scene scene = new Scene(firstMenu, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/com/kropkigame/main/style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    /**
     * Change la scène pour afficher le jeu avec la difficulté et le numéro de niveau spécifiés.
     * 
     * @param difficulty La difficulté du jeu.
     * @param levelNumber Le numéro du niveau.
     */
    public void switchToGame(String difficulty, int levelNumber) {
        // Obtenir le chemin du fichier en fonction du numéro de niveau
        String filepath = KropkiConstants.getFilePathForLevel(difficulty, levelNumber);
        
        Puzzle model = FileData.parseKropkiGrid(filepath);
        int gridSize = model.getGridSize();
        GameBoardPanel view = new GameBoardPanel(gridSize, this);
        GameController gameController = new GameController(model, view);

        gameController.startGame();
        gameController.getGameBoardController().drawEdgePoints(model.getEdgePoints());

        Scene gameScene = new Scene(view, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        gameScene.getStylesheets().add(getClass().getResource("/com/kropkigame/main/style.css").toExternalForm());
        gameController.getGameBoardController().getView().setStyle(KropkiConstants.GAMEBOARD_STYLE);
        gameController.getGameBoardController().addResizeListener(primaryStage);
        primaryStage.setScene(gameScene);
        primaryStage.show();
    }

    @Override
    /**
     * Change la scène pour afficher la sélection de la difficulté.
     */
    public void switchToDifficultySelection() {
        DifficultySelectionMenu difficultySelectionMenu = new DifficultySelectionMenu(this);

        Scene difficultySelectionScene = new Scene(difficultySelectionMenu, KropkiConstants.SCENE_WIDTH,
                KropkiConstants.SCENE_HEIGHT);
        difficultySelectionScene.getStylesheets()
                .add(getClass().getResource("/com/kropkigame/main/style.css").toExternalForm());
        primaryStage.setScene(difficultySelectionScene);
    }

    @Override
    /**
     * Change la scène pour afficher le menu d'accueil.
     */
    public void switchToFirstMenu() {
        FirstMenu firstMenu = new FirstMenu(this);

        Scene firstMenuScene = new Scene(firstMenu, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        firstMenuScene.getStylesheets().add(getClass().getResource("/com/kropkigame/main/style.css").toExternalForm());
        primaryStage.setScene(firstMenuScene);
    }

    @Override
    /**
     * Change la scène pour afficher la sélection du niveau avec la difficulté spécifiée.
     * 
     * @param difficulty La difficulté du jeu.
     */
    public void showLevelSelection(String difficulty) {
        LevelSelectionMenu levelSelectionMenu = new LevelSelectionMenu(this, difficulty);
        Scene levelSelectionScene = new Scene(levelSelectionMenu, KropkiConstants.SCENE_WIDTH,
                KropkiConstants.SCENE_HEIGHT);
        levelSelectionScene.getStylesheets()
                .add(getClass().getResource("/com/kropkigame/main/style.css").toExternalForm());
        primaryStage.setScene(levelSelectionScene);
    }

    /**
     * Le point d'entrée principal de l'application.
     * 
     * @param args les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }
}