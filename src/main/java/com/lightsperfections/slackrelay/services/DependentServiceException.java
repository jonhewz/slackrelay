package com.lightsperfections.slackrelay.services;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/22/15
 * Time: 3:02 PM
 */
public class DependentServiceException extends Exception {
    public DependentServiceException(String message) {
        super(message);
    }
}
