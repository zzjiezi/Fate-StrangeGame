package com.game.rpg.config;

public class GameConfig {
    private int windowWidth;
    private int windowHeight;
    private int targetFps;
    private String gameTitle;
    
    public GameConfig() {
        this.windowWidth = 1280;
        this.windowHeight = 720;
        this.targetFps = 60;
        this.gameTitle = "RPG Game - 圣杯战争";
    }
    
    public int getWindowWidth() {
        return windowWidth;
    }
    
    public void setWindowWidth(int windowWidth) {
        if (windowWidth <= 0) {
            throw new IllegalArgumentException("Window width must be positive");
        }
        this.windowWidth = windowWidth;
    }
    
    public int getWindowHeight() {
        return windowHeight;
    }
    
    public void setWindowHeight(int windowHeight) {
        if (windowHeight <= 0) {
            throw new IllegalArgumentException("Window height must be positive");
        }
        this.windowHeight = windowHeight;
    }
    
    public int getTargetFps() {
        return targetFps;
    }
    
    public void setTargetFps(int targetFps) {
        if (targetFps <= 0) {
            throw new IllegalArgumentException("Target FPS must be positive");
        }
        this.targetFps = targetFps;
    }
    
    public String getGameTitle() {
        return gameTitle;
    }
    
    public void setGameTitle(String gameTitle) {
        if (gameTitle == null || gameTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Game title cannot be null or empty");
        }
        this.gameTitle = gameTitle;
    }
}