package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LevelSelectionMenu extends Parent {

    private SceneSwitcher sceneSwitcher;
    private String difficulty;

    public LevelSelectionMenu(SceneSwitcher sceneSwitcher, String difficulty) {
        this.sceneSwitcher = sceneSwitcher;
        this.difficulty = difficulty;

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label difficultyLabel = createTitleLabel("DIFFICULTY");

        GridPane levelGrid = new GridPane();
        levelGrid.setHgap(10); // Espacement horizontal entre les boutons
        levelGrid.setVgap(10); // Espacement vertical entre les boutons
        levelGrid.setAlignment(Pos.CENTER);

        // Créer une grille de boutons 2x5
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                int level = ((i * 5) + j + 1) ; // Calcule le numéro du niveau
                Button levelButton = createImageButton("Level\\" + level, 150, 100, e -> sceneSwitcher.switchToGame(difficulty, level));
                levelGrid.add(levelButton, j, i); // Ajoute le bouton à la grille
            }
        }

        Button homeButton = createImageButton(
                "Home",
                120,
                70,
                e -> sceneSwitcher.switchToDifficultySelection());
        HBox iconsHBox = new HBox(homeButton);
        iconsHBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox.setHgrow(iconsHBox, Priority.ALWAYS);
        iconsHBox.setPadding(new Insets(0, 0, 20, 0));

        VBox leveBox = createCenterVBox(difficultyLabel, levelGrid);

        mainLayout.setCenter(leveBox);
        mainLayout.setBottom(iconsHBox);

        this.sceneProperty()
                .addListener((obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        mainLayout.prefHeightProperty().bind(newScene.heightProperty());
                        mainLayout.prefWidthProperty().bind(newScene.widthProperty());
                    }
                });

        getChildren().add(mainLayout);
    }

    private Button createImageButton(String imageType, double width, double height,
            javafx.event.EventHandler<javafx.event.ActionEvent> actionEvent) {
        Button button = new Button();
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.getStyleClass().add("button-transparent");

        Image image = new Image("file:" + KropkiConstants.ASSETS_PATH + "\\png\\Buttons\\Square-Medium\\" + imageType
                + "\\Default.png");
        Image hoverImage = new Image(
                "file:" + KropkiConstants.ASSETS_PATH + "\\png\\Buttons\\Square-Medium\\" + imageType + "\\Hover.png");

        ImageView imageView = new ImageView(image);
        ImageView hoverImageView = new ImageView(hoverImage);

        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        hoverImageView.setFitHeight(height);
        hoverImageView.setFitWidth(width);

        imageView.setPreserveRatio(true);
        hoverImageView.setPreserveRatio(true);

        button.setGraphic(imageView);
        button.setOnMouseEntered(e -> button.setGraphic(hoverImageView));
        button.setOnMouseExited(e -> button.setGraphic(imageView));
        button.setOnAction(actionEvent);

        return button;
    }

    private Label createTitleLabel(String text) {
        Label titleLabel = new Label(text);
        titleLabel.getStyleClass().add("label-title");
        return titleLabel;
    }

    private VBox createCenterVBox(Label label, Node gridOrComponent) {
        VBox vbox = new VBox(10); // Ajustez l'espacement selon vos besoins
        vbox.getChildren().add(label);
        vbox.getChildren().add(gridOrComponent);
        vbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(gridOrComponent, Priority.ALWAYS);

        return vbox;
    }

    private void playLevel(String level) {
        // TODO: Implémenter la logique pour démarrer le niveau sélectionné
    }
}
