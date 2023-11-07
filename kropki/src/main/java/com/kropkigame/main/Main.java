package com.kropkigame.main;

import com.kropkigame.model.*;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.GameBoardPanel;

import java.io.IOException;

import com.kropkigame.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kropki Game");
        
        Puzzle model = new Puzzle();
        GameController gameController = new GameController(model, new GameBoardPanel());

        // Initialise le jeu
        gameController.startGame();

        BorderPane root = new BorderPane();
        root.setCenter(gameController.getView());

        Scene scene = new Scene(root, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        System.out.println((FileData.parseKropkiGrid(KropkiConstants.FILE_PATH)));
        launch(args);
    }
}
