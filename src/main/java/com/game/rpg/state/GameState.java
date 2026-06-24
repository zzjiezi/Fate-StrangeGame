package com.game.rpg.state;

import com.game.rpg.model.entities.Achievement;

import java.time.LocalDateTime;
import java.util.*;

public class GameState {
    private int currentAct;
    private String currentSceneId;
    private PlayerState playerState;
    private SceneState sceneState;
    private BattleState battleState;
    private final List<Achievement> achievements;
    private LocalDateTime saveTime;
    private final Map<String, Object> customData;
    
    public GameState() {
        this.currentAct = 1;
        this.currentSceneId = "start";
        this.playerState = new PlayerState();
        this.sceneState = new SceneState();
        this.battleState = new BattleState();
        this.achievements = new ArrayList<>();
        this.saveTime = LocalDateTime.now();
        this.customData = new HashMap<>();
    }
    
    public GameState createSnapshot() {
        GameState snapshot = new GameState();
        snapshot.currentAct = this.currentAct;
        snapshot.currentSceneId = this.currentSceneId;
        snapshot.playerState = this.playerState;
        snapshot.sceneState = this.sceneState;
        snapshot.battleState = this.battleState;
        snapshot.achievements.addAll(this.achievements);
        snapshot.saveTime = LocalDateTime.now();
        snapshot.customData.putAll(this.customData);
        return snapshot;
    }
    
    public void addAchievement(Achievement achievement) {
        if (achievement != null && !achievements.contains(achievement)) {
            achievements.add(achievement);
        }
    }
    
    public Optional<Achievement> getAchievement(String achievementId) {
        return achievements.stream()
                .filter(a -> a.getId().equals(achievementId))
                .findFirst();
    }
    
    public List<Achievement> getUnlockedAchievements() {
        return achievements.stream()
                .filter(Achievement::isUnlocked)
                .toList();
    }
    
    public void setCustomData(String key, Object value) {
        if (key != null) {
            customData.put(key, value);
        }
    }
    
    @SuppressWarnings("unchecked")
    public <T> Optional<T> getCustomData(String key) {
        return Optional.ofNullable((T) customData.get(key));
    }
    
    public int getCurrentAct() {
        return currentAct;
    }
    
    public void setCurrentAct(int currentAct) {
        this.currentAct = Math.max(1, Math.min(4, currentAct));
    }
    
    public String getCurrentSceneId() {
        return currentSceneId;
    }
    
    public void setCurrentSceneId(String currentSceneId) {
        this.currentSceneId = currentSceneId;
    }
    
    public PlayerState getPlayerState() {
        return playerState;
    }
    
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }
    
    public SceneState getSceneState() {
        return sceneState;
    }
    
    public void setSceneState(SceneState sceneState) {
        this.sceneState = sceneState;
    }
    
    public BattleState getBattleState() {
        return battleState;
    }
    
    public void setBattleState(BattleState battleState) {
        this.battleState = battleState;
    }
    
    public List<Achievement> getAchievements() {
        return Collections.unmodifiableList(achievements);
    }
    
    public LocalDateTime getSaveTime() {
        return saveTime;
    }
    
    public void setSaveTime(LocalDateTime saveTime) {
        this.saveTime = saveTime;
    }
}