package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {

        /*** Tailles des composants de la vue ***/
        int CELL_SIZE = 80; // Taille d'une cellule
        int SCENE_WIDTH = 800; // Largeur de la scène
        int SCENE_HEIGHT = 800; // Hauteur de la scène

        /*** Styles des composants de la vue ***/
        String GAMEBOARD_STYLE = "-fx-background-color: black;" // Couleur de fond du plateau de jeu
                + "-fx-border-color: white;" // Couleur de bordure du plateau de jeu
                + "-fx-border-width: 2px;"; // Largeur de bordure du plateau de jeu
        String NUMBER_BAR_STYLE = "-fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10px;"; // Style de la numberBar (sélection des nombres)
        String NUMBER_BUTTON_STYLE = "-fx-background-color: #000000, linear-gradient(#7ebcea, #2f4b8f), linear-gradient(#426ab7, #263e75), "
                + "linear-gradient(#395cab, #223768); -fx-background-insets: 0,1,2,3; -fx-background-radius: 3,2,2,2;"
                + "-fx-padding: 12 30 12 30; -fx-text-fill: white; -fx-font-family: Helvetica; -fx-font-size: 20pt;"
                + "-fx-pref-width: 50px; -fx-pref-height: 50px; -fx-font-weight: bold; -fx-border-color: white; -fx-border-radius: 3"; // Style des boutons de sélection des nombres
        String NUMBER_BUTTON_HOVER_STYLE = "-fx-background-color: #000000, linear-gradient(#7ebcea, #2f4b8f), linear-gradient(#426ab7, #263e75), "
                + "linear-gradient(#395cab, #223768); -fx-background-insets: 0,1,2,3; -fx-background-radius: 3,2,2,2; "
                + "-fx-padding: 12 30 12 30; -fx-text-fill: white; -fx-font-family: Helvetica; -fx-font-size: 20pt; "
                + "-fx-pref-width: 50px; -fx-pref-height: 50px; -fx-font-weight: bold; -fx-border-color: white; -fx-border-radius: 3;"
                + "-fx-effect: dropshadow(gaussian, white, 5, 0.3, 0, 0);"; // Style de survol des boutons de sélection des nombres
        String HELP_TEXT_STYLE = "-fx-font-family: Helvetica; -fx-font-weight: bold;"; // Style du texte d'aide (switch)

        String CELL_TEXT_STYLE = "-fx-fill: white; -fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;"; // Style de texte pour les cellules
        String CELL_BORDER_STYLE = "-fx-border-color: white; -fx-border-width: 1px;"; // Style de bordure pour les cellules
        String CELL_ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 3px; -fx-border-style: solid;"; // Style de la cellule lorsqu'elle est incorrecte
        String CELL_SELECTED_STYLE = "-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: blue;"; // Style de la cellule lorsqu'elle est sélectionnée
        String CELL_ERROR_SELECTED_STYLE = "-fx-border-color: red; -fx-border-width: 3px; -fx-border-style: solid; -fx-background-color: blue;"; // Style de la cellule lorsqu'elle est incorrecte et sélectionnée

        String RESET_BUTTON_STYLE = "-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 10;"; // Style du bouton de réinitialisation
        String RESET_BUTTON_HOVER_STYLE = "-fx-effect: dropshadow(gaussian, white, 3, 0.3, 0, 0);" // Style de survol du bouton de réinitialisation
        + "-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 10;"; 

        String BACK_BUTTON_STYLE = "-fx-background-color: transparent; -fx-border-color: white; -fx-border-radius: 10;"; // Style du bouton de réinitialisation
        String BACK_BUTTON_HOVER_STYLE = "-fx-effect: dropshadow(gaussian, white, 3, 0.3, 0, 0);" // Style de survol du bouton de réinitialisation
        + "-fx-background-color: transparent; -fx-border-color: white; -fx-border-width: 1; -fx-border-style: solid; -fx-border-radius: 10;"; 

        String TIMER_LABEL_STYLE = "-fx-font-family: 'Digital-7'; -fx-font-size: 40pt; -fx-text-fill: white;"; // Style du chronomètre

        /*** Chemins vers les fichiers contenant les grilles de jeu ***/
        String FILE_PATH_4x4 = "kropki" + File.separator + "data" + File.separator + "4x4" + File.separator + "kropki_4x4.txt";
        String FILE_PATH_5x5 = "kropki" + File.separator + "data" + File.separator + "5x5" + File.separator + "kropki_5x5.txt";
        String FILE_PATH_6x6 = "kropki" + File.separator + "data" + File.separator + "6x6" + File.separator + "kropki_6x6.txt";
        String FILE_PATH_7x7 = "kropki" + File.separator + "data" + File.separator + "7x7" + File.separator + "kropki_7x7.txt";
        String FILE_PATH_8x8 = "kropki" + File.separator + "data" + File.separator + "8x8" + File.separator + "kropki_8x8.txt";

        String ASSETS_PATH = "kropki\\assets\\Prinbles_GUI_Asset_Solid (1.0.0)\\asset";

        /*** Chemins vers les ressources supplémentaires (images, polices, ...) ***/
        String RESET_ICON_PATH = "/icons/reset_icon.png"; // Chemin vers l'icône du bouton Reset
        String BACK_ICON_PATH = "/icons/back_icon.png"; // Chemin vers l'icône du bouton Back
        String LABEL_TIMER_FONT_PATH = "/fonts/digital-7.ttf"; // Chemin vers la police du chronomètre
}