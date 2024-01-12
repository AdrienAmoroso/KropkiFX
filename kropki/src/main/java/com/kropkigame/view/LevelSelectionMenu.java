package com.kropkigame.view;

import com.kropkigame.utils.UiUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * La classe LevelSelectionMenu représente le menu de sélection de niveau du jeu.
 * Elle hérite de la classe Parent de JavaFX, ce qui signifie qu'elle peut être utilisée comme un nœud dans la scène de l'interface utilisateur.
 */
public class LevelSelectionMenu extends Parent {

    /**
     * Constructeur de la classe LevelSelectionMenu.
     * @param sceneSwitcher Le commutateur de scène utilisé pour changer de scène dans l'interface utilisateur.
     * @param difficulty Le niveau de difficulté sélectionné.
     */
    public LevelSelectionMenu(SceneSwitcher sceneSwitcher, String difficulty) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label difficultyLabel = UiUtils.createTitleLabel("LEVEL SELECTION");

        GridPane levelGrid = new GridPane();
        levelGrid.setHgap(10); // Espacement horizontal entre les boutons
        levelGrid.setVgap(10); // Espacement vertical entre les boutons
        levelGrid.setAlignment(Pos.CENTER);

        // Création d'une grille de boutons pour la sélection du niveau
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 5; j++) {
                int level = ((i * 5) + j + 1); // Calcule le numéro du niveau
                Button levelButton = UiUtils.createImageButton("Level\\" + level, 150, 100,
                        e -> sceneSwitcher.switchToGame(difficulty, level));
                levelGrid.add(levelButton, j, i); // Ajoute le bouton à la grille
            }
        }

        Button homeButton = UiUtils.createImageButton(
                "Home",
                120,
                70,
                e -> sceneSwitcher.switchToDifficultySelection());
        HBox iconsHBox = new HBox(homeButton);
        iconsHBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox.setHgrow(iconsHBox, Priority.ALWAYS);
        iconsHBox.setPadding(new Insets(0, 0, 20, 0));

        VBox leveBox = UiUtils.createCenterVBoxGrid(difficultyLabel, levelGrid);

        mainLayout.setCenter(leveBox);
        mainLayout.setBottom(iconsHBox);

        this.sceneProperty()
                .addListener((obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        mainLayout.prefHeightProperty().bind(newScene.heightProperty());
                        mainLayout.prefWidthProperty().bind(newScene.widthProperty());
                    }
                });

        // Ajout du layout principal comme enfant de ce nœud
        getChildren().add(mainLayout);
    }    
}
