package com.game.rpg.model.entities;

import com.game.rpg.model.enums.SkillType;
import com.game.rpg.util.ValidationUtils;

public class Skill {
    private final String id;
    private final String name;
    private final int baseDamage;
    private final int mpCost;
    private final SkillType type;
    private final String description;
    
    public Skill(String id, String name, int baseDamage, int mpCost, SkillType type, String description) {
        ValidationUtils.requireNonNull(id, "Skill ID cannot be null");
        ValidationUtils.requireNonNull(name, "Skill name cannot be null");
        ValidationUtils.requireNonNull(type, "Skill type cannot be null");
        ValidationUtils.requireNonNegative(baseDamage, "Base damage must be non-negative");
        ValidationUtils.requireNonNegative(mpCost, "MP cost must be non-negative");
        
        this.id = id;
        this.name = name;
        this.baseDamage = baseDamage;
        this.mpCost = mpCost;
        this.type = type;
        this.description = description != null ? description : "";
    }
    
    public int calculateDamage(double multiplier) {
        ValidationUtils.requireInRange(multiplier, 0.0, 10.0, "Damage multiplier must be between 0.0 and 10.0");
        return (int) Math.round(baseDamage * multiplier);
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public int getBaseDamage() {
        return baseDamage;
    }
    
    public int getMpCost() {
        return mpCost;
    }
    
    public SkillType getType() {
        return type;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return String.format("Skill{id='%s', name='%s', baseDamage=%d, mpCost=%d, type=%s}",
                id, name, baseDamage, mpCost, type);
    }
}