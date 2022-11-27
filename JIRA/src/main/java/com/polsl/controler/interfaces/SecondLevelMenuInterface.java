package com.polsl.controler.interfaces;
/**
 * This interface is resposible displaying, handling simple menu and handling basic database instance operations
 * @author Roch Fedorowicz
 * @version 1.2
 * @since 1.1
*/
public interface SecondLevelMenuInterface extends FirstLevelMenuInterface {
    
    /**
    * Adding new instance to database
    */
    public void addInstance();
    
    /**
    * Displaying all instances in database
    */
    public void displayInstances();
    
    /**
    * Removing some instance from database
    */
    public void removeInstance();
    
    /**
    * Displaying some statistics in database
    */
    public void displayGroupedBy();
    
}
