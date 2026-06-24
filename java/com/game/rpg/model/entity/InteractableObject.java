package com.game.rpg.model.entity;

import com.game.rpg.model.enums.InteractionType;
import com.game.rpg.util.validation.ValidationUtil;

import java.util.function.Consumer;

public class InteractableObject {
    private final String id;
    private final String name;
    private final double x;
    private final double y;
    private final double width;
    private final double height;
    private final InteractionType interactionType;
    private Consumer<Void> interactionHandler;
    private boolean interacted;
    
    public InteractableObject(String id, String name, double x, double y, 
                              double width, double height, InteractionType interactionType) {
        ValidationUtil.requireNonNull(id, "Object ID cannot be null");
        ValidationUtil.requireNonNull(name, "Object name cannot be null");
        ValidationUtil.requireNonNull(interactionType, "Interaction type cannot be null");
        
        this.id = id;
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.interactionType = interactionType;
        this.interacted = false;
    }
    
    public void interact() {
        if (interactionHandler != null) {
            interactionHandler.accept(null);
            interacted = true;
        }
    }
    
    public void setInteractionHandler(Consumer<Void> handler) {
        this.interactionHandler = handler;
    }
    
    public boolean isInteracted() {
        return interacted;
    }
    
    public boolean containsPoint(double pointX, double pointY) {
        return pointX >= x && pointX <= x + width && 
               pointY >= y && pointY <= y + height;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public InteractionType getInteractionType() {
        return interactionType;
    }
    
    @Override
    public String toString() {
        return String.format("InteractableObject{id='%s', name='%s', type=%s}", 
                id, name, interactionType);
    }
}