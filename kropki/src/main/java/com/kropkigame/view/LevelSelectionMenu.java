package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.utils.UiUtils;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * La classe LevelSelectionMenu représente le menu de sélection de niveau du
 * jeu.
 * Elle hérite de la classe Parent de JavaFX, ce qui signifie qu'elle peut être
 * utilisée comme un nœud dans la scène de l'interface utilisateur.
 */
public class LevelSelectionMenu extends Parent {

    /**
     * Constructeur de la classe LevelSelectionMenu.
     * 
     * @param sceneSwitcher Le commutateur de scène utilisé pour changer de scène
     *                      dans l'interface utilisateur.
     * @param difficulty    Le niveau de difficulté sélectionné.
     */
    public LevelSelectionMenu(SceneSwitcher sceneSwitcher, String difficulty) {
        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        // Chargement et configuration de l'image du titre
        Image titre = new Image("file:" + KropkiConstants.ASSETS_PATH + "\\png\\Titles\\SelectionDesNiveaux.png");
        ImageView titreview = new ImageView(titre);
        titreview.setFitWidth(700); // Largeur souhaitée
        titreview.setFitHeight(650); // Hauteur souhaitée
        titreview.setPreserveRatio(true);

        VBox titreVBoxBox = new VBox(titreview); // Boîte pour le titre
        titreVBoxBox.setAlignment(Pos.TOP_CENTER); // Alignement du titre
        titreVBoxBox.setPadding(new Insets(65, 0, 0, 0)); // Marge autour du titre

        GridPane levelGrid = new GridPane();
        levelGrid.setHgap(10); // Espacement horizontal
        levelGrid.setVgap(10); // Espacement vertical
        levelGrid.setAlignment(Pos.CENTER);

        VBox levelVBox = new VBox(levelGrid);
        levelVBox.setAlignment(Pos.CENTER);
        levelVBox.setPadding(new Insets(-100, 0, 0, 0));

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

        // VBox leveBox = UiUtils.createCenterVBoxGrid(titreview, levelGrid);

        mainLayout.setTop(titreVBoxBox); // Ajout du titre en haut
        mainLayout.setCenter(levelVBox); // Ajout de la grille de sélection des niveaux au centre
        mainLayout.setBottom(iconsHBox); // Ajout du bouton Home en bas

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
