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
public class Help implements SlackRelayService {
    private final String name;

    public Help(String name) {
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
        return
                "LOGOS Help\n" +
                "----------\n" +
                "/logos pop - get the next passage\n" +
                "/logos push - mark the most recent passage as unread\n" +
                "/logos ff [n] - fast-forward n passages (but still track them)\n" +
                "/logos status - progress info\n" +
                "/logos plan - plan info\n";

    }

    public String getName() {
        return name;
    }

}
