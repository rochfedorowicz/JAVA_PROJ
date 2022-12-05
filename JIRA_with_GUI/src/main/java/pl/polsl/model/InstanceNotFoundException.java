package pl.polsl.model;
/**
 * Simple exception thrown when no instance identified by an certain 
 * identifier exist
 * @author Roch Fedorowicz
 */
public class InstanceNotFoundException extends Exception{
    
    public InstanceNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}