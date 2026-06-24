package com.game.rpg.model.entities;

import com.game.rpg.util.ValidationUtils;

import java.util.*;

public class Scene {
    private final String id;
    private final String name;
    private String backgroundPath;
    private final List<InteractableObject> interactableObjects;
    private final List<NPC> npcs;
    private final Map<String, double[]> triggerZones;
    private boolean hasIllusionEffect;
    
    public Scene(String id, String name, String backgroundPath) {
        ValidationUtils.requireNonNull(id, "Scene ID cannot be null");
        ValidationUtils.requireNonNull(name, "Scene name cannot be null");
        
        this.id = id;
        this.name = name;
        this.backgroundPath = backgroundPath;
        this.interactableObjects = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.triggerZones = new HashMap<>();
        this.hasIllusionEffect = false;
    }
    
    public void addInteractableObject(InteractableObject object) {
        ValidationUtils.requireNonNull(object, "Interactable object cannot be null");
        interactableObjects.add(object);
    }
    
    public void addNPC(NPC npc) {
        ValidationUtils.requireNonNull(npc, "NPC cannot be null");
        npcs.add(npc);
    }
    
    public void addTriggerZone(String zoneId, double[] bounds) {
        ValidationUtils.requireNonNull(zoneId, "Zone ID cannot be null");
        ValidationUtils.requireNonNull(bounds, "Bounds cannot be null");
        if (bounds.length != 4) {
            throw new IllegalArgumentException("Bounds must have 4 elements [x, y, width, height]");
        }
        triggerZones.put(zoneId, bounds);
    }
    
    public Optional<InteractableObject> getInteractableObject(double x, double y) {
        return interactableObjects.stream()
                .filter(obj -> obj.containsPoint(x, y))
                .findFirst();
    }
    
    public Optional<NPC> getNPC(double x, double y) {
        return npcs.stream()
                .filter(npc -> {
                    double distance = Math.sqrt(Math.pow(npc.getX() - x, 2) + Math.pow(npc.getY() - y, 2));
                    return distance < 50;
                })
                .findFirst();
    }
    
    public Optional<String> checkTriggerZone(double x, double y) {
        for (Map.Entry<String, double[]> entry : triggerZones.entrySet()) {
            double[] bounds = entry.getValue();
            if (x >= bounds[0] && x <= bounds[0] + bounds[2] &&
                y >= bounds[1] && y <= bounds[1] + bounds[3]) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }
    
    public List<InteractableObject> getInteractableObjects() {
        return Collections.unmodifiableList(interactableObjects);
    }
    
    public List<NPC> getNpcs() {
        return Collections.unmodifiableList(npcs);
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getBackgroundPath() {
        return backgroundPath;
    }
    
    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }
    
    public boolean hasIllusionEffect() {
        return hasIllusionEffect;
    }
    
    public void setIllusionEffect(boolean hasIllusionEffect) {
        this.hasIllusionEffect = hasIllusionEffect;
    }
    
    @Override
    public String toString() {
        return String.format("Scene{id='%s', name='%s', objects=%d, npcs=%d}", 
                id, name, interactableObjects.size(), npcs.size());
    }
}