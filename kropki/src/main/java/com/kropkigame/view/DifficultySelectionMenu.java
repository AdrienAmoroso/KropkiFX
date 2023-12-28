package com.kropkigame.view;

import java.util.Arrays;
import java.util.List;

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

public class DifficultySelectionMenu extends Parent {

    private SceneSwitcher sceneSwitcher;

    public DifficultySelectionMenu(SceneSwitcher sceneSwitcher) {
        this.sceneSwitcher = sceneSwitcher;

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label difficultyLabel = createTitleLabel("DIFFICULTY");

        Button fourButton = createImageButton(
                "Difficulty\\4x4",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("4x4"));
        Button fiveButton = createImageButton(
                "Difficulty\\5x5",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("5x5"));
        Button sixButton = createImageButton(
                "Difficulty\\6x6",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("6x6"));
        Button sevenButton = createImageButton(
                "Difficulty\\7x7",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("7x7"));
        Button eightButton = createImageButton(
                "Difficulty\\8x8",
                150,
                100,
                e -> sceneSwitcher.showLevelSelection("8x8"));

        Button homeButton = createImageButton(
                "Home",
                120,
                70,
                e -> sceneSwitcher.switchToFirstMenu());
        HBox iconsHBox = new HBox(homeButton);
        iconsHBox.setAlignment(Pos.BOTTOM_LEFT);
        HBox.setHgrow(iconsHBox, Priority.ALWAYS);
        iconsHBox.setPadding(new Insets(0, 0, 20, 0));

        // Create a list of buttons for the VBox
        List<Button> buttons = Arrays.asList(
                fourButton,
                fiveButton,
                sixButton,
                sevenButton,
                eightButton);

        // Use the modified createCenterVBox method that accepts a list of buttons
        VBox difficultyVBox = createCenterVBox(difficultyLabel, buttons);

        // Set the VBox to the center of the main layout
        mainLayout.setCenter(difficultyVBox);
        mainLayout.setBottom(iconsHBox);

        this.sceneProperty()
                .addListener((obs, oldScene, newScene) -> {
                    if (newScene != null) {
                        mainLayout.prefHeightProperty().bind(newScene.heightProperty());
                        mainLayout.prefWidthProperty().bind(newScene.widthProperty());
                    }
                });

        // Assuming this class extends a JavaFX layout, set the main layout as the child
        getChildren().add(mainLayout);
    }

    private Button createImageButton(
            String imageType,
            double width,
            double height,
            javafx.event.EventHandler<javafx.event.ActionEvent> actionEvent) {
        Button button = new Button();
        button.setMinSize(width, height);
        button.setMaxSize(width, height);
        button.getStyleClass().add("button-transparent");

        Image image = new Image(
                "file:" +
                        KropkiConstants.ASSETS_PATH +
                        "\\png\\Buttons\\Square-Medium\\" +
                        imageType +
                        "\\Default.png");
        Image hoverImage = new Image(
                "file:" +
                        KropkiConstants.ASSETS_PATH +
                        "\\png\\Buttons\\Square-Medium\\" +
                        imageType +
                        "\\Hover.png");

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

    private VBox createCenterVBox(Label label, List<Button> buttons) {
        VBox vbox = new VBox();
        vbox.getChildren().add(label);
        vbox.getChildren().addAll(buttons);
        vbox.setSpacing(10); // Vous pouvez ajuster l'espacement ici
        vbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vbox, Priority.ALWAYS);

        return vbox;
    }

}
