package com.kropkigame.main;

import com.kropkigame.controller.GameController;
import com.kropkigame.model.KropkiConstants;
import com.kropkigame.model.Puzzle;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.FirstMenu;
import com.kropkigame.view.GameBoardPanel;
import com.kropkigame.view.SceneSwitcher;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the Kropki Game application.
 * This class extends the Application class and is responsible for starting the
 * game.
 */


public class Main extends Application implements SceneSwitcher {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Kropki Game");

        FirstMenu firstMenu = new FirstMenu(this);

        Scene scene = new Scene(firstMenu, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void switchToGame() {
        // Logic to switch to the game scene
        // Similar to your existing game initialization code
        Puzzle model = FileData.parseKropkiGrid(KropkiConstants.FILE_PATH_4x4);
        int gridSize = model.getGridSize();

        GameBoardPanel view = new GameBoardPanel(gridSize);
        GameController gameController = new GameController(model, view);

        gameController.startGame();
        gameController.getGameBoardController().drawEdgePoints(model.getEdgePoints());

        Scene gameScene = new Scene(view, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        gameController.getGameBoardController().addResizeListener(primaryStage);
        primaryStage.setScene(gameScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
