package com.polsl.jira;

import com.polsl.controler.Controller;
/**
 * This class is resposible for the start of whole system
 * @author Roch Fedorowicz
 * @version 1.0
 * @since 1.0
*/
public class JIRA {

    /**
     * Main function calling controller to start the system
    */
    public static void main(String[] args) {
        Controller.initializeMenu();
        Controller.displayMenu();
    }
}
