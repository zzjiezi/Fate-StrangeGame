package com.game.rpg.core.achievement;

import com.game.rpg.state.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AchievementChecker {
    private static final Logger logger = LoggerFactory.getLogger(AchievementChecker.class);
    
    private final List<AchievementCondition> conditions;
    
    public AchievementChecker() {
        this.conditions = new ArrayList<>();
    }
    
    public void registerCondition(AchievementCondition condition) {
        if (condition == null) {
            throw new IllegalArgumentException("Condition cannot be null");
        }
        
        conditions.add(condition);
        logger.debug("Achievement condition registered: {}", condition.getAchievementId());
    }
    
    public Optional<AchievementCondition> checkCondition(String achievementId, GameState gameState) {
        if (achievementId == null || gameState == null) {
            return Optional.empty();
        }
        
        for (AchievementCondition condition : conditions) {
            if (condition.getAchievementId().equals(achievementId)) {
                if (condition.checkCondition(gameState)) {
                    logger.info("Achievement condition met: {}", achievementId);
                    return Optional.of(condition);
                }
            }
        }
        
        return Optional.empty();
    }
    
    public List<AchievementCondition> checkAllConditions(GameState gameState) {
        List<AchievementCondition> metConditions = new ArrayList<>();
        
        for (AchievementCondition condition : conditions) {
            if (condition.checkCondition(gameState)) {
                metConditions.add(condition);
                logger.info("Achievement condition met: {}", condition.getAchievementId());
            }
        }
        
        return metConditions;
    }
    
    public List<AchievementCondition> getConditions() {
        return new ArrayList<>(conditions);
    }
}