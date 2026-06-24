package com.game.rpg.model.enums;

public enum SkillType {
    NORMAL_ATTACK("普通攻击"),
    NOBLE_PHANTASM("宝具"),
    COMMAND_SPELL("令咒");
    
    private final String displayName;
    
    SkillType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}