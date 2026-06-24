package com.game.rpg.core.achievement;

import com.game.rpg.core.save.SaveManager;
import com.game.rpg.model.entities.Achievement;
import com.game.rpg.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class AchievementManager {
    private static final Logger logger = LoggerFactory.getLogger(AchievementManager.class);
    
    private final Map<String, Achievement> achievements;
    private final AchievementChecker checker;
    private final SaveManager saveManager;
    private Runnable onAchievementUnlocked;
    
    public AchievementManager(SaveManager saveManager) {
        this.achievements = new HashMap<>();
        this.checker = new AchievementChecker();
        this.saveManager = saveManager;
        
        initialize();
    }
    
    private void initialize() {
        Achievement sleepWar = new Achievement("sleep_war", "昏睡战争", 
                "选择继续睡觉3次");
        achievements.put(sleepWar.getId(), sleepWar);
        
        checker.registerCondition(new SleepWarCondition());
        
        logger.info("Achievement system initialized with {} achievements", achievements.size());
    }
    
    public void checkAchievements(GameState gameState) {
        if (gameState == null) {
            return;
        }
        
        List<AchievementCondition> metConditions = checker.checkAllConditions(gameState);
        
        for (AchievementCondition condition : metConditions) {
            Achievement achievement = achievements.get(condition.getAchievementId());
            if (achievement != null && !achievement.isUnlocked()) {
                unlockAchievement(achievement.getId(), gameState);
            }
        }
    }
    
    public void unlockAchievement(String achievementId, GameState gameState) {
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
        gameState.addAchievement(achievement);
        
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
    
    public AchievementChecker getChecker() {
        return checker;
    }
}