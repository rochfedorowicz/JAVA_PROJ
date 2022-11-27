package com.polsl.controler;

import com.polsl.controler.interfaces.FirstLevelMenuInterface;
import com.polsl.model.Model;
import com.polsl.view.View;
/**
 * This class is resposible for displaying and handling simple menu implements FirstLevelMenuInterface
 * @author Roch Fedorowicz
 * @version 1.2
 * @since 1.0
*/
public class FirstLevelMenu implements FirstLevelMenuInterface {
    
    /**
     * Helper class resposnible for tasks handling
     */
    private static TaskLevelMenu taskLevelMenu;
    
    /**
     * Helper class resposnible for people handling
     */
    private static PeopleLevelMenu peopleLevelMenu;
    
    /**
     * Model class
     */
    private static Model model;

    public FirstLevelMenu(Model model) {
        taskLevelMenu = new TaskLevelMenu(model);
        peopleLevelMenu = new PeopleLevelMenu(model);
        FirstLevelMenu.model = model;
    }
        
    /**
     * Displaying and handling menu for user
     */
    @Override
    public void display() {
        int answer;
        do {
            View.displayContent("\n----MENU----\nThese options are possible:" +
                "\nTeam Manager - 1\n" + "Task Manager - 2\n" + "Quit - 3\n");
            answer = Integer.parseInt(View.scanSingleInput());
            switch (answer) {
                case 1 -> peopleLevelMenu.display();
                case 2 -> taskLevelMenu.display();
                case 3 -> View.displayContent("Quiting program");
                default -> View.displayContent("Incorect option");
            }
        } while (answer != 3);
    }
}
