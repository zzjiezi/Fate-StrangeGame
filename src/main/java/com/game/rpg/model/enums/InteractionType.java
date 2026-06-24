package com.game.rpg.model.enums;

public enum InteractionType {
    EXAMINE("查看"),
    TALK("对话"),
    PICK_UP("拾取"),
    USE("使用"),
    TRIGGER("触发");
    
    private final String displayName;
    
    InteractionType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}