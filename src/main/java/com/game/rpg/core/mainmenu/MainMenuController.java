package com.game.rpg.core.mainmenu;

import com.game.rpg.core.achievement.AchievementManager;
import com.game.rpg.core.save.SaveManager;
import com.game.rpg.core.story.StoryManager;
import com.game.rpg.state.GameState;
import com.game.rpg.state.GameStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Consumer;

public class MainMenuController {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuController.class);
    
    private final MenuNavigator navigator;
    private final SaveManager saveManager;
    private final AchievementManager achievementManager;
    private final StoryManager storyManager;
    private final GameStateManager stateManager;
    
    private Consumer<Void> onNewGame;
    private Consumer<List<SaveManager.SaveSlotInfo>> onShowSaveList;
    private Consumer<List<com.game.rpg.model.entities.Achievement>> onShowAchievements;
    private Runnable onExitGame;
    
    public MainMenuController(SaveManager saveManager, AchievementManager achievementManager,
                             StoryManager storyManager, GameStateManager stateManager) {
        this.navigator = new MenuNavigator();
        this.saveManager = saveManager;
        this.achievementManager = achievementManager;
        this.storyManager = storyManager;
        this.stateManager = stateManager;
    }
    
    public void showMainMenu() {
        navigator.clearHistory();
        logger.info("Main menu displayed");
    }
    
    public void startNewGame() {
        GameState newGameState = new GameState();
        stateManager.restoreFromSnapshot(newGameState);
        
        storyManager.startAct(1);
        
        if (onNewGame != null) {
            onNewGame.accept(null);
        }
        
        logger.info("New game started");
    }
    
    public void loadGame(int slot) {
        saveManager.loadGame(slot).ifPresentOrElse(
                gameState -> {
                    stateManager.restoreFromSnapshot(gameState);
                    storyManager.startAct(gameState.getCurrentAct());
                    logger.info("Game loaded from slot {}", slot);
                },
                () -> logger.warn("Failed to load game from slot {}", slot)
        );
    }
    
    public void showSaveList() {
        List<SaveManager.SaveSlotInfo> slots = saveManager.getSaveSlots();
        
        if (onShowSaveList != null) {
            onShowSaveList.accept(slots);
        }
        
        logger.info("Save list displayed");
    }
    
    public void showAchievements() {
        List<com.game.rpg.model.entities.Achievement> achievements = 
                achievementManager.getAllAchievements();
        
        if (onShowAchievements != null) {
            onShowAchievements.accept(achievements);
        }
        
        logger.info("Achievements displayed");
    }
    
    public void saveGame(int slot) {
        GameState currentState = stateManager.getCurrentState();
        saveManager.saveGame(slot, currentState);
        logger.info("Game saved to slot {}", slot);
    }
    
    public void exitGame() {
        if (onExitGame != null) {
            onExitGame.run();
        }
        
        logger.info("Game exiting");
    }
    
    public void navigateToMenu(String menuId) {
        navigator.navigateTo(menuId);
    }
    
    public void goBack() {
        navigator.goBack();
    }
    
    public boolean canGoBack() {
        return navigator.canGoBack();
    }
    
    public String getCurrentMenu() {
        return navigator.getCurrentMenu();
    }
    
    public void setOnNewGame(Consumer<Void> onNewGame) {
        this.onNewGame = onNewGame;
    }
    
    public void setOnShowSaveList(Consumer<List<SaveManager.SaveSlotInfo>> onShowSaveList) {
        this.onShowSaveList = onShowSaveList;
    }
    
    public void setOnShowAchievements(Consumer<List<com.game.rpg.model.entities.Achievement>> onShowAchievements) {
        this.onShowAchievements = onShowAchievements;
    }
    
    public void setOnExitGame(Runnable onExitGame) {
        this.onExitGame = onExitGame;
    }
}