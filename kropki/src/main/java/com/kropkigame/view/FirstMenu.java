package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.utils.UiUtils;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * La classe FirstMenu représente le premier menu du jeu.
 * Elle hérite de la classe Parent de JavaFX, ce qui signifie qu'elle peut être utilisée comme un nœud dans la scène de l'interface utilisateur.
 */
public class FirstMenu extends Parent {

    

    /**
     * Constructeur de la classe FirstMenu.
     * @param sceneSwitcher Le commutateur de scène utilisé pour changer de scène dans l'interface utilisateur.
     */
    public FirstMenu(SceneSwitcher sceneSwitcher) {
        

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Image titre = new Image("file:" + KropkiConstants.ASSETS_PATH + "\\png\\Titles\\Kropki.png");
        ImageView titreview = new ImageView(titre);
        titreview.setFitWidth(500); // Largeur souhaitée
        titreview.setFitHeight(450); // Hauteur souhaitée
        titreview.setPreserveRatio(true);

        Button playButton = UiUtils.createImageButton("PlayText", 150, 100, e -> sceneSwitcher.switchToDifficultySelection());

        Button homeButton = UiUtils.createImageButton("Home", 120, 70, e -> System.exit(0));
        Button starButton = UiUtils.createImageButton("Star", 120, 70, e -> sceneSwitcher.switchToDifficultySelection());

        HBox iconsHBox = UiUtils.createIconHBox(homeButton, starButton);
        VBox centerVBox = UiUtils.createCenterVBox(titreview, playButton);

        mainLayout.setBottom(iconsHBox);
        mainLayout.setCenter(centerVBox);

        this.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                mainLayout.prefHeightProperty().bind(newScene.heightProperty());
                mainLayout.prefWidthProperty().bind(newScene.widthProperty());
            }
        });

        getChildren().add(mainLayout);
    }    
}
