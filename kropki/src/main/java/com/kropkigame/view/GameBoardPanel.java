package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * Représente le panneau du plateau de jeu, qui contient la grille de cellules et la barre de numéros.
 */
public class GameBoardPanel extends BorderPane {
    private Cell[][] cells;
    private int gridSize;
    private HBox numberBar;
    private GridPane gridPane;
    private HelpSwitch helpSwitch;
    private Button resetButton;
    private Button backButton;
    private Label timerLabel;

    /**
     * Construit la fenêtre de jeu avec la taille de grille spécifiée.
     * @param gridSize la taille de la grille.
     */
    public GameBoardPanel(int gridSize) {
        this.gridSize = gridSize;
        this.cells = new Cell[gridSize][gridSize];
        this.gridPane = createGridPane(gridSize);
        this.numberBar = createNumberBar(gridSize);
        this.helpSwitch = new HelpSwitch();
        this.resetButton = createResetButton();
        this.backButton = createBackButton();
        this.timerLabel = createTimer();

        HBox contentHbox = new HBox(50); // Boutons utilitaires
        VBox contentVBox = new VBox(10); // Contenu principal (grille + Hbox)
        contentHbox.setAlignment(Pos.CENTER);
        contentVBox.setAlignment(Pos.CENTER);

        contentHbox.getChildren().addAll(resetButton, helpSwitch, backButton);
        contentVBox.getChildren().addAll(timerLabel, contentHbox, gridPane);

        this.setStyle(KropkiConstants.GAMEBOARD_STYLE);
        this.setCenter(contentVBox);
        this.setBottom(numberBar);
    }

    /**
     * Crée la grille de jeu (sans les points).
     * @param gridSize la taille de la grille.
     * @return la grille de jeu (sans les points).
     */
    private GridPane createGridPane(int gridSize) {
        this.gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        for (int row = 0; row < gridSize; row++) {
            for (int col = 0; col < gridSize; col++) {
                cells[row][col] = new Cell(row, col);
                gridPane.add(cells[row][col], col, row);
            }
        }
        return gridPane;
    }

    /**
     * Crée la barre de sélection des nombres.
     * @param gridSize la taille de la grille.
     * @return la barre de sélection des nombres.
     */
    private HBox createNumberBar(int gridSize) {
        this.numberBar = new HBox(10);

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
     * Crée le bouton de réinitialisation.
     * @return le bouton de réinitialisation.
     */
    private Button createResetButton() {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream(KropkiConstants.RESET_ICON_PATH)));
        imageView.setFitWidth(30); 
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(false);
    
        Button resetBtn = new Button();
        resetBtn.setGraphic(imageView);
        resetBtn.setStyle(KropkiConstants.RESET_BUTTON_STYLE);
    
        return resetBtn;
    }
    
    /**
     * Crée le bouton de retour.
     * @return le bouton de retour.
     */
    private Button createBackButton() {
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream(KropkiConstants.BACK_ICON_PATH)));
        imageView.setFitWidth(30); 
        imageView.setFitHeight(30);
        imageView.setPreserveRatio(false);
    
        Button backBtn = new Button();
        backBtn.setGraphic(imageView);
        backBtn.setStyle(KropkiConstants.BACK_BUTTON_STYLE);
    
        return backBtn;
    }   

    /**
     * Crée le chronomètre.
     */
    private Label createTimer() {
        timerLabel = new Label("00:00:00");
        timerLabel.setStyle(KropkiConstants.TIMER_LABEL_STYLE);
        return timerLabel;
    }
    
    /**
     * Renvoie la cellule à la ligne et à la colonne spécifiées.
     * @param row la ligne de la cellule.
     * @param col la colonne de la cellule.
     * @return la cellule à la ligne et à la colonne spécifiées.
     */
    public Cell getCell(int row, int col) {
        if (row < 0 || row >= gridSize || col < 0 || col >= gridSize) {
            throw new IllegalArgumentException("Index de ligne ou de colonne invalide");
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (cells[i][j].getRow() == row && cells[i][j].getCol() == col) {
                    return cells[i][j];
                }
            }
        }
        
        return null;
    }

    /**
     * Renvoie la grille de jeu.
     * @return la grille de jeu.
     */
    public GridPane getGridPane() {
        return this.gridPane;
    }

    /**
     * Renvoie la barre de sélection des nombres.
     * @return la barre de sélection des nombres.
     */
    public HBox getNumberBar() {
        return this.numberBar;
    }

    /**
     * Renvoie la taille de la grille.
     * @return la taille de la grille.
     */
    public int getGridSize() {
        return this.gridSize;
    }

    /**
     * Renvoie le bouton d'aide.
     * @return le bouton d'aide.
     */
    public HelpSwitch getHelpSwitch() {
        return this.helpSwitch;
    }

    /**
     * Renvoie le bouton de réinitialisation.
     * @return le bouton de réinitialisation.
     */
    public Button getResetButton() {
        return this.resetButton;
    }

    /**
     * Définit le bouton de réinitialisation.
     * @param resetButton le bouton de réinitialisation.
     */
    public void setResetButton(Button resetButton) {
        this.resetButton = resetButton;
    }

    /**
     * Renvoie le bouton de retour.
     * @return le bouton de retour.
     */
    public Button getBackButton() {
        return this.backButton;
    }

    /**
     * Définit le bouton de retour.
     * @param backButton le bouton de retour.
     */
    public void setBackButton(Button backButton) {
        this.backButton = backButton;
    }

    /**
     * Renvoie le Label du chronomètre.
     * @return le Label du chronomètre.
     */
    public Label getTimerLabel() {
        return this.timerLabel;
    }

    /**
     * Définit le Label du chronomètre.
     * @param timerLabel le Label du chronomètre.
     */
    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }
}