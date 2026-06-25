package com.game.rpg.model.entities;

import com.game.rpg.util.ValidationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NPC {
    private final String id;
    private final String name;
    private String portraitPath;
    private double x;
    private double y;
    private final List<String> dialogues;
    private int currentDialogueIndex;
    
    public NPC(String id, String name, String portraitPath, double x, double y) {
        ValidationUtils.requireNonNull(id, "NPC ID cannot be null");
        ValidationUtils.requireNonNull(name, "NPC name cannot be null");
        
        this.id = id;
        this.name = name;
        this.portraitPath = portraitPath;
        this.x = x;
        this.y = y;
        this.dialogues = new ArrayList<>();
        this.currentDialogueIndex = 0;
    }
    
    public void addDialogue(String dialogue) {
        ValidationUtils.requireNonNull(dialogue, "Dialogue cannot be null");
        dialogues.add(dialogue);
    }
    
    public String getCurrentDialogue() {
        if (dialogues.isEmpty() || currentDialogueIndex >= dialogues.size()) {
            return null;
        }
        return dialogues.get(currentDialogueIndex);
    }
    
    public boolean hasNextDialogue() {
        return currentDialogueIndex < dialogues.size() - 1;
    }
    
    public void nextDialogue() {
        if (hasNextDialogue()) {
            currentDialogueIndex++;
        }
    }
    
    public void resetDialogue() {
        currentDialogueIndex = 0;
    }
    
    public List<String> getDialogues() {
        return Collections.unmodifiableList(dialogues);
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPortraitPath() {
        return portraitPath;
    }
    
    public void setPortraitPath(String portraitPath) {
        this.portraitPath = portraitPath;
    }
    
    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    @Override
    public String toString() {
        return String.format("NPC{id='%s', name='%s'}", id, name);
    }
}