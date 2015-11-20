package com.lightsperfections.slackrelay;

import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.services.Unimplemented;
import com.lightsperfections.slackrelay.services.esv.QueryPassage;
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

    @Bean(name="queryPassage")
    @Primary
    public SlackRelayService getQueryPassageService() {
        return new QueryPassage();
    }

    @Bean(name="unimplemented")
    public SlackRelayService getUnimplementedService() {
        return new Unimplemented();
    }
}
