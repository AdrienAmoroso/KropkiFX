package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {

    /*** Tailles des composants de la vue ***/
    int CELL_SIZE = 60; // Taille d'une cellule
    int SCENE_WIDTH = 500; // Largeur de la scène
    int SCENE_HEIGHT = 600; // Hauteur de la scène

    /*** Styles des composants de la vue ***/
    String NUMBER_BUTTON_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;"; // Style des boutons de sélection des nombres
    String CELL_TEXT_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;"; // Style du texte des cellules

    /*** Chemins vers les fichiers contenant les grilles de jeu ***/
    String FILE_PATH_4x4 = "kropki" + File.separator + "data" + File.separator + "kropki_4x4.txt";

}
