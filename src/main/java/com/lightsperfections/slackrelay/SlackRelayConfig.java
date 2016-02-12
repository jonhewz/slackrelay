package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.dao.HistoryEntryDao;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.dao.dynamodb.DynamoDBHistoryEntryDao;
import com.lightsperfections.slackrelay.dao.dynamodb.DynamoDBReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.services.Unimplemented;
import com.lightsperfections.slackrelay.services.esv.PassageQuery;
import com.lightsperfections.slackrelay.services.logos.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/13/15
 * Time: 12:12 PM
 */
@Configuration
public class SlackRelayConfig {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
        crlf.setIncludeClientInfo(true);
        crlf.setIncludeQueryString(true);
        crlf.setIncludePayload(true);
        crlf.setMaxPayloadLength(500);
        return crlf;
    }

    // Not any slack team can connect up. Specify the single team allowed as
    // a jvm arg. i.e. --slack.team.id=abc123
    @Value("#{ systemProperties['slack.team.id'] ?: 'TEST' }")
    private String authorizedSlackTeamId;

    @Bean(name="authorizedSlackTeamId")
    public String getAuthorizedSlackTeamId() {
        return authorizedSlackTeamId;
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
    public SlackRelayService getESVPassageQueryService() {
        return new PassageQuery("ESV Passage Query", esvBaseUrl, esvPassageQueryPath, getEsvPassageQueryParams());
    }

    @Bean(name="esv.help")
    public SlackRelayService getESVHelpService() {
        return new com.lightsperfections.slackrelay.services.esv.Help("ESV Help");
    }

    // Logos Services
    @Bean(name="logos.help")
    public SlackRelayService getLogosHelpService() {
        return new com.lightsperfections.slackrelay.services.logos.Help("LOGOS Help");
    }

    @Bean(name="logos.pop")
    public SlackRelayService getLogosPopService() {
        return new Pop("LOGOS Pop");
    }

    @Bean(name="logos.set")
    public SlackRelayService getLogosSetService() {
        return new Set("LOGOS Set");
    }

    @Bean(name="logos.status")
    public SlackRelayService getLogosStatusService() {
        return new Status("LOGOS Status");
    }

    @Bean(name="logos.plans")
    public SlackRelayService getLogosPlansService() {
        return new Plans("LOGOS Plans");
    }

    @Bean(name="logos.stats")
    public SlackRelayService getLogosStatsService() {
        return new Stats("LOGOS Stats");
    }

    // DAOs
    @Bean
    public ReadingPlanBookmarkDao getReadingPlanBookmarkDao() {
        return new DynamoDBReadingPlanBookmarkDao();
    }

    @Bean
    public HistoryEntryDao getHistoryDao() {
        return new DynamoDBHistoryEntryDao();
    }

}
