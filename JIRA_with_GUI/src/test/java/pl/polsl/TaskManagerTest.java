package pl.polsl;

import pl.polsl.model.InstanceNotFoundException;
import pl.polsl.model.KeyViolationException;
import pl.polsl.model.Model;
import pl.polsl.otherClasses.Priority;
import pl.polsl.otherClasses.Task;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
/**
 * @author Roch Fedorowicz
 * Class implementing tests for task manging related functionalities
 */
public class TaskManagerTest {
        private final Model model = new Model();
    
    /**
    * Proper case of execution for task adding
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldAddTaskResources.csv", numLinesToSkip = 1)
    public void testShouldAddTask(String taskID, String priority,
        String date, String description, String signum) {
        Task addedTask = new Task(taskID, Priority.valueOf(priority),
            LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(addedTask);
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing adding");
        }
        assertEquals(1, model.getTasks().size());
        model.resetTasks();
    }
     
    /**
    * Unproper case of execution for task adding, exception should be thrown
    * when attempt to add two task with the same ID
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldNotAllowToAddTaskWithTheSameIDResources.csv", numLinesToSkip = 1)
    public void testShouldNotAllowToAddTaskWithTheSameID(String taskID, 
        String priority, String date, String description, String signum) {
        Task firstAddedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        Task secondAddedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(firstAddedTask);
            model.addNewTaskToBoard(secondAddedTask);
            fail("Exception not rised!!!");
        } catch (KeyViolationException exceptionCaught) {
            //Proper way of execution
        }
        assertEquals(1, model.getTasks().size());
        model.resetTasks();
    }
    
    /**
    * Proper case of execution for task getting
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    * @param testingTaskID ID of task to be obtained
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldGetTaskResources.csv", numLinesToSkip = 1)
    public void testShouldGetTask(String taskID, String priority, String date,
        String description, String signum, String testingTaskID) {
        Task obtainedTask = null;
        Task addedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(addedTask);
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing getting");
        }
        try {
            obtainedTask = model.getTaskById(testingTaskID);
        } catch (InstanceNotFoundException exceptionCaught) {
            fail(exceptionCaught.getMessage());
        }
        assertEquals(obtainedTask,addedTask);
        model.resetTasks();
    }
    
    /**
    * Unproper case of execution for task getting, exception should be thrown
    * when tried to get task by ID that does not exist
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    * @param testingTaskID ID of task to be obtained
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldThrowExceptionWhenTriedToGetNonExistingTaskResources.csv", numLinesToSkip = 1)
    public void testShouldThrowExceptionWhenTriedToGetNonExistingTask(
        String taskID, String priority, String date, String description,
        String signum, String testingTaskID) {
        Task obtainedTask = null;
        Task addedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(addedTask);
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing getting");
        }
        try {
            obtainedTask = model.getTaskById(testingTaskID);
            fail("Exception not rised!!!");
        } catch (InstanceNotFoundException exceptionCaught) {
            //Proper way of execution
        }
        assertNotEquals(obtainedTask, addedTask);
        model.resetTasks();
    }
    
    /**
    * Proper case of execution for task removing
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    * @param testingTaskID ID of task to be removed
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldRemoveTaskResources.csv", numLinesToSkip = 1)
    public void testShouldRemovePerson(String taskID, String priority,
        String date, String description, String signum, String testingTaskID) {
        Task addedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(addedTask);
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing removing");
        }
        try {
            model.removeTaskById(testingTaskID);
        } catch (InstanceNotFoundException ex) {
           fail(ex.getMessage());
        }
        assertEquals(0, model.getTasks().size());
        model.resetTasks();
    }
    
    /**
    * Unproper case of execution for task removing, exception should be thrown 
    * when tried to remove task by ID that does not exist
    * @param taskID ID of task to be added
    * @param priority Priority of task to be added
    * @param date Date of task to be added
    * @param description Description of task to be added
    * @param signum Signum of person assigned to task to be added
    * @param testingTaskID ID of task to be removed
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldThrowExceptionWhenTriedToRemoveNonExistingTaskResources.csv", numLinesToSkip = 1)
    public void testShouldThrowExceptionWhenTriedToRemoveNonExistingTask(
        String taskID, String priority, String date, String description,
        String signum, String testingTaskID) {
        Task addedTask = new Task(taskID, Priority.valueOf(priority),
        LocalDate.parse(date), description, signum);
        try {
            model.addNewTaskToBoard(addedTask);
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing removing");
        }
        try {
            model.removeTaskById(testingTaskID);
            fail("Exception not rised!!!");
        } catch (InstanceNotFoundException exceptionCaught) {
            //Proper way of execution
        }
        assertEquals(1, model.getTasks().size());
        model.resetTasks();
    }

}
