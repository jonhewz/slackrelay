package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.services.Unimplemented;
import com.lightsperfections.slackrelay.services.esv.PassageQuery;

import java.util.HashMap;
import java.util.Map;

import com.lightsperfections.slackrelay.services.logos.Pop;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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
    @Value("#{ systemProperties['esv.key'] ?: 'IP' }")
    private String esvKey;

    @Bean(name="esvKey")
    public String getEsvKey() {
        return esvKey;
    }

    @Value("http://www.esvapi.org/v2/rest/")
    private String esvBaseUrl;

    @Value("passageQuery")
    private String esvPassageQueryPath;

    @Value("")
    private String esvPassageQueryParams;

    @Bean(name="esvPassageQueryParams")
    public Map<String,String> getEsvPassageQueryParams() {
        Map<String, String> rv = new HashMap<String, String>();
        rv.put("output-format", "plain-text");
        rv.put("include-passage-references", "true");
        rv.put("include-first-verse-numbers", "false");
        rv.put("include-verse-numbers", "false");
        rv.put("include-footnotes", "false");
        rv.put("include-short-copyright", "false");
        rv.put("include-copyright", "false");
        rv.put("include-passage-horizontal-lines", "false");
        rv.put("include-heading-horizontal-lines", "false");
        rv.put("include-headings", "false");
        rv.put("include-subheadings", "false");
        rv.put("include-selahs", "true");
        rv.put("include-content-type", "true");
        rv.put("line-length", "0");
        return (rv);
    }

    @Bean(name="unimplemented")
    public SlackRelayService getUnimplementedService() {
        return new Unimplemented();
    }

    // ESV Services
    @Bean(name="esv.passagequery")
    public SlackRelayService getPassageQueryService() {
        return new PassageQuery("ESV Passage Query", esvBaseUrl, esvPassageQueryPath, getEsvPassageQueryParams());
    }

    @Bean(name="esv.help")
    public SlackRelayService getESVHelpService() {
        return new com.lightsperfections.slackrelay.services.esv.Help("ESV Help");
    }

    // Logos Services
    @Bean(name="logos.help")
    public SlackRelayService getLOGOSHelpService() {
        return new com.lightsperfections.slackrelay.services.logos.Help("LOGOS Help");
    }

    @Bean(name="logos.pop")
    public SlackRelayService getHelpService() {
        return new Pop("LOGOS Pop");
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        crlf.setMaxPayloadLength(500);
        return crlf;
    }
}
