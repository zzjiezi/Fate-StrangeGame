package com.game.rpg.controller.story;

import com.game.rpg.model.entity.Scene;
import com.game.rpg.model.state.GameStateManager;
import com.game.rpg.util.resource.ResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class StoryController {
    private static final Logger logger = LoggerFactory.getLogger(StoryController.class);
    
    private final GameStateManager stateManager;
    private final ResourceLoader resourceLoader;
    private final DialogueController dialogueController;
    
    private int currentAct;
    private int currentSceneIndex;
    
    private Consumer<Integer> onActChanged;
    private Consumer<String> onSceneChanged;
    
    public StoryController(GameStateManager stateManager, ResourceLoader resourceLoader) {
        this.stateManager = stateManager;
        this.resourceLoader = resourceLoader;
        this.dialogueController = new DialogueController();
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
        stateManager.setCurrentSceneId(sceneId);
        
        if (onSceneChanged != null) {
            onSceneChanged.accept(sceneId);
        }
        
        logger.info("Scene switched to: {}", sceneId);
    }
    
    public int handleBranchChoice(String branchId, int choiceIndex) {
        stateManager.updateState(state -> {
            state.getSceneState().recordChoice(branchId, choiceIndex);
        });
        
        logger.info("Branch choice recorded: {} -> {}", branchId, choiceIndex);
        
        return choiceIndex;
    }
    
    public void triggerInteraction(String objectId) {
        stateManager.updateState(state -> {
            state.getSceneState().markInteracted(objectId);
        });
        
        logger.info("Interaction triggered: {}", objectId);
    }
    
    public void incrementSleepCount() {
        stateManager.updateState(state -> {
            Integer sleepCount = (Integer) state.getCustomData("sleepCount").orElse(0);
            state.setCustomData("sleepCount", sleepCount + 1);
        });
        
        logger.info("Sleep count incremented");
    }
    
    public void startDialogue(String speakerName, String portraitPath, List<String> lines) {
        dialogueController.startDialogue(speakerName, portraitPath, lines);
    }
    
    public void advanceDialogue() {
        dialogueController.showNextLine();
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
    
    public DialogueController getDialogueController() {
        return dialogueController;
    }
    
    public void setOnActChanged(Consumer<Integer> onActChanged) {
        this.onActChanged = onActChanged;
    }
    
    public void setOnSceneChanged(Consumer<String> onSceneChanged) {
        this.onSceneChanged = onSceneChanged;
    }
}