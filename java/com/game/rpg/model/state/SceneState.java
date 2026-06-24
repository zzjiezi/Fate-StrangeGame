package com.game.rpg.model.state;

import java.util.*;

public class SceneState {
    private int currentAct;
    private int currentSceneIndex;
    private final Map<String, Integer> branchChoices;
    private final Set<String> interactedObjects;
    
    public SceneState() {
        this.currentAct = 1;
        this.currentSceneIndex = 0;
        this.branchChoices = new HashMap<>();
        this.interactedObjects = new HashSet<>();
    }
    
    public void recordChoice(String branchId, int choiceIndex) {
        if (branchId == null || branchId.trim().isEmpty()) {
            throw new IllegalArgumentException("Branch ID cannot be null or empty");
        }
        branchChoices.put(branchId, choiceIndex);
    }
    
    public Optional<Integer> getChoice(String branchId) {
        return Optional.ofNullable(branchChoices.get(branchId));
    }
    
    public void markInteracted(String objectId) {
        if (objectId != null && !objectId.trim().isEmpty()) {
            interactedObjects.add(objectId);
        }
    }
    
    public boolean hasInteracted(String objectId) {
        return interactedObjects.contains(objectId);
    }
    
    public int getCurrentAct() {
        return currentAct;
    }
    
    public void setCurrentAct(int currentAct) {
        this.currentAct = Math.max(1, Math.min(4, currentAct));
    }
    
    public int getCurrentSceneIndex() {
        return currentSceneIndex;
    }
    
    public void setCurrentSceneIndex(int currentSceneIndex) {
        this.currentSceneIndex = Math.max(0, currentSceneIndex);
    }
    
    public Map<String, Integer> getBranchChoices() {
        return Collections.unmodifiableMap(branchChoices);
    }
    
    public Set<String> getInteractedObjects() {
        return Collections.unmodifiableSet(interactedObjects);
    }
}