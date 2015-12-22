package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.services.Unimplemented;
import com.lightsperfections.slackrelay.services.esv.PassageQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/13/15
 * Time: 12:12 PM
 */
@Configuration
public class SlackRelayConfig {

    // Not any slack account can connect up. Specify the single token allowed as
    // a jvm arg. i.e. --slack.token=abc123
    @Value("#{ systemProperties['slack.token'] ?: 'TEST' }")
    private String authorizedSlackToken;

    @Bean(name="authorizedSlackToken")
    public String getAuthorizedSlackToken() {
        return authorizedSlackToken;
    }

    // ESV API will need a developer key to be passed along with requests. Specify
    // as a jvm arg. i.e. --esv.key=abc123
    @Value("#{ systemProperties['esv.key'] ?: 'TEST' }")
    private String esvKey;

    @Bean(name="esvKey")
    public String getEsvKey() {
        return esvKey;
    }

    private String esvBaseUrl = "http://www.esvapi.org/v2/rest/";
    private String esvPassageQueryPath = "passageQuery";

    @Bean(name="passageQuery")
    @Primary
    public SlackRelayService getPassageQueryService() {
        return new PassageQuery("ESV Passage Query", esvBaseUrl, esvPassageQueryPath);
    }

    @Bean(name="unimplemented")
    public SlackRelayService getUnimplementedService() {
        return new Unimplemented();
    }

}
