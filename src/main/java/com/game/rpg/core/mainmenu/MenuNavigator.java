package com.game.rpg.core.mainmenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

public class MenuNavigator {
    private static final Logger logger = LoggerFactory.getLogger(MenuNavigator.class);
    
    private final Stack<String> menuStack;
    private String currentMenu;
    
    public MenuNavigator() {
        this.menuStack = new Stack<>();
        this.currentMenu = "main";
    }
    
    public void navigateTo(String menuId) {
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
}