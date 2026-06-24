package com.game.rpg.model.enums;

public enum CommandSpellBranch {
    SABER("Saber线"),
    BILL("Bill线");
    
    private final String displayName;
    
    CommandSpellBranch(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}