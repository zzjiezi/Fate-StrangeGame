package com.game.rpg.core.save;

import com.game.rpg.state.GameState;
import com.game.rpg.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class GameStateSerializer {
    private static final Logger logger = LoggerFactory.getLogger(GameStateSerializer.class);
    
    public String serialize(GameState gameState) {
        if (gameState == null) {
            throw new IllegalArgumentException("Game state cannot be null");
        }
        
        try {
            String json = JsonUtils.toJson(gameState);
            logger.debug("Game state serialized successfully");
            return json;
        } catch (Exception e) {
            logger.error("Failed to serialize game state", e);
            throw new RuntimeException("Failed to serialize game state", e);
        }
    }
    
    public GameState deserialize(String json) {
        if (json == null || json.trim().isEmpty()) {
            throw new IllegalArgumentException("JSON string cannot be null or empty");
        }
        
        try {
            GameState gameState = JsonUtils.fromJson(json, GameState.class);
            logger.debug("Game state deserialized successfully");
            return gameState;
        } catch (Exception e) {
            logger.error("Failed to deserialize game state", e);
            throw new RuntimeException("Failed to deserialize game state", e);
        }
    }
    
    public void saveToFile(File file, GameState gameState) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (gameState == null) {
            throw new IllegalArgumentException("Game state cannot be null");
        }
        
        try {
            JsonUtils.writeToFile(file, gameState);
            logger.info("Game state saved to file: {}", file.getAbsolutePath());
        } catch (Exception e) {
            logger.error("Failed to save game state to file", e);
            throw new RuntimeException("Failed to save game state to file", e);
        }
    }
    
    public GameState loadFromFile(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        
        try {
            GameState gameState = JsonUtils.readFromFile(file, GameState.class);
            logger.info("Game state loaded from file: {}", file.getAbsolutePath());
            return gameState;
        } catch (Exception e) {
            logger.error("Failed to load game state from file", e);
            throw new RuntimeException("Failed to load game state from file", e);
        }
    }
}