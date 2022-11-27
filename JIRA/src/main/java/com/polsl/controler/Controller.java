package com.polsl.controler;

import com.polsl.model.Model;

/**
 * This class is resposible for controling all actions in a system
 * @author Roch Fedorowicz
 * @version 1.0
 * @since 1.0
*/
public class Controller {
    
    /**
     * Helper class
     */
    private static FirstLevelMenu firstLevelMenu;
    
    /**
     * Model class
     */
    private static Model model;
    
    /**
    * Instantiate helper class
    */
    public static void initializeMenu() {
        model = new Model();
        firstLevelMenu = new FirstLevelMenu(model);
    }
    
    /**
    * Displaying first level menu
    */
    public static void displayMenu() {
        firstLevelMenu.display();
    }
}
