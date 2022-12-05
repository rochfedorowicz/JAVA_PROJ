package pl.polsl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * This class is responsible for handling data
 * @author Roch Fedorowicz
 * @version 1.2
 * @since 1.0
*/
public class Model {
    private List<Person> team = new ArrayList<>();
    private List<Task> tasks = new ArrayList<>();
    
    /**
    * Adding new person to a database
    * @param person New person to be added
     * @throws pl.polsl.model.KeyViolationException
     * @throws pl.polsl.model.NameViolationException
    */
    public void addNewPersonToTeam(Person person) throws KeyViolationException,
        NameViolationException {
        if (!person.getSurname().matches("^[A-Z]{1}([a-z]{1,}[ ]?){1,}$") ||
            !person.getName().matches("^[A-Z]{1}([a-z]{1,}[ ]?){1,}$")) {
            throw new NameViolationException("\nThis name isn't correct");
        }
        for (Person p : team) { 
            if (p.getSignum().equals(person.getSignum())) {
                throw new KeyViolationException("\nEmployee identified by " +
                    "signum: " + person.getSignum() + " already exist!!!");
            }
        }
        team.add(person);
    }
    
    /**
    * Adding new task to a database
    * @param task New task to be added
     * @throws pl.polsl.model.KeyViolationException
    */
    public void addNewTaskToBoard(Task task) throws KeyViolationException {
        for (Task t : tasks) { 
            if (t.getTaskID().equals(t.getTaskID())) {
                throw new KeyViolationException("\nTask identified by " +
                    "ID: " + task.getTaskID() + " already exist!!!");
            }
        }
        tasks.add(task);
    }
    
    /**
    * Removing task from a database using ID
    * @param ID ID of task that should be removed
    * @throws pl.polsl.model.InstanceNotFoundException
    */
    public void removeTaskById(String ID) throws InstanceNotFoundException{
        for (Task task : tasks) { 
            if (task.getTaskID().equals(ID)) {
                tasks.remove(task);
                return;
            }
        }
        throw new InstanceNotFoundException("\nTask identyfied by ID " +
            ID + " not found!!!");
    }
    
    /**
    * Getting task from a database using ID
    * @param ID ID of task that should be returned
    * @return Task found by ID
    * @throws pl.polsl.model.InstanceNotFoundException
    */
    public Task getTaskById(String ID) throws InstanceNotFoundException {
        for (Task task : tasks) { 
            if (task.getTaskID().equals(ID)) {
                return task;
            }
        }
        throw new InstanceNotFoundException("\nTask identyfied by ID " +
            ID + " not found!!!");
    }
    
    /**
    * Removing person from a database using signum
    * @param signum Signum of person that should be removed
    * @throws pl.polsl.model.InstanceNotFoundException
    */
    public void removePersonBySignum(String signum) throws InstanceNotFoundException {
        for (Person person : team) { 
            if (person.getSignum().equals(signum)) {
                team.remove(person);
                return;
            }
        }
        throw new InstanceNotFoundException("\nPerson identyfied by signum " +
            signum + " not found!!!");
    }
    
    /**
    * Getting person from a database using signum
    * @param signum Signum of person that should be returned
    * @return Person found by signum, in case if such a signum does not exist null is returned
    * @throws pl.polsl.model.InstanceNotFoundException
    */
    public Person getPersonBySignum(String signum) throws InstanceNotFoundException {
        for (Person person : team) { 
            if (person.getSignum().equals(signum)) {
                return person;
            }
        }
        throw new InstanceNotFoundException("\nPerson identyfied by signum " +
            signum + " not found!!!");
    }
    
    /**
    * Getting all people in a system
    * @return List of people
    */
    public List<Person> getTeam() {
        return team;
    }
    
    /**
    * Getting all tasks in a system
    * @return List of task
    */
    public List<Task> getTasks() {
        return tasks;
    }
       
    /**
    * Setting all people in a system
    * @param team New list of people
    */
    public void setTeam(List<Person> team) {
        this.team = team;
    }

    /**
    * Setting all tasks in a system
    * @param tasks New list of tasks
    */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    
    /**
    * Clearing all tasks
    */
    public void resetTasks() {
        tasks.clear();
    }
    
    /**
    * Clearing all people
    */
    public void resetTeam() {
        team.clear();
    }
    
    /*
    * Getting grouping people by the position 
    */
    public Map<Position, Integer> getPositionGrouping() {
        HashMap<Position, Integer> groupedMap = new HashMap<>();
        team.stream()
        .collect(Collectors.groupingBy(person -> person.getPosition()))
        .forEach(
            (position, listOfPeople) -> {
                groupedMap.put(position, listOfPeople.size());
        });
        return groupedMap;
    }
    
    /*
    * Getting grouping task by the priority
    */
    public Map<Priority, Integer> getPriorityGrouping() {
        HashMap<Priority, Integer> groupedMap = new HashMap<>();
        tasks.stream()
        .collect(Collectors.groupingBy(task -> task.getPriority()))
        .forEach(
            (position, listOfTasks) -> {
                groupedMap.put(position, listOfTasks.size());
        });
        return groupedMap;
    }
}
