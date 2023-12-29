package com.kropkigame.view;

public interface SceneSwitcher {
    void switchToGame(String difficulty, int levelNumber);
    void switchToDifficultySelection();
    void switchToFirstMenu();
    void showLevelSelection(String difficulty);
}

