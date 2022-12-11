package pl.polsl.otherClasses;

import pl.polsl.otherClasses.Position;

/**
 * This class represents person in a system
 * @author Roch Fedorowicz
 * @version 1.1
 * @since 1.0
*/
public class Person {
    /**
     * Person's signum - unique identifier
    */
    private String signum;
    /**
     * Person's name
    */
    private String name;
    /**
     * Person's surname
    */
    private String surname;
    /**
     * Person's developer
    */
    private Position position;

    public Person(String signum, String name, String surname, Position position) {
        this.signum = signum;
        this.name = name;
        this.surname = surname;
        this.position = position;
    }

    @Override
    public String toString() {
        return "Person{signum=" + signum + ", name=" + name + ", position=" + position + "}";
    }

    public String getSignum() {
        return signum;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
    
    public Position getPosition() { 
        return position; 
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public final void setPosition(Position position) { 
        this.position = position; 
    }

}
