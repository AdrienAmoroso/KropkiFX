package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {

    /*** Tailles des composants de la vue ***/
    int CELL_SIZE = 80; // Taille d'une cellule
    int SCENE_WIDTH = 600; // Largeur de la scène
    int SCENE_HEIGHT = 800; // Hauteur de la scène

    /*** Styles des composants de la vue ***/
    String NUMBER_BUTTON_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;"; // Style des boutons de sélection des nombres
    String CELL_TEXT_STYLE = "-fx-fill: white; -fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;"; // Style de texte pour les cellules
    String CELL_BORDER_STYLE = "-fx-border-color: white; -fx-border-width: 1px;"; // Style de bordure pour les cellules
    String GAMEBOARD_STYLE = "-fx-background-color: black;" // Couleur de fond du plateau de jeu
            + "-fx-border-color: white;" // Couleur de bordure du plateau de jeu
            + "-fx-border-width: 2px;"; // Largeur de bordure du plateau de jeu
    String NUMBER_BAR_STYLE = "-fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10px;"; // Style de la numberBar (sélection des nombress)
    String HELP_TEXT_STYLE = "-fx-font-family: Helvetica; -fx-font-weight: bold;"; // Style du texte d'aide (switch)
    String CELL_ERROR_STYLE = "-fx-border-color: red; -fx-border-width: 3px; -fx-border-style: solid;"; // Style de la cellule lorsqu'elle est incorrecte
    String CELL_SELECTED_STYLE = "-fx-border-color: white; -fx-border-width: 1px; -fx-background-color: blue;"; // Style de la cellule lorsqu'elle est sélectionnée
    String CELL_ERROR_SELECTED_STYLE = "-fx-border-color: red; -fx-border-width: 3px; -fx-border-style: solid; -fx-background-color: blue;"; // Style de la cellule lorsqu'elle est incorrecte et sélectionnée

    /*** Chemins vers les fichiers contenant les grilles de jeu ***/
    String FILE_PATH_4x4 = "kropki" + File.separator + "data" + File.separator + "kropki_4x4.txt";
    String FILE_PATH_5x5 = "kropki" + File.separator + "data" + File.separator + "kropki_5x5.txt";
    String FILE_PATH_6x6 = "kropki" + File.separator + "data" + File.separator + "kropki_6x6.txt";
    String FILE_PATH_7x7 = "kropki" + File.separator + "data" + File.separator + "kropki_7x7.txt";
    String FILE_PATH_8x8 = "kropki" + File.separator + "data" + File.separator + "kropki_8x8.txt";
}