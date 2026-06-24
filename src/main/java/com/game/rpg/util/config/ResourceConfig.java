package com.game.rpg.util.config;

import java.util.HashMap;
import java.util.Map;

public class ResourceConfig {
    private final Map<String, String> imagePaths;
    private final Map<String, String> audioPaths;
    private final Map<String, String> dataPaths;
    
    private String basePath;
    
    public ResourceConfig() {
        this.imagePaths = new HashMap<>();
        this.audioPaths = new HashMap<>();
        this.dataPaths = new HashMap<>();
        this.basePath = "src/main/resources";
        
        initializeDefaultPaths();
    }
    
    private void initializeDefaultPaths() {
        imagePaths.put("startBackground", "D:\\我的\\java课程设计\\java美术素材\\背景.png");
        imagePaths.put("mapBackground", "D:\\我的\\java课程设计\\java美术素材\\PC _Computer - Undertale - Backgrounds - Home and New Home.png");
        imagePaths.put("saberCharacter", "D:\\我的\\java课程设计\\java美术素材\\Saber");
        
        imagePaths.put("backgrounds", "/images/backgrounds/");
        imagePaths.put("characters", "/images/characters/");
        imagePaths.put("enemies", "/images/enemies/");
        imagePaths.put("ui", "/images/ui/");
        
        audioPaths.put("bgm", "/audio/bgm/");
        audioPaths.put("sfx", "/audio/sfx/");
        
        dataPaths.put("scenes", "/data/scenes/");
        dataPaths.put("characters", "/data/characters.json");
        dataPaths.put("skills", "/data/skills.json");
        dataPaths.put("achievements", "/data/achievements.json");
    }
    
    public String getImagePath(String key) {
        return imagePaths.get(key);
    }
    
    public void setImagePath(String key, String path) {
        if (key == null || path == null) {
            throw new IllegalArgumentException("Key and path cannot be null");
        }
        imagePaths.put(key, path);
    }
    
    public String getAudioPath(String key) {
        return audioPaths.get(key);
    }
    
    public void setAudioPath(String key, String path) {
        if (key == null || path == null) {
            throw new IllegalArgumentException("Key and path cannot be null");
        }
        audioPaths.put(key, path);
    }
    
    public String getDataPath(String key) {
        return dataPaths.get(key);
    }
    
    public void setDataPath(String key, String path) {
        if (key == null || path == null) {
            throw new IllegalArgumentException("Key and path cannot be null");
        }
        dataPaths.put(key, path);
    }
    
    public String getBasePath() {
        return basePath;
    }
    
    public void setBasePath(String basePath) {
        if (basePath == null || basePath.trim().isEmpty()) {
            throw new IllegalArgumentException("Base path cannot be null or empty");
        }
        this.basePath = basePath;
    }
}