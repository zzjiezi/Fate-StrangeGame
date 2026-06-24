package com.game.rpg.model.state;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameStateManager {
    private static final Logger logger = LoggerFactory.getLogger(GameStateManager.class);
    
    private GameState currentState;
    private final List<Consumer<GameState>> stateListeners;
    
    public GameStateManager() {
        this.currentState = new GameState();
        this.stateListeners = new ArrayList<>();
    }
    
    public GameState getCurrentState() {
        return currentState;
    }
    
    public void updateState(Consumer<GameState> updater) {
        updater.accept(currentState);
        notifyStateListeners();
    }
    
    public GameState createSnapshot() {
        return currentState.createSnapshot();
    }
    
    public void restoreFromSnapshot(GameState snapshot) {
        if (snapshot == null) {
            throw new IllegalArgumentException("Snapshot cannot be null");
        }
        this.currentState = snapshot;
        notifyStateListeners();
        logger.info("Game state restored from snapshot");
    }
    
    public void addStateListener(Consumer<GameState> listener) {
        if (listener != null) {
            stateListeners.add(listener);
        }
    }
    
    public void removeStateListener(Consumer<GameState> listener) {
        stateListeners.remove(listener);
    }
    
    private void notifyStateListeners() {
        for (Consumer<GameState> listener : stateListeners) {
            try {
                listener.accept(currentState);
            } catch (Exception e) {
                logger.error("Error notifying state listener", e);
            }
        }
    }
    
    public PlayerState getPlayerState() {
        return currentState.getPlayerState();
    }
    
    public SceneState getSceneState() {
        return currentState.getSceneState();
    }
    
    public BattleState getBattleState() {
        return currentState.getBattleState();
    }
    
    public int getCurrentAct() {
        return currentState.getCurrentAct();
    }
    
    public void setCurrentAct(int act) {
        currentState.setCurrentAct(act);
        notifyStateListeners();
        logger.info("Current act changed to: {}", act);
    }
    
    public String getCurrentSceneId() {
        return currentState.getCurrentSceneId();
    }
    
    public void setCurrentSceneId(String sceneId) {
        currentState.setCurrentSceneId(sceneId);
        notifyStateListeners();
        logger.info("Current scene changed to: {}", sceneId);
    }
}