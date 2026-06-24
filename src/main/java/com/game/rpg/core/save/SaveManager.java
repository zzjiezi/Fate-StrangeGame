package com.game.rpg.core.save;

import com.game.rpg.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SaveManager {
    private static final Logger logger = LoggerFactory.getLogger(SaveManager.class);
    private static final int MAX_SAVE_SLOTS = 5;
    private static final String SAVE_DIR = "saves";
    private static final String SAVE_FILE_PREFIX = "save_";
    private static final String SAVE_FILE_EXTENSION = ".json";
    
    private final GameStateSerializer serializer;
    private final File saveDirectory;
    
    public SaveManager() {
        this.serializer = new GameStateSerializer();
        this.saveDirectory = new File(SAVE_DIR);
        
        if (!saveDirectory.exists()) {
            boolean created = saveDirectory.mkdirs();
            if (created) {
                logger.info("Save directory created: {}", SAVE_DIR);
            }
        }
    }
    
    public void saveGame(int slot, GameState gameState) {
        if (slot < 1 || slot > MAX_SAVE_SLOTS) {
            throw new IllegalArgumentException("Invalid save slot: " + slot);
        }
        if (gameState == null) {
            throw new IllegalArgumentException("Game state cannot be null");
        }
        
        gameState.setSaveTime(LocalDateTime.now());
        
        File saveFile = getSaveFile(slot);
        serializer.saveToFile(saveFile, gameState);
        
        logger.info("Game saved to slot {}", slot);
    }
    
    public Optional<GameState> loadGame(int slot) {
        if (slot < 1 || slot > MAX_SAVE_SLOTS) {
            throw new IllegalArgumentException("Invalid save slot: " + slot);
        }
        
        File saveFile = getSaveFile(slot);
        
        if (!saveFile.exists()) {
            logger.warn("Save file does not exist for slot {}", slot);
            return Optional.empty();
        }
        
        try {
            GameState gameState = serializer.loadFromFile(saveFile);
            logger.info("Game loaded from slot {}", slot);
            return Optional.of(gameState);
        } catch (Exception e) {
            logger.error("Failed to load game from slot {}", slot, e);
            return Optional.empty();
        }
    }
    
    public boolean deleteSave(int slot) {
        if (slot < 1 || slot > MAX_SAVE_SLOTS) {
            throw new IllegalArgumentException("Invalid save slot: " + slot);
        }
        
        File saveFile = getSaveFile(slot);
        
        if (saveFile.exists()) {
            boolean deleted = saveFile.delete();
            if (deleted) {
                logger.info("Save deleted from slot {}", slot);
                return true;
            }
        }
        
        return false;
    }
    
    public List<SaveSlotInfo> getSaveSlots() {
        List<SaveSlotInfo> slots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 1; i <= MAX_SAVE_SLOTS; i++) {
            File saveFile = getSaveFile(i);
            
            if (saveFile.exists()) {
                try {
                    GameState gameState = serializer.loadFromFile(saveFile);
                    LocalDateTime saveTime = gameState.getSaveTime();
                    String formattedTime = saveTime != null ? saveTime.format(formatter) : "Unknown";
                    
                    slots.add(new SaveSlotInfo(i, true, formattedTime, 
                            gameState.getCurrentAct(), gameState.getCurrentSceneId()));
                } catch (Exception e) {
                    logger.error("Failed to read save slot {}", i, e);
                    slots.add(new SaveSlotInfo(i, false, null, 0, null));
                }
            } else {
                slots.add(new SaveSlotInfo(i, false, null, 0, null));
            }
        }
        
        return slots;
    }
    
    public boolean hasSave(int slot) {
        if (slot < 1 || slot > MAX_SAVE_SLOTS) {
            return false;
        }
        return getSaveFile(slot).exists();
    }
    
    private File getSaveFile(int slot) {
        String fileName = SAVE_FILE_PREFIX + slot + SAVE_FILE_EXTENSION;
        return new File(saveDirectory, fileName);
    }
    
    public static class SaveSlotInfo {
        private final int slot;
        private final boolean hasData;
        private final String saveTime;
        private final int currentAct;
        private final String currentSceneId;
        
        public SaveSlotInfo(int slot, boolean hasData, String saveTime, 
                           int currentAct, String currentSceneId) {
            this.slot = slot;
            this.hasData = hasData;
            this.saveTime = saveTime;
            this.currentAct = currentAct;
            this.currentSceneId = currentSceneId;
        }
        
        public int getSlot() {
            return slot;
        }
        
        public boolean hasData() {
            return hasData;
        }
        
        public String getSaveTime() {
            return saveTime;
        }
        
        public int getCurrentAct() {
            return currentAct;
        }
        
        public String getCurrentSceneId() {
            return currentSceneId;
        }
    }
}