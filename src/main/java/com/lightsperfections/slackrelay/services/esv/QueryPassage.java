package com.lightsperfections.slackrelay.services.esv;

import com.lightsperfections.slackrelay.services.SlackRelayService;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class QueryPassage implements SlackRelayService {
    @Override
    public String performAction(String userText) {
        return "QueryPassage";
    }
}
