package com.kropkigame.main;

import com.kropkigame.model.*;
import com.kropkigame.utils.FileData;
import com.kropkigame.view.GameBoardPanel;

import java.io.IOException;

import com.kropkigame.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kropki Game");
        
        Puzzle model = new Puzzle();
        GameBoardPanel view = new GameBoardPanel();
        GameController gameController = new GameController(model, view);

        // Initialise le jeu
        gameController.startGame();

        Scene scene = new Scene(view, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        System.out.println((FileData.parseKropkiGrid(KropkiConstants.FILE_PATH)));
        launch(args);
    }
}
