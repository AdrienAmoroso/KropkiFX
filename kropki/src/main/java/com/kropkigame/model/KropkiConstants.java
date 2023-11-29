package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {

    /*** Sizes of view components ***/
    int CELL_SIZE = 60; // Size of a cell
    int SCENE_WIDTH = 500; // Width of the scene
    int SCENE_HEIGHT = 600; // Height of the scene

    /*** Styles of view components ***/
    String NUMBER_BUTTON_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;"; // Style of number selection buttons
    String CELL_TEXT_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;"; // Text style for cells

    /*** Paths to files containing the game grids ***/
    String FILE_PATH_4x4 = "kropki" + File.separator + "data" + File.separator + "kropki_4x4.txt";
    String FILE_PATH_5x5 = "kropki" + File.separator + "data" + File.separator + "kropki_5x5.txt";
    String FILE_PATH_6x6 = "kropki" + File.separator + "data" + File.separator + "kropki_6x6.txt";
    String FILE_PATH_7x7 = "kropki" + File.separator + "data" + File.separator + "kropki_7x7.txt";
    String FILE_PATH_8x8 = "kropki" + File.separator + "data" + File.separator + "kropki_8x8.txt";
}
