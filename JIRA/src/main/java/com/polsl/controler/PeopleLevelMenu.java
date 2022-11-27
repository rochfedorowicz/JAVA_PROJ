package com.polsl.controler;

import com.polsl.controler.interfaces.SecondLevelMenuInterface;
import com.polsl.model.InstanceNotFoundException;
import com.polsl.model.KeyViolationException;
import com.polsl.model.Model;
import com.polsl.model.NameViolationException;
import com.polsl.otherClasses.Person;
import com.polsl.otherClasses.Position;
import com.polsl.view.View;
/**
 * This class is resposible for handling simple menu and database operations related to people
 * @author Roch Fedorowicz
 * @version 1.2
 * @since 1.1
*/
public class PeopleLevelMenu implements SecondLevelMenuInterface{
    
    /**
     * Model class
     */
    private static Model model;

    public PeopleLevelMenu(Model model) {
        PeopleLevelMenu.model = model;
    }
    
    /**
     * Displaying and handling team manager menu
     */
    @Override
    public void display() {
        int answer;
        do {
            View.displayContent("\n----TEAM_MENU----\nThese options are possible:" +
                "\nAdd new worker - 1\n" + "Display team - 2\n" + "Remove worker - 3\n" + 
                "Display position statistics - 4\n" + "Quit - 5\n");
            answer = Integer.parseInt(View.scanSingleInput());
            switch (answer) {
                case 1 -> addInstance();
                case 2 -> displayInstances();
                case 3 -> removeInstance();
                case 4 -> displayGroupedBy();
                case 5 -> View.displayContent("Quiting program");
                default -> View.displayContent("Incorect option");
            }
        } while (answer != 5);
    }
    
    /**
     * Hanndling adding new person to database by user
     */
    @Override
    public void addInstance() {
        
        String signum, name, surname;
        Position position = null;
        
        View.displayContent("Adding new person, please input name: ");
        name = View.scanSingleInput();
        View.displayContent("Please input surname: ");
        surname = View.scanSingleInput();
        View.displayContent("Please input signum: ");
        signum = View.scanSingleInput();
        View.displayContent("Please input position: ");
        do {
            try {
                position = Position.valueOf(View.scanSingleInput());
            } catch (IllegalArgumentException exceptionCaught) {
                View.displayContent("Please input valid position!!!" +
                    "\nPossible are: DEVELOPER, MANAGER, PRODUCT_OWNER");
            }
        } while (position == null);
        
        try {
            model.addNewPersonToTeam(new Person(signum, name, surname, position));
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            View.displayContent(exceptionCaught.getMessage());
        }
    }
    
    /**
     * Displaying all people in database to user
     */
    @Override
    public void displayInstances() {
        for (Person person : model.getTeam()) {
            View.displayContent(person.toString());
        }
    }

    /**
     * Hanndling removal of certain person specified by signum in database by user
     */
    @Override
    public void removeInstance() {
        String signum;
        View.displayContent("Input signum of person you want to remove: ");
        signum = View.scanSingleInput();
        try {
            model.removePersonBySignum(signum);
        } catch (InstanceNotFoundException exceptionCaught) {
            View.displayContent(exceptionCaught.getMessage());
        }
    }
    
    /**
    * Displaying statistics by the position
    */
    @Override
    public void displayGroupedBy() {
        model.getPositionGrouping()
        .forEach((position, number) -> {
            System.out
            .format("Position %s: %s\n", position, number);
        });
    }
    
}
