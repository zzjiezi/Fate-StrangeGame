package com.game.rpg.core.story;

import com.game.rpg.core.achievement.AchievementManager;
import com.game.rpg.state.GameState;
import com.game.rpg.state.GameStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class StoryManager {
    private static final Logger logger = LoggerFactory.getLogger(StoryManager.class);
    
    private final GameStateManager stateManager;
    private final SceneController sceneController;
    private final DialogueSystem dialogueSystem;
    private final AchievementManager achievementManager;
    
    private int currentAct;
    private int currentSceneIndex;
    
    private Consumer<Integer> onActChanged;
    private Consumer<String> onSceneChanged;
    
    public StoryManager(GameStateManager stateManager, SceneController sceneController,
                       AchievementManager achievementManager) {
        this.stateManager = stateManager;
        this.sceneController = sceneController;
        this.dialogueSystem = new DialogueSystem();
        this.achievementManager = achievementManager;
        this.currentAct = 1;
        this.currentSceneIndex = 0;
    }
    
    public void startAct(int actNumber) {
        if (actNumber < 1 || actNumber > 4) {
            logger.warn("Invalid act number: {}", actNumber);
            return;
        }
        
        currentAct = actNumber;
        currentSceneIndex = 0;
        stateManager.setCurrentAct(actNumber);
        
        String startSceneId = getStartSceneIdForAct(actNumber);
        switchScene(startSceneId);
        
        if (onActChanged != null) {
            onActChanged.accept(actNumber);
        }
        
        logger.info("Act {} started", actNumber);
    }
    
    public void switchScene(String sceneId) {
        sceneController.loadScene(sceneId);
        stateManager.setCurrentSceneId(sceneId);
        
        if (onSceneChanged != null) {
            onSceneChanged.accept(sceneId);
        }
        
        logger.info("Scene switched to: {}", sceneId);
    }
    
    public int handleBranchChoice(String branchId, int choiceIndex) {
        GameState gameState = stateManager.getCurrentState();
        gameState.getSceneState().recordChoice(branchId, choiceIndex);
        
        achievementManager.checkAchievements(gameState);
        
        logger.info("Branch choice recorded: {} -> {}", branchId, choiceIndex);
        
        return choiceIndex;
    }
    
    public void triggerInteraction(String objectId) {
        GameState gameState = stateManager.getCurrentState();
        gameState.getSceneState().markInteracted(objectId);
        
        achievementManager.checkAchievements(gameState);
        
        logger.info("Interaction triggered: {}", objectId);
    }
    
    public void incrementSleepCount() {
        GameState gameState = stateManager.getCurrentState();
        Integer sleepCount = (Integer)gameState.getCustomData("sleepCount").orElse(0);
        gameState.setCustomData("sleepCount", sleepCount + 1);
        
        achievementManager.checkAchievements(gameState);
        
        logger.info("Sleep count incremented to: {}", sleepCount + 1);
    }
    
    public void startDialogue(String speakerName, String portraitPath, List<String> lines) {
        dialogueSystem.startDialogue(speakerName, portraitPath, lines);
    }
    
    public void advanceDialogue() {
        dialogueSystem.showNextLine();
    }
    
    private String getStartSceneIdForAct(int act) {
        return switch (act) {
            case 1 -> "act1_scene1";
            case 2 -> "act2_scene1";
            case 3 -> "act3_scene1";
            case 4 -> "act4_scene1";
            default -> "start";
        };
    }
    
    public int getCurrentAct() {
        return currentAct;
    }
    
    public int getCurrentSceneIndex() {
        return currentSceneIndex;
    }
    
    public DialogueSystem getDialogueSystem() {
        return dialogueSystem;
    }
    
    public SceneController getSceneController() {
        return sceneController;
    }
    
    public void setOnActChanged(Consumer<Integer> onActChanged) {
        this.onActChanged = onActChanged;
    }
    
    public void setOnSceneChanged(Consumer<String> onSceneChanged) {
        this.onSceneChanged = onSceneChanged;
    }
}