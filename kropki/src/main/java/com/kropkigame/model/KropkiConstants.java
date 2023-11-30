package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {

    /*** Sizes of view components ***/
    int CELL_SIZE = 80; // Size of a cell
    int SCENE_WIDTH = 600; // Width of the scene
    int SCENE_HEIGHT = 800; // Height of the scene

    /*** Styles of view components ***/
    String NUMBER_BUTTON_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;"; // Style of number selection buttons
    String CELL_TEXT_STYLE = "-fx-fill: white; -fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;"; // Text style for cells
    String CELL_BORDER_STYLE = "-fx-border-color: white; -fx-border-width: 1px;"; // Border style for cells
    String GAMEBOARD_STYLE = "-fx-background-color: black;" // Background color of the game board
            + "-fx-border-color: white;" // Border color of the game board
            + "-fx-border-width: 2px;"; // Border width of the game board
    String NUMBER_BAR_STYLE = "-fx-border-color: white; -fx-border-width: 2px; -fx-padding: 10px;"; // Style of the number bar

    /*** Paths to files containing the game grids ***/
    String FILE_PATH_4x4 = "kropki" + File.separator + "data" + File.separator + "kropki_4x4.txt";
    String FILE_PATH_5x5 = "kropki" + File.separator + "data" + File.separator + "kropki_5x5.txt";
    String FILE_PATH_6x6 = "kropki" + File.separator + "data" + File.separator + "kropki_6x6.txt";
    String FILE_PATH_7x7 = "kropki" + File.separator + "data" + File.separator + "kropki_7x7.txt";
    String FILE_PATH_8x8 = "kropki" + File.separator + "data" + File.separator + "kropki_8x8.txt";
}
