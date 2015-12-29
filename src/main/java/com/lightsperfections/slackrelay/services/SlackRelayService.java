package com.lightsperfections.slackrelay.services;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:10 PM
 */
public interface SlackRelayService {
    public String getName();
    public String performAction(String userName, String userText)
            throws DependentServiceException, InternalImplementationException;
}
