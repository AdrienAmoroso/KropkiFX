package com.kropkigame.view;

import com.kropkigame.model.KropkiConstants;
import com.kropkigame.utils.UiUtils;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private BotSwitch botSwitch;
    private Button resetButton;
    private Button backButton;
    private Label timerLabel;
    

    /**
     * Construit la fenêtre de jeu avec la taille de grille spécifiée.
     * @param gridSize la taille de la grille.
     */
    public GameBoardPanel(int gridSize, SceneSwitcher sceneSwitcher) {
        this.gridSize = gridSize;
        this.cells = new Cell[gridSize][gridSize];
        this.gridPane = createGridPane(gridSize);
        this.numberBar = UiUtils.createNumberBar(gridSize);
        this.helpSwitch = new HelpSwitch();

        this.botSwitch = new BotSwitch();
        
        this.resetButton = UiUtils.createImageButton("repeat", 40, 40, e -> {});
        this.backButton = UiUtils.createImageButton("Back", 40, 40, e -> {});
        this.timerLabel = UiUtils.createTimer();

        HBox contentHbox = new HBox(50); // Boutons utilitaires
        VBox contentVBox = new VBox(10); // Contenu principal (grille + Hbox)
        HBox botSwitchHBox = new HBox(10); // Contient le bouton du bot

        contentHbox.setAlignment(Pos.CENTER);
        contentVBox.setAlignment(Pos.CENTER);
        botSwitchHBox.setAlignment(Pos.CENTER);

        Button homeButton = UiUtils.createImageButton("Home", 120, 70, e -> sceneSwitcher.switchToDifficultySelection());
        contentHbox.getChildren().addAll(resetButton, helpSwitch, backButton);
        botSwitchHBox.getChildren().add(botSwitch);
        contentVBox.getChildren().addAll(timerLabel, contentHbox, gridPane, botSwitchHBox);

        this.setTop(homeButton);

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
        GridPane gridPane = new GridPane();
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
     * Désactive les interactions utilisateurs avec la grille et la barre de sélection des nombres.
     */
    public void disableUserInteraction() {
        resetButton.setDisable(true);
        backButton.setDisable(true);
        helpSwitch.setDisable(true);

        // Désactiver les interactions avec numberBar et les cellules
        for (javafx.scene.Node node : numberBar.getChildren()) {
            node.setDisable(true);
        }
    }

    /**
     * Active les interactions utilisateurs avec la grille et la barre de sélection des nombres.
     */
    public void enableUserInteraction() {
        resetButton.setDisable(false);
        backButton.setDisable(false);
        helpSwitch.setDisable(false);

        // Activer les interactions avec numberBar et les cellules
        for (javafx.scene.Node node : numberBar.getChildren()) {
            node.setDisable(false);
        }
    }

    /**
     * Désactive les interactions utilisateurs avec le bouton du bot.
     */
    public void disableBotInteraction() {
        botSwitch.setDisable(true);
    }

    /**
     * Active les interactions utilisateurs avec le bouton du bot.
     */
    public void enableBotInteraction() {
        botSwitch.setDisable(false);
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
     * Renvoie le bouton du bot.
     * @return le bouton du bot.
     */
    public BotSwitch getBotSwitch() {
        return this.botSwitch;
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