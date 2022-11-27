package com.polsl.controler;

import com.polsl.controler.interfaces.SecondLevelMenuInterface;
import com.polsl.model.InstanceNotFoundException;
import com.polsl.model.KeyViolationException;
import com.polsl.model.Model;
import com.polsl.otherClasses.Person;
import com.polsl.otherClasses.Priority;
import com.polsl.otherClasses.Task;
import com.polsl.view.View;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * This class is resposible for handling simple menu and database operations related to tasks
 * @author Roch Fedorowicz
 * @version 1.2
 * @since 1.1
*/
public class TaskLevelMenu implements SecondLevelMenuInterface{
    
    /**
     * Model class
     */
    private static Model model;

    public TaskLevelMenu(Model model) {
        TaskLevelMenu.model = model;
    }
    
    /**
     * Displaying and handling task manager menu
     */
    @Override
    public void display() {
        int answer;
        do {
            View.displayContent("\n----TASK_MENU----\nThese options are possible:" +
                "\nAdd new task - 1\n" + "Display tasks - 2\n" + "Remove task - 3\n" + 
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
     * Hanndling adding new task to database by user
     */
    @Override
    public void addInstance() {
        
        if (model.getTeam().isEmpty()) {
            View.displayContent("You need to have at least one worker in" +
                " your team to add tasks!!!");
            return;
        }
        String taskID, description, assigneeSignum;
        Priority priority = null;
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        View.displayContent("Adding new task, please input taskID: ");
        taskID = View.scanSingleInput();
        View.displayContent("Please input priority: ");
        do {
            try {
                priority = Priority.valueOf(View.scanSingleInput());
            } catch (IllegalArgumentException exceptionCaught) {
                View.displayContent("Please input valid priority!!!" +
                    "\nPossible are: HIGH, MEDIUM, LOW");
            }
        } while (priority == null);
        View.displayContent("Please input date: ");
        do {
            try {
                date = LocalDate.parse(View.scanSingleInput(), formatter);
            } catch (DateTimeParseException exceptionCaught) {
                View.displayContent("Please input valid date format " + 
                    "!!! (dd/mm/yyyy)");
            }
        } while (date == null);
        View.displayContent("Please input asignee signum: ");
        do {
            assigneeSignum = View.scanSingleInput();
            try {
                model.getPersonBySignum(assigneeSignum);
            } catch (InstanceNotFoundException exceptionCaught) {
                assigneeSignum = "";
                View.displayContent(exceptionCaught.getMessage());
                View.displayContent("\nPossible signums are:");
                for (Person person : model.getTeam()) {
                    View.displayContent("- " + person.getSignum());
                }
                 View.displayContent("\n");
            }
        } while (assigneeSignum.isEmpty());
        View.displayContent("Please input description: ");
        description = View.scanWholeLineInput();

        try {
            model.addNewTaskToBoard(new Task(taskID, priority, date, description, assigneeSignum));
        } catch (KeyViolationException exceptionCaught) {
            View.displayContent(exceptionCaught.getMessage());
        }
    }

    /**
     * Displaying all tasks in database to user
     */
    @Override
    public void displayInstances() {
        for (Task task : model.getTasks()) {
            View.displayContent(task.toString());
        }
    }
    
    /**
     * Hanndling removal of certain task specified by ID in database by user
     */
    @Override
    public void removeInstance() {
        String taskID;
        View.displayContent("Input taskID of task you want to remove: ");
        taskID = View.scanSingleInput();
        try {
            model.removeTaskById(taskID);
        } catch (InstanceNotFoundException exceptionCaught) {
            View.displayContent(exceptionCaught.getMessage());
        }
    }
    
    /**
    * Displaying statistics by the position
    */
    @Override
    public void displayGroupedBy() {
        model.getPriorityGrouping()
        .forEach((priority, number) -> {
            System.out
            .format("Priority %s: %s\n", priority, number);
        });
    }
}
