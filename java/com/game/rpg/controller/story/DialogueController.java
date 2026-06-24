package com.game.rpg.controller.story;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class DialogueController {
    private static final Logger logger = LoggerFactory.getLogger(DialogueController.class);
    
    private final List<String> dialogueLines;
    private int currentLineIndex;
    private String speakerName;
    private String portraitPath;
    private boolean dialogueActive;
    
    private Consumer<String> onLineDisplayed;
    private Runnable onDialogueEnd;
    
    public DialogueController() {
        this.dialogueLines = new ArrayList<>();
        this.currentLineIndex = 0;
        this.dialogueActive = false;
    }
    
    public void startDialogue(String speakerName, String portraitPath, List<String> lines) {
        this.speakerName = speakerName;
        this.portraitPath = portraitPath;
        this.dialogueLines.clear();
        this.dialogueLines.addAll(lines);
        this.currentLineIndex = 0;
        this.dialogueActive = true;
        
        logger.info("Dialogue started: {} lines", lines.size());
        
        showNextLine();
    }
    
    public void showNextLine() {
        if (!dialogueActive || currentLineIndex >= dialogueLines.size()) {
            endDialogue();
            return;
        }
        
        String currentLine = dialogueLines.get(currentLineIndex);
        
        if (onLineDisplayed != null) {
            onLineDisplayed.accept(currentLine);
        }
        
        currentLineIndex++;
        logger.debug("Dialogue line {}/{}: {}", currentLineIndex, dialogueLines.size(), currentLine);
    }
    
    public boolean hasNextLine() {
        return dialogueActive && currentLineIndex < dialogueLines.size();
    }
    
    public void endDialogue() {
        dialogueActive = false;
        currentLineIndex = 0;
        
        if (onDialogueEnd != null) {
            onDialogueEnd.run();
        }
        
        logger.info("Dialogue ended");
    }
    
    public void showOptions(List<String> options) {
        logger.debug("Showing {} options", options.size());
    }
    
    public int handleOptionSelection(int selectedIndex) {
        logger.info("Option selected: {}", selectedIndex);
        return selectedIndex;
    }
    
    public void setOnLineDisplayed(Consumer<String> onLineDisplayed) {
        this.onLineDisplayed = onLineDisplayed;
    }
    
    public void setOnDialogueEnd(Runnable onDialogueEnd) {
        this.onDialogueEnd = onDialogueEnd;
    }
    
    public String getSpeakerName() {
        return speakerName;
    }
    
    public String getPortraitPath() {
        return portraitPath;
    }
    
    public String getCurrentLine() {
        if (!dialogueActive || currentLineIndex <= 0 || currentLineIndex > dialogueLines.size()) {
            return null;
        }
        return dialogueLines.get(currentLineIndex - 1);
    }
    
    public boolean isDialogueActive() {
        return dialogueActive;
    }
}