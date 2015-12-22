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

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class PassageQuery implements SlackRelayService {
    private final String name;
    private final String baseUrl;
    private final String path;

    private final OkHttpClient client = new OkHttpClient();

    AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SlackRelayConfig.class);

    public PassageQuery(String name, String baseUrl, String path) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.path = path;
    }

    /**
     * Perform a GET request to the ESV API passageQuery resource, where the passage search query is the
     * userText passed in.
     *
     * @param userText
     * @return
     * @throws DependentServiceException
     * @throws InternalImplementationException
     */
    @Override
    public String performAction(String userText) throws DependentServiceException, InternalImplementationException {

        String body;
        try {

            Request request = new Request.Builder()
                .url(getBaseUrl() + getPath() + "?key=" + context.getBean("esvKey") + "&passage=" + userText)
                .build();

            Response response = client.newCall(request).execute();
            body = response.body().string();



        } catch (IOException e) {
            throw new DependentServiceException("Error issuing request to ESV API");

        } catch (NoSuchBeanDefinitionException e) {
            throw new InternalImplementationException("Error with ESV service configuration");
        }

        return body;
    }

    public String getName() {
        return name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getPath() {
        return path;
    }
}
