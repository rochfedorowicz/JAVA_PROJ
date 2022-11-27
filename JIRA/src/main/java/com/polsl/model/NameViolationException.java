package com.polsl.model;
/**
 * Simple exception thrown when attempt to add name with numbers in name
 * @author Roch Fedorowicz
 */
public class NameViolationException extends Exception {
    
    public NameViolationException(String errorMessage) {
        super(errorMessage);
    }
}
