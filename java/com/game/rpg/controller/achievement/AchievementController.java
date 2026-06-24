package com.game.rpg.controller.achievement;

import com.game.rpg.model.entity.Achievement;
import com.game.rpg.model.state.GameStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Consumer;

public class AchievementController {
    private static final Logger logger = LoggerFactory.getLogger(AchievementController.class);
    
    private final Map<String, Achievement> achievements;
    private final GameStateManager stateManager;
    
    private Runnable onAchievementUnlocked;
    
    public AchievementController(GameStateManager stateManager) {
        this.achievements = new HashMap<>();
        this.stateManager = stateManager;
        
        initializeAchievements();
    }
    
    private void initializeAchievements() {
        Achievement sleepWar = new Achievement("sleep_war", "昏睡战争", 
                "选择继续睡觉3次");
        achievements.put(sleepWar.getId(), sleepWar);
        
        logger.info("Achievement system initialized with {} achievements", achievements.size());
    }
    
    public void checkAchievements() {
        Integer sleepCount = (Integer) stateManager.getCurrentState()
                .getCustomData("sleepCount").orElse(0);
        
        if (sleepCount >= 3) {
            unlockAchievement("sleep_war");
        }
    }
    
    public void unlockAchievement(String achievementId) {
        Achievement achievement = achievements.get(achievementId);
        
        if (achievement == null) {
            logger.warn("Achievement not found: {}", achievementId);
            return;
        }
        
        if (achievement.isUnlocked()) {
            logger.debug("Achievement already unlocked: {}", achievementId);
            return;
        }
        
        achievement.unlock();
        stateManager.updateState(state -> {
            state.addAchievement(achievement);
        });
        
        logger.info("Achievement unlocked: {} - {}", achievement.getName(), achievement.getDescription());
        
        if (onAchievementUnlocked != null) {
            onAchievementUnlocked.run();
        }
    }
    
    public List<Achievement> getAllAchievements() {
        return new ArrayList<>(achievements.values());
    }
    
    public List<Achievement> getUnlockedAchievements() {
        return achievements.values().stream()
                .filter(Achievement::isUnlocked)
                .toList();
    }
    
    public Optional<Achievement> getAchievement(String achievementId) {
        return Optional.ofNullable(achievements.get(achievementId));
    }
    
    public void setOnAchievementUnlocked(Runnable onAchievementUnlocked) {
        this.onAchievementUnlocked = onAchievementUnlocked;
    }
}