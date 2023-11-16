package com.kropkigame.model;

import java.io.File;

public interface KropkiConstants {
    int GRID_SIZE = 4; // for a 4x4 grid
    int CELL_SIZE = 60; // Adjust size as needed.
    String FILE_PATH = "kropki" + File.separator + "data" + File.separator + "kropki_4x4.txt";
    int SCENE_WIDTH = 500;
    int SCENE_HEIGHT = 600;
    String NUMBER_BUTTON_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 14pt; -fx-pref-width: 50px; -fx-pref-height: 50px;";
    String CELL_TEXT_STYLE = "-fx-font-family: Helvetica; -fx-font-size: 20pt; -fx-font-weight: bold;";
}
