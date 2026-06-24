package com.game.rpg.view.component;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DialogBox extends VBox {
    private final ImageView portraitView;
    private final Label speakerLabel;
    private final Label textLabel;
    private final StringBuilder currentText;
    private AnimationTimer typewriterTimer;
    private int charIndex;
    private String fullText;
    private boolean isTyping;
    private Runnable onComplete;
    
    public DialogBox(double width, double height) {
        setSpacing(10);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-padding: 15; " +
                "-fx-border-color: white; -fx-border-width: 2;");
        setPrefSize(width, height);
        
        HBox topBox = new HBox(10);
        
        portraitView = new ImageView();
        portraitView.setFitWidth(100);
        portraitView.setFitHeight(100);
        portraitView.setPreserveRatio(true);
        
        VBox textContainer = new VBox(5);
        HBox.setHgrow(textContainer, Priority.ALWAYS);
        
        speakerLabel = new Label();
        speakerLabel.setTextFill(Color.CYAN);
        speakerLabel.setFont(Font.font(18));
        
        textLabel = new Label();
        textLabel.setTextFill(Color.WHITE);
        textLabel.setFont(Font.font(16));
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(Double.MAX_VALUE);
        
        textContainer.getChildren().addAll(speakerLabel, textLabel);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        topBox.getChildren().addAll(portraitView, textContainer);
        
        getChildren().add(topBox);
        
        currentText = new StringBuilder();
        isTyping = false;
    }
    
    public void showDialogue(String speaker, String text, Image portrait) {
        if (isTyping) {
            completeTyping();
            return;
        }
        
        speakerLabel.setText(speaker);
        portraitView.setImage(portrait);
        fullText = text;
        currentText.setLength(0);
        charIndex = 0;
        isTyping = true;
        
        startTypewriterEffect();
    }
    
    private void startTypewriterEffect() {
        typewriterTimer = new AnimationTimer() {
            private long lastUpdate = 0;
            
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 30_000_000) {
                    if (charIndex < fullText.length()) {
                        currentText.append(fullText.charAt(charIndex));
                        textLabel.setText(currentText.toString());
                        charIndex++;
                        lastUpdate = now;
                    } else {
                        stop();
                        isTyping = false;
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    }
                }
            }
        };
        
        typewriterTimer.start();
    }
    
    public void completeTyping() {
        if (typewriterTimer != null) {
            typewriterTimer.stop();
        }
        textLabel.setText(fullText);
        isTyping = false;
        if (onComplete != null) {
            onComplete.run();
        }
    }
    
    public void setOnComplete(Runnable onComplete) {
        this.onComplete = onComplete;
    }
    
    public boolean isTyping() {
        return isTyping;
    }
}