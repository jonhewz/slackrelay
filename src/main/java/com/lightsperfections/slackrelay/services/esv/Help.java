package com.lightsperfections.slackrelay.services.esv;

import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Map;

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
