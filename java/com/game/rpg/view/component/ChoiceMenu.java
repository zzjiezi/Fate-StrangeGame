package com.game.rpg.view.component;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ChoiceMenu extends VBox {
    private final List<Button> choiceButtons;
    private Consumer<Integer> onChoiceSelected;
    
    public ChoiceMenu() {
        setSpacing(10);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 20;");
        choiceButtons = new ArrayList<>();
    }
    
    public void showChoices(List<String> choices) {
        getChildren().clear();
        choiceButtons.clear();
        
        for (int i = 0; i < choices.size(); i++) {
            Button button = createChoiceButton(i, choices.get(i));
            choiceButtons.add(button);
            getChildren().add(button);
        }
    }
    
    private Button createChoiceButton(int index, String text) {
        Button button = new Button(text);
        button.setFont(Font.font(16));
        button.setPrefWidth(400);
        button.setStyle("-fx-background-color: #2a2a2a; -fx-text-fill: white; " +
                "-fx-border-color: #4a4a4a; -fx-border-width: 2; -fx-padding: 10;");
        
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #3a3a3a; -fx-text-fill: cyan; " +
                "-fx-border-color: cyan; -fx-border-width: 2; -fx-padding: 10;"));
        
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #2a2a2a; -fx-text-fill: white; " +
                "-fx-border-color: #4a4a4a; -fx-border-width: 2; -fx-padding: 10;"));
        
        button.setOnAction(e -> {
            if (onChoiceSelected != null) {
                onChoiceSelected.accept(index);
            }
        });
        
        return button;
    }
    
    public void setOnChoiceSelected(Consumer<Integer> onChoiceSelected) {
        this.onChoiceSelected = onChoiceSelected;
    }
    
    public void hide() {
        getChildren().clear();
    }
    

}