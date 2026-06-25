package com.game.rpg.model.entities;

import com.game.rpg.model.enums.CommandSpellBranch;
import com.game.rpg.util.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Character {
    private final String id;
    private final String name;
    private int hp;
    private int maxHp;
    private int mp;
    private int maxMp;
    private int attackPower;
    private int commandSpellCount;
    private final CommandSpellBranch commandSpellBranch;
    private final List<Skill> skills;
    
    public Character(String id, String name, int maxHp, int maxMp, int attackPower, 
                     CommandSpellBranch commandSpellBranch) {
        ValidationUtils.requireNonNull(id, "Character ID cannot be null");
        ValidationUtils.requireNonNull(name, "Character name cannot be null");
        ValidationUtils.requirePositive(maxHp, "Max HP must be positive");
        ValidationUtils.requirePositive(maxMp, "Max MP must be positive");
        ValidationUtils.requireNonNegative(attackPower, "Attack power must be non-negative");
        
        this.id = id;
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxMp = maxMp;
        this.mp = maxMp;
        this.attackPower = attackPower;
        this.commandSpellCount = 3;
        this.commandSpellBranch = commandSpellBranch;
        this.skills = new ArrayList<>();
    }
    
    public boolean isAlive() {
        return hp > 0;
    }
    
    public boolean canCastSkill(Skill skill) {
        ValidationUtils.requireNonNull(skill, "Skill cannot be null");
        return mp >= skill.getMpCost();
    }
    
    public void takeDamage(int damage) {
        ValidationUtils.requireNonNegative(damage, "Damage must be non-negative");
        hp = Math.max(0, hp - damage);
    }
    
    public void heal(int amount) {
        ValidationUtils.requireNonNegative(amount, "Heal amount must be non-negative");
        hp = Math.min(maxHp, hp + amount);
    }
    
    public void consumeMP(int amount) {
        ValidationUtils.requireNonNegative(amount, "MP amount must be non-negative");
        mp = Math.max(0, mp - amount);
    }
    
    public void restoreMP(int amount) {
        ValidationUtils.requireNonNegative(amount, "MP amount must be non-negative");
        mp = Math.min(maxMp, mp + amount);
    }
    
    public boolean useCommandSpell() {
        if (commandSpellCount > 0) {
            commandSpellCount--;
            return true;
        }
        return false;
    }
    
    public void addSkill(Skill skill) {
        ValidationUtils.requireNonNull(skill, "Skill cannot be null");
        skills.add(skill);
    }
    
    public List<Skill> getSkills() {
        return Collections.unmodifiableList(skills);
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getHp() {
        return hp;
    }
    
    public int getMaxHp() {
        return maxHp;
    }
    
    public int getMp() {
        return mp;
    }
    
    public int getMaxMp() {
        return maxMp;
    }
    
    public int getAttackPower() {
        return attackPower;
    }
    
    public int getCommandSpellCount() {
        return commandSpellCount;
    }
    
    public CommandSpellBranch getCommandSpellBranch() {
        return commandSpellBranch;
    }
    
    @Override
    public String toString() {
        return String.format("Character{id='%s', name='%s', hp=%d/%d, mp=%d/%d}",
                id, name, hp, maxHp, mp, maxMp);
    }
}