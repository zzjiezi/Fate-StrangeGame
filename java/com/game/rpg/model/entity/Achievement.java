package com.game.rpg.model.entity;

import com.game.rpg.util.validation.ValidationUtil;

import java.time.LocalDateTime;
import java.util.Objects;

public class Achievement {
    private final String id;
    private final String name;
    private final String description;
    private boolean unlocked;
    private LocalDateTime unlockTime;
    private String iconPath;
    
    public Achievement(String id, String name, String description) {
        ValidationUtil.requireNonNull(id, "Achievement ID cannot be null");
        ValidationUtil.requireNonNull(name, "Achievement name cannot be null");
        
        this.id = id;
        this.name = name;
        this.description = description != null ? description : "";
        this.unlocked = false;
        this.unlockTime = null;
    }
    
    public void unlock() {
        if (!unlocked) {
            unlocked = true;
            unlockTime = LocalDateTime.now();
        }
    }
    
    public boolean isUnlocked() {
        return unlocked;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public LocalDateTime getUnlockTime() {
        return unlockTime;
    }
    
    public String getIconPath() {
        return iconPath;
    }
    
    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(id, that.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return String.format("Achievement{id='%s', name='%s', unlocked=%s}", 
                id, name, unlocked);
    }
}