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
    public String performAction(String userName, String userText)
            throws DependentServiceException, InternalImplementationException {
        return
                "LOGOS Help\n" +
                "----------\n" +
                "/logos plans - list the available plans\n" +
                "/logos set [plan_name|index] - set the plan or the index\n" +
                "/logos pop - get the next passage\n" +
                "/logos status - info about your current plan and progress\n" +
                "/logos stats - data analysis\n";

    }

    public String getName() {
        return name;
    }

}
