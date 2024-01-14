package com.kropkigame.view;

import java.util.Arrays;
import java.util.List;

import com.kropkigame.utils.UiUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * La classe DifficultySelectionMenu représente le menu de sélection de la difficulté du jeu.
 * Elle hérite de la classe Parent de JavaFX, ce qui signifie qu'elle peut être utilisée comme un nœud dans la scène de l'interface utilisateur.
 */
public class DifficultySelectionMenu extends Parent {

    

    /**
     * Constructeur de la classe DifficultySelectionMenu.
     * @param sceneSwitcher Le commutateur de scène utilisé pour changer de scène dans l'interface utilisateur.
     */
    public DifficultySelectionMenu(SceneSwitcher sceneSwitcher) {
        

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label difficultyLabel = UiUtils.createTitleLabel("DIFFICULTY");

        // Création des boutons pour chaque niveau de difficulté
        Button fourButton = UiUtils.createImageButton(
                "Difficulty\\4x4",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("4x4"));
        Button fiveButton = UiUtils.createImageButton(
                "Difficulty\\5x5",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("5x5"));
        Button sixButton = UiUtils.createImageButton(
                "Difficulty\\6x6",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("6x6"));
        Button sevenButton = UiUtils.createImageButton(
                "Difficulty\\7x7",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("7x7"));
        Button eightButton = UiUtils.createImageButton(
                "Difficulty\\8x8",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("8x8"));

        // Création du bouton pour revenir à l'accueil
        Button homeButton = UiUtils.createImageButton(
                "Home",
                120,
                70,
                e -> sceneSwitcher.switchToFirstMenu());
        HBox iconsHBox = new HBox(homeButton);
        iconsHBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox.setHgrow(iconsHBox, Priority.ALWAYS);
        iconsHBox.setPadding(new Insets(0, 0, 20, 0));

        // Création d'une liste de boutons pour la VBox
        List<Button> buttons = Arrays.asList(
                fourButton,
                fiveButton,
                sixButton,
                sevenButton,
                eightButton);

        // Utilisation de la méthode modifiée createCenterVBox qui accepte une liste de boutons
        VBox difficultyVBox = UiUtils.createCenterVBoxList(difficultyLabel, buttons);

        // Configuration de la VBox au centre du layout principal
        mainLayout.setCenter(difficultyVBox);
        mainLayout.setBottom(iconsHBox);

        // Liaison des propriétés de hauteur et de largeur du layout principal à celles de la scène
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
