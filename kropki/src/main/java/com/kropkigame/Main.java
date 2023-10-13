package com.kropkigame;

import com.kropkigame.model.Puzzle;
import com.kropkigame.view.GameBoardPanel;
import com.kropkigame.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Puzzle model = new Puzzle();
        GameBoardPanel view = new GameBoardPanel();
        GameController controller = new GameController(model, view);
        view.setController(controller);

        Scene scene = new Scene(view, 200, 200); // Adjust size as needed.

        primaryStage.setTitle("Kropki Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
