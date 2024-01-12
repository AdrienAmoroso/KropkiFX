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

public class DifficultySelectionMenu extends Parent {

    

    public DifficultySelectionMenu(SceneSwitcher sceneSwitcher) {
        

        BorderPane mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-layout");

        Label difficultyLabel = UiUtils.createTitleLabel("DIFFICULTY");

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

        Button homeButton = UiUtils.createImageButton(
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
        VBox difficultyVBox = UiUtils.createCenterVBoxList(difficultyLabel, buttons);

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



}
