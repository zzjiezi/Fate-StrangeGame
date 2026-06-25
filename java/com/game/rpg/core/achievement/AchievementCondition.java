package com.game.rpg.core.achievement;

import com.game.rpg.state.GameState;

public interface AchievementCondition {
    String getAchievementId();
    boolean checkCondition(GameState gameState);
    String getDescription();
}