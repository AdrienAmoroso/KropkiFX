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
 * La classe principale de l'application Kropki Game.
 * Cette classe étend la classe Application et est responsable du démarrage du jeu.
 */
public class Main extends Application {
    @Override
    /**
     * Cette méthode est appelée au démarrage de l'application.
     * @param primaryStage le stage principal de l'application.
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Kropki Game");
        
        // Parse le fichier de grille Kropki et crée un modèle de puzzle
        Puzzle model = FileData.parseKropkiGrid(KropkiConstants.FILE_PATH_4x4);
        int gridSize = model.getGridSize();

        GameBoardPanel view = new GameBoardPanel(gridSize);
        GameController gameController = new GameController(model, view);

        // Initialise le jeu
        gameController.startGame();
        gameController.getGameBoardController().drawEdgePoints(model.getEdgePoints());

        System.out.println((model));

        Scene scene = new Scene(view, KropkiConstants.SCENE_WIDTH, KropkiConstants.SCENE_HEIGHT);
        primaryStage.setScene(scene);

        // Ajoute un écouteur de redimensionnement au stage principal pour
        // redessiner les points en cas de redimensionnement de la fenêtre
        gameController.getGameBoardController().addResizeListener(primaryStage);

        primaryStage.show();
    }

    /**
     * La méthode principale de l'application.
     * @param args les arguments de la ligne de commande.
     * @throws IOException si une erreur d'entrée/sortie se produit.
     */
    public static void main(String[] args) throws IOException {
        launch(args);
    }
}