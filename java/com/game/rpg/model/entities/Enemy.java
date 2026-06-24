package com.game.rpg.model.entities;

import com.game.rpg.util.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Enemy {
    private final String id;
    private final String name;
    private int hp;
    private int maxHp;
    private int attackPower;
    private final List<Skill> skills;
    private final Random random;
    
    public Enemy(String id, String name, int maxHp, int attackPower) {
        ValidationUtils.requireNonNull(id, "Enemy ID cannot be null");
        ValidationUtils.requireNonNull(name, "Enemy name cannot be null");
        ValidationUtils.requirePositive(maxHp, "Max HP must be positive");
        ValidationUtils.requireNonNegative(attackPower, "Attack power must be non-negative");
        
        this.id = id;
        this.name = name;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.attackPower = attackPower;
        this.skills = new ArrayList<>();
        this.random = new Random();
    }
    
    public boolean isAlive() {
        return hp > 0;
    }
    
    public void takeDamage(int damage) {
        ValidationUtils.requireNonNegative(damage, "Damage must be non-negative");
        hp = Math.max(0, hp - damage);
    }
    
    public Skill decideAction() {
        if (skills.isEmpty()) {
            return null;
        }
        
        if (random.nextDouble() < 0.7) {
            return null;
        }
        
        return skills.get(random.nextInt(skills.size()));
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
    
    public int getAttackPower() {
        return attackPower;
    }
    
    @Override
    public String toString() {
        return String.format("Enemy{id='%s', name='%s', hp=%d/%d}", id, name, hp, maxHp);
    }
}