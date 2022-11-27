package com.polsl.model;
/**
 * Simple exception thrown when attempt to add second instance identified 
 * by the same ID
 * @author Roch Fedorowicz
 */
public class KeyViolationException extends Exception {
    
    public KeyViolationException(String errorMessage) {
        super(errorMessage);
    }
}