package com.lightsperfections.slackrelay.services.esv;

import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Help implements SlackRelayService {
    private final String name;

    public Help(String name) {
        this.name = name;
    }

    /**
     * Display usage
     *
     * @param userName
     * @param userText
     * @return
     * @throws DependentServiceException
     * @throws InternalImplementationException
     */
    @Override
    public String performAction(String userName, String userText)
            throws DependentServiceException, InternalImplementationException {
        return
                "ESV Help\n" +
                "--------\n" +
                "Perform a passage lookup with either\n" +
                "/esv passagequery prov 31:1-5\n" +
                "or just /esv prov 31:1-5\n";

    }

    public String getName() {
        return name;
    }

}
