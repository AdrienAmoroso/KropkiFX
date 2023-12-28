package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FirstMenu extends Parent {

    private SceneSwitcher sceneSwitcher;

    public FirstMenu(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label titleLabel = createTitleLabel("KROPKI");
        Button playButton = createImageButton("PlayText", 150, 100, e -> sceneSwitcher.switchToDifficultySelection());

        Button homeButton = createImageButton("Home", 120, 70, e -> System.exit(0));
        Button starButton = createImageButton("Star", 120, 70, e -> sceneSwitcher.switchToGame());

        HBox iconsHBox = createIconHBox(homeButton, starButton);
        VBox centerVBox = createCenterVBox(titleLabel, playButton);

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

    private Label createTitleLabel(String text) {
        Label titleLabel = new Label(text);
        titleLabel.getStyleClass().add("label-title");
        return titleLabel;
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

    private HBox createIconHBox(Button... buttons) {
        HBox hbox = new HBox(500, buttons);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.setPadding(new Insets(0, 0, 20, 0));
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    private VBox createCenterVBox(Label label, Button button) {
        VBox vbox = new VBox(100, label, button);
        vbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        return vbox;
    }
}
