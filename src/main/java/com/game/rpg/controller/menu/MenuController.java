package com.game.rpg.controller.menu;

import com.game.rpg.controller.save.SaveController;
import com.game.rpg.controller.achievement.AchievementController;
import com.game.rpg.controller.story.StoryController;
import com.game.rpg.model.state.GameStateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;
import java.util.function.Consumer;

public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    
    private final Stack<String> menuStack;
    private final GameStateManager stateManager;
    private String currentMenu;
    
    private Consumer<Void> onNewGame;
    private Runnable onExitGame;
    
    public MenuController(GameStateManager stateManager) {
        this.menuStack = new Stack<>();
        this.stateManager = stateManager;
        this.currentMenu = "main";
    }
    
    public void showMainMenu() {
        menuStack.clear();
        currentMenu = "main";
        logger.info("Main menu displayed");
    }
    
    public void startNewGame() {
        if (onNewGame != null) {
            onNewGame.accept(null);
        }
        
        logger.info("New game started");
    }
    
    public void exitGame() {
        if (onExitGame != null) {
            onExitGame.run();
        }
        
        logger.info("Game exiting");
    }
    
    public void navigateToMenu(String menuId) {
        if (menuId == null || menuId.trim().isEmpty()) {
            logger.warn("Invalid menu ID");
            return;
        }
        
        if (currentMenu != null) {
            menuStack.push(currentMenu);
        }
        
        currentMenu = menuId;
        logger.info("Navigated to menu: {} (stack size: {})", menuId, menuStack.size());
    }
    
    public String goBack() {
        if (menuStack.isEmpty()) {
            logger.warn("Cannot go back: menu stack is empty");
            return currentMenu;
        }
        
        currentMenu = menuStack.pop();
        logger.info("Went back to menu: {} (stack size: {})", currentMenu, menuStack.size());
        return currentMenu;
    }
    
    public boolean canGoBack() {
        return !menuStack.isEmpty();
    }
    
    public String getCurrentMenu() {
        return currentMenu;
    }
    
    public void clearHistory() {
        menuStack.clear();
        logger.debug("Menu history cleared");
    }
    
    public int getHistorySize() {
        return menuStack.size();
    }
    
    public void setOnNewGame(Consumer<Void> onNewGame) {
        this.onNewGame = onNewGame;
    }
    
    public void setOnExitGame(Runnable onExitGame) {
        this.onExitGame = onExitGame;
    }
}