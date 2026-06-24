package com.game.rpg.core.achievement;

import com.game.rpg.state.GameState;

public class SleepWarCondition implements AchievementCondition {
    private static final String ACHIEVEMENT_ID = "sleep_war";
    private static final int REQUIRED_SLEEP_COUNT = 3;
    
    @Override
    public String getAchievementId() {
        return ACHIEVEMENT_ID;
    }
    
    @Override
    public boolean checkCondition(GameState gameState) {
        if (gameState == null) {
            return false;
        }
        
        Integer sleepCount = (Integer) gameState.getCustomData("sleepCount").orElse(0);
        return sleepCount >= REQUIRED_SLEEP_COUNT;
    }
    
    @Override
    public String getDescription() {
        return "选择继续睡觉3次";
    }
}