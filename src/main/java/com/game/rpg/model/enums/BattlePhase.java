package com.game.rpg.model.enums;

public enum BattlePhase {
    PLAYER_TURN("玩家回合"),
    ENEMY_TURN("敌人回合"),
    BATTLE_END("战斗结束");
    
    private final String displayName;
    
    BattlePhase(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}