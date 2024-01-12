package com.kropkigame.utils;

import java.util.List;

import com.kropkigame.model.KropkiConstants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class UiUtils {

    public static Button createImageButton(String imageType, double width, double height,
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

    public static HBox createIconHBox(Button... buttons) {
        HBox hbox = new HBox(500, buttons);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.setPadding(new Insets(0, 0, 20, 0));
        HBox.setHgrow(hbox, Priority.ALWAYS);
        return hbox;
    }

    public static VBox createCenterVBox(Label label, Button button) {
        VBox vbox = new VBox(100, label, button);
        vbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vbox, Priority.ALWAYS);
        return vbox;
    }

    public static VBox createCenterVBoxList(Label label, List<Button> buttons) {
        VBox vbox = new VBox();
        vbox.getChildren().add(label);
        vbox.getChildren().addAll(buttons);
        vbox.setSpacing(10); 
        vbox.setAlignment(Pos.CENTER);
        VBox.setVgrow(vbox, Priority.ALWAYS);

        return vbox;
    }

    public static Label createTitleLabel(String text) {
        Label titleLabel = new Label(text);
        titleLabel.getStyleClass().add("label-title");
        return titleLabel;
    }

    /**
     * Crée la barre de sélection des nombres.
     * @param gridSize la taille de la grille.
     * @return la barre de sélection des nombres.
     */
    public static HBox createNumberBar(int gridSize) {
        HBox numberBar = new HBox(10);

        // Crée un bouton pour chaque nombre
        for (int j = 1; j <= gridSize; j++) {
            final int number = j;
            Button numberButton = new Button(String.valueOf(number));
            numberButton.setId("numberButton" + number);
            numberButton.setStyle(KropkiConstants.NUMBER_BUTTON_STYLE);
            numberButton.setMaxWidth(Double.MAX_VALUE);

            // Ajoute le bouton à la barre de sélection des nombres
            numberBar.getChildren().add(numberButton);

            HBox.setHgrow(numberButton, Priority.ALWAYS);           
        }

        // Définit l'espacement entre les boutons
        numberBar.setSpacing(10);

        // Définit une bordure autour de la barre de sélection des nombres 
        numberBar.setStyle(KropkiConstants.NUMBER_BAR_STYLE);

        return numberBar;
    }

    /**
     * Crée le chronomètre.
     */
    public static Label createTimer() {
        Label timerLabel = new Label("00:00:00");
        timerLabel.setStyle(KropkiConstants.TIMER_LABEL_STYLE);
        return timerLabel;
    }

    

}
