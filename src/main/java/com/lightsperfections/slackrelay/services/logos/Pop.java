package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Pop implements SlackRelayService {
    private final String name;

    public Pop(String name) {
        this.name = name;
    }

    /**
     * Display usage
     *
     * @param userText
     * @return
     * @throws DependentServiceException
     * @throws InternalImplementationException
     */
    @Override
    public String performAction(String userText) throws DependentServiceException, InternalImplementationException {
        return "POP!";

    }

    public String getName() {
        return name;
    }

}
