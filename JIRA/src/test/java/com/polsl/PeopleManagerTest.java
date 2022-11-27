package com.polsl;

import com.polsl.model.InstanceNotFoundException;
import com.polsl.model.KeyViolationException;
import com.polsl.model.Model;
import com.polsl.model.NameViolationException;
import com.polsl.otherClasses.Person;
import com.polsl.otherClasses.Position;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * @author Roch Fedorowicz
 * Class implementing tests for task manging related functionalities
 */
public class PeopleManagerTest {
    
    private final Model model = new Model();
    
    /**
    * Proper case of execution for person adding
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldAddPersonResources.csv", numLinesToSkip = 1)
    public void testShouldAddPerson(String signum, String name, String surname,
        String position) {
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(addedPerson);
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing adding");
        }
        assertEquals(1, model.getTeam().size());
        model.resetTeam();
    }
    
    /**
    * Unproper case of execution for person adding, exception should be thrown
    * when attempt to add person with inadequate name (e.g. contains numbers
    * and/or special characters and/or starts with lowercase character)
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldNotAllowToAddPersonWithInadequateNameResources.csv", numLinesToSkip = 1)
    public void testShouldNotAllowToAddPersonWithInadequateName(String signum,
        String name, String surname, String position) {
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(addedPerson);
            fail("Exception not rised!!!");
        } catch (KeyViolationException exceptionCaught) {
            fail("Unexpected exception while testing adding");
        } catch (NameViolationException exceptionCaught) {
            //Proper way of execution
        }
        assertEquals(0, model.getTeam().size());
        model.resetTeam();
    }
    
    /**
    * Unproper case of execution for person adding, exception should be thrown
    * when attempt to add two people with the same signum
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldNotAllowToAddPersonWithTheSameSignumResources.csv", numLinesToSkip = 1)
    public void testShouldNotAllowToAddPersonWithTheSameSignum(String signum,
        String name, String surname, String position) {
        Person firstAddedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        Person secondAddedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(firstAddedPerson);
            model.addNewPersonToTeam(secondAddedPerson);
            fail("Exception not rised!!!");
        } catch (KeyViolationException exceptionCaught) {
            //Proper way of execution
        } catch (NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing adding");
        }
        assertEquals(1, model.getTeam().size());
        model.resetTeam();
    }
    
    /**
    * Proper case of execution for person getting
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    * @param testingSignum Signum of person to be obtained
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldGetPersonResources.csv", numLinesToSkip = 1)
    public void testShouldGetPerson(String signum, String name, String surname,
        String position, String testingSignum) {
        Person obtainedPerson = null;
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(addedPerson);
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing getting");
        }
        try {
            obtainedPerson = model.getPersonBySignum(testingSignum);
        } catch (InstanceNotFoundException exceptionCaught) {
            fail(exceptionCaught.getMessage());
        }
        assertEquals(addedPerson, obtainedPerson);
        model.resetTeam();
    }
    
    /**
    * Unproper case of execution for person getting, exception should be thrown
    * when tried to get person by signum that does not exist
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    * @param testingSignum Signum of person to be obtained
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldThrowExceptionWhenTriedToGetNonExistingPersonResources.csv", numLinesToSkip = 1)
    public void testShouldThrowExceptionWhenTriedToGetNonExistingPerson(String signum,
        String name, String surname, String position, String testingSignum) {
        Person obtainedPerson = null;
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(addedPerson);
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing getting");
        }
        try {
            obtainedPerson = model.getPersonBySignum(testingSignum);
            fail("Exception not rised!!!");
        } catch (InstanceNotFoundException exceptionCaught) {
            //Proper way of execution
        }
        assertNotEquals(obtainedPerson, addedPerson);
        model.resetTeam();
    }
    
    /**
    * Proper case of execution for person removing
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    * @param testingSignum Signum of person to be removed
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldRemovePersonResources.csv", numLinesToSkip = 1)
    public void testShouldRemovePerson(String signum, String name,
        String surname, String position, String testingSignum) {
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position));
        try {
            model.addNewPersonToTeam(addedPerson);
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing removing");
        }
        try {
            model.removePersonBySignum(testingSignum);
        } catch (InstanceNotFoundException ex) {
           fail(ex.getMessage());
        }
        assertEquals(0, model.getTeam().size());
        model.resetTeam();
    }
    
    /**
    * Unproper case of execution for person removing, exception should be thrown 
    * when tried to remove person by signum that does not exist
    * @param signum Signum of person to be added
    * @param name Name of person to be added
    * @param surname Surname of person to be added
    * @param position Position of person to be added
    * @param testingSignum Signum of person to be removed
    */
    @ParameterizedTest
    @CsvFileSource(resources = "/shouldThrowExceptionWhenTriedToRemoveNonExistingPersonResources.csv", numLinesToSkip = 1)
    public void testShouldThrowExceptionWhenTriedToRemoveNonExistingPerson(String signum,
        String name, String surname, String position, String testingSignum) {
        Person addedPerson = new Person(signum, name, surname, 
        Position.valueOf(position)); 
        try {
            model.addNewPersonToTeam(addedPerson);
        } catch (KeyViolationException | NameViolationException exceptionCaught) {
            fail("Unexpected exception while testing removing");
        }
        try {
            model.removePersonBySignum(testingSignum);
            fail("Exception not rised!!!");
        } catch (InstanceNotFoundException exceptionCaught) {
            //Proper way of execution
        }
        assertEquals(1, model.getTeam().size());
        model.resetTeam();
    }

}    