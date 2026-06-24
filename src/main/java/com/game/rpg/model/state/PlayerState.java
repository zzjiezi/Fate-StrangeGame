package com.game.rpg.model.state;

import com.game.rpg.model.entity.Character;
import com.game.rpg.model.enums.CommandSpellBranch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerState {
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int attackPower;
    private int commandSpellCount;
    private CommandSpellBranch commandSpellBranch;
    private final List<String> inventory;
    
    public PlayerState() {
        this.inventory = new ArrayList<>();
    }
    
    public static PlayerState fromCharacter(Character character) {
        PlayerState state = new PlayerState();
        state.hp = character.getHp();
        state.maxHp = character.getMaxHp();
        state.mp = character.getMp();
        state.maxMp = character.getMaxMp();
        state.attackPower = character.getAttackPower();
        state.commandSpellCount = character.getCommandSpellCount();
        state.commandSpellBranch = character.getCommandSpellBranch();
        return state;
    }
    
    public void applyToCharacter(Character character) {
        while (character.getHp() < hp && character.getHp() < character.getMaxHp()) {
            character.heal(1);
        }
        while (character.getMp() < mp && character.getMp() < character.getMaxMp()) {
            character.restoreMP(1);
        }
    }
    
    public void addItem(String itemId) {
        if (itemId != null && !itemId.trim().isEmpty()) {
            inventory.add(itemId);
        }
    }
    
    public boolean removeItem(String itemId) {
        return inventory.remove(itemId);
    }
    
    public boolean hasItem(String itemId) {
        return inventory.contains(itemId);
    }
    
    public List<String> getInventory() {
        return Collections.unmodifiableList(inventory);
    }
    
    public int getHp() {
        return hp;
    }
    
    public void setHp(int hp) {
        this.hp = Math.max(0, Math.min(maxHp, hp));
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(1, maxHp);
    }
    
    public int getMp() {
        return mp;
    }
    
    public void setMp(int mp) {
        this.mp = Math.max(0, Math.min(maxMp, mp));
    }
    
    public int getMaxMp() {
        return maxMp;
    }
    
    public void setMaxMp(int maxMp) {
        this.maxMp = Math.max(1, maxMp);
    }
    
    public int getAttackPower() {
        return attackPower;
    }
    
    public void setAttackPower(int attackPower) {
        this.attackPower = Math.max(0, attackPower);
    }
    
    public int getCommandSpellCount() {
        return commandSpellCount;
    }
    
    public void setCommandSpellCount(int commandSpellCount) {
        this.commandSpellCount = Math.max(0, Math.min(3, commandSpellCount));
    }
    
    public CommandSpellBranch getCommandSpellBranch() {
        return commandSpellBranch;
    }
    
    public void setCommandSpellBranch(CommandSpellBranch commandSpellBranch) {
        this.commandSpellBranch = commandSpellBranch;
    }
}