package com.game.rpg.model.enums;

public enum CommandSpellEffect {
    REVIVE_SERVANT("复活从者", CommandSpellBranch.SABER),
    DAMAGE_BOOST("伤害提升50%", CommandSpellBranch.SABER),
    HEAL_HP("回复能量50HP", CommandSpellBranch.SABER),
    MIND_CONTROL("精神操控", CommandSpellBranch.BILL),
    REFLECT_DAMAGE("反伤", CommandSpellBranch.BILL),
    PUPPET("傀儡", CommandSpellBranch.BILL);
    
    private final String displayName;
    private final CommandSpellBranch branch;
    
    CommandSpellEffect(String displayName, CommandSpellBranch branch) {
        this.displayName = displayName;
        this.branch = branch;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public CommandSpellBranch getBranch() {
        return branch;
    }
}