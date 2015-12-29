package com.lightsperfections.slackrelay.services;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:28 PM
 */
public class Unimplemented implements SlackRelayService {
    @Override
    public String getName() { return "Unimplemented"; }
    @Override
    public String performAction(String userName, String userText) {
        return getName();
    }
}
