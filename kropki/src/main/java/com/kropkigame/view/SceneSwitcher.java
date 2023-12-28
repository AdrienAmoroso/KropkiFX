package com.kropkigame.view;

public interface SceneSwitcher {
    void switchToGame();
    void switchToDifficultySelection();
    void switchToFirstMenu();
    void showLevelSelection(String difficulty);
}

