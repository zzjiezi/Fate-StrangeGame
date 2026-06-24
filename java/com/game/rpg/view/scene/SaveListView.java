package com.game.rpg.view.scene;

import com.game.rpg.controller.save.SaveController;
import com.game.rpg.controller.save.SaveController.SaveSlotInfo;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class SaveListView extends VBox {
    private static final Logger logger = LoggerFactory.getLogger(SaveListView.class);
    
    private final SaveController saveController;
    private Consumer<Integer> onSlotSelected;
    private Runnable onBack;
    
    public SaveListView(SaveController saveController) {
        this.saveController = saveController;
        
        initializeUI();
    }
    
    private void initializeUI() {
        setSpacing(20);
        setPadding(new Insets(50));
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(0, 0, 0, 0.9);");
        
        Label titleLabel = new Label("读取存档");
        titleLabel.setFont(Font.font(36));
        titleLabel.setTextFill(Color.CYAN);
        
        VBox slotsBox = createSlotsBox();
        
        Button backButton = createBackButton();
        
        getChildren().addAll(titleLabel, slotsBox, backButton);
        
        logger.info("Save list view initialized");
    }
    
    private VBox createSlotsBox() {
        VBox slotsBox = new VBox(15);
        slotsBox.setAlignment(Pos.CENTER);
        
        List<SaveSlotInfo> slots = saveController.getSaveSlots();
        
        for (SaveSlotInfo slot : slots) {
            HBox slotBox = createSlotBox(slot);
            slotsBox.getChildren().add(slotBox);
        }
        
        return slotsBox;
    }
    
    private HBox createSlotBox(SaveSlotInfo slot) {
        HBox slotBox = new HBox(20);
        slotBox.setAlignment(Pos.CENTER_LEFT);
        slotBox.setPadding(new Insets(15));
        slotBox.setPrefWidth(600);
        
        if (slot.hasData()) {
            slotBox.setStyle(
                "-fx-background-color: rgba(0, 100, 100, 0.5); " +
                "-fx-border-color: cyan; " +
                "-fx-border-width: 2; " +
                "-fx-cursor: hand;"
            );
            
            Label slotLabel = new Label("存档 " + slot.getSlot());
            slotLabel.setFont(Font.font(20));
            slotLabel.setTextFill(Color.WHITE);
            
            Label infoLabel = new Label(String.format("第%d幕 | %s", 
                slot.getCurrentAct(), slot.getSaveTime()));
            infoLabel.setFont(Font.font(16));
            infoLabel.setTextFill(Color.LIGHTGRAY);
            
            slotBox.getChildren().addAll(slotLabel, infoLabel);
            
            slotBox.setOnMouseClicked(e -> {
                if (onSlotSelected != null) {
                    onSlotSelected.accept(slot.getSlot());
                }
            });
        } else {
            slotBox.setStyle(
                "-fx-background-color: rgba(50, 50, 50, 0.5); " +
                "-fx-border-color: gray; " +
                "-fx-border-width: 1;"
            );
            
            Label emptyLabel = new Label("存档 " + slot.getSlot() + " - 空");
            emptyLabel.setFont(Font.font(20));
            emptyLabel.setTextFill(Color.GRAY);
            
            slotBox.getChildren().add(emptyLabel);
        }
        
        return slotBox;
    }
    
    private Button createBackButton() {
        Button button = new Button("返回");
        button.setPrefWidth(150);
        button.setFont(Font.font(18));
        button.setStyle(
            "-fx-background-color: rgba(100, 100, 100, 0.7); " +
            "-fx-text-fill: white; " +
            "-fx-border-color: white; " +
            "-fx-border-width: 2; " +
            "-fx-cursor: hand;"
        );
        
        button.setOnAction(e -> {
            if (onBack != null) {
                onBack.run();
            }
        });
        
        return button;
    }
    
    public void setOnSlotSelected(Consumer<Integer> onSlotSelected) {
        this.onSlotSelected = onSlotSelected;
    }
    
    public void setOnBack(Runnable onBack) {
        this.onBack = onBack;
    }
}