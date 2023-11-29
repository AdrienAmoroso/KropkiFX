package com.kropkigame.main;

import com.kropkigame.model.*;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.GameBoardPanel;

import java.io.IOException;

import com.kropkigame.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The main class of the Kropki Game application.
 * This class extends the Application class and is responsible for starting the game.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kropki Game");
        
        Puzzle model = FileData.parseKropkiGrid(KropkiConstants.FILE_PATH_6x6);
        int gridSize = model.getGridSize();

        GameBoardPanel view = new GameBoardPanel(gridSize);
        GameController gameController = new GameController(model, view);

        // Initialize the game
        gameController.startGame();
        gameController.getGameBoardController().drawEdgePoints(model.getEdgePoints());

        System.out.println((model));

        Scene scene = new Scene(view, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);

        primaryStage.setScene(scene);

        gameController.getGameBoardController().addResizeListener(primaryStage);

        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
