package com.game.rpg.model.enums;

public enum JudgementResult {
    FAIL(0.0, "失败"),
    SMALL_SUCCESS(1.0, "小成功"),
    GREAT_SUCCESS(1.25, "大成功");
    
    private final double damageMultiplier;
    private final String displayName;
    
    JudgementResult(double damageMultiplier, String displayName) {
        this.damageMultiplier = damageMultiplier;
        this.displayName = displayName;
    }
    
    public double getDamageMultiplier() {
        return damageMultiplier;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}