package pl.polsl.model;

import java.time.LocalDate;
/**
 * This class represents task in a system
 * @author Roch Fedorowicz
 * @version 1.1
 * @since 1.0
*/
public class Task {
    /**
     * Unique task ID
    */
    private String taskID;
    /**
     * Priority of task
    */
    private Priority priority;
    /**
     * Date due to which task should be finished
    */
    private LocalDate date;
    /**
     * Description of task
    */
    private String description;
    /**
     * Signum of person that is resposible for this task
    */
    private String assigneeSignum;

    public Task(String taskID, Priority priority, LocalDate date, String description,
            String assigneeSignum) {
        this.taskID = taskID;
        this.priority = priority;
        this.date = date;
        this.description = description;
        this.assigneeSignum = assigneeSignum;
    }
    
        public String getTaskID() {
        return taskID;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getAssigneeSignum() {
        return assigneeSignum;
    }

    @Override
    public String toString() {
        return "Task{" + "taskID=" + taskID + ", assigneeSignum=" +
            assigneeSignum + " priority=" + priority + ", date=" +
            date + ", description=" + description + '}';
    }
}
