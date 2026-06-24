package com.game.rpg.view.animation;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class AnimationPlayer {
    
    public void playFadeIn(Node node, double durationSeconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(durationSeconds), node);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }
    
    public void playFadeOut(Node node, double durationSeconds) {
        FadeTransition fade = new FadeTransition(Duration.seconds(durationSeconds), node);
        fade.setFromValue(1.0);
        fade.setToValue(0.0);
        fade.play();
    }
    
    public void playBlackToWhite(Pane parent, Runnable onComplete) {
        Rectangle overlay = new Rectangle();
        overlay.setWidth(parent.getWidth());
        overlay.setHeight(parent.getHeight());
        overlay.setFill(Color.BLACK);
        
        parent.getChildren().add(overlay);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1.0), overlay);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        
        overlay.setFill(Color.WHITE);
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1.0), overlay);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        
        SequentialTransition sequence = new SequentialTransition(fadeOut, pause, fadeIn);
        
        sequence.setOnFinished(e -> {
            parent.getChildren().remove(overlay);
            if (onComplete != null) {
                onComplete.run();
            }
        });
        
        sequence.play();
    }
    
    public void playSceneTransition(Pane parent, Runnable transitionAction, Runnable onComplete) {
        Rectangle overlay = new Rectangle();
        overlay.setWidth(parent.getWidth());
        overlay.setHeight(parent.getHeight());
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.0);
        
        parent.getChildren().add(overlay);
        
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), overlay);
        fadeOut.setFromValue(0.0);
        fadeOut.setToValue(1.0);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(e -> {
            if (transitionAction != null) {
                transitionAction.run();
            }
        });
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), overlay);
        fadeIn.setFromValue(1.0);
        fadeIn.setToValue(0.0);
        
        SequentialTransition sequence = new SequentialTransition(fadeOut, pause, fadeIn);
        
        sequence.setOnFinished(e -> {
            parent.getChildren().remove(overlay);
            if (onComplete != null) {
                onComplete.run();
            }
        });
        
        sequence.play();
    }
}