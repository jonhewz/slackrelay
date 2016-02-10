package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.ReadingPlanConfig;
import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlanBookmark;
import com.lightsperfections.slackrelay.beans.logos.Track;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.utils.logos.ProgressReport;
import com.lightsperfections.slackrelay.utils.logos.ReadingPlanNavigation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Stats implements SlackRelayService {

    private final String name;

    public Stats(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Display user stats
     *
     * Longest streak: 21 days (9/1/15 - 10/21/15)
     * Biggest day: 15 chapters on 9/1/15
     * Average num chapters (reading days): 8.4 chapters
     * Average num chapters (all days): 3.8 chapters
     * Completed books: HOS|3 GE|2 PHP|1
     * Times of Day:
     *
     *       *
     *       *
     *       *       *
     *       *  *    *
     * -----------------------
     * N  5  8 11  1  4  7  10
     *
     * Year trend:
     *
     *         *   *
     *     *   *   *
     * *   *   *   *
     * --------------
     * Q1  Q2  Q3  Q4
     *
     *
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

        String stats = "";

        // This context for Database stored user history
        AnnotationConfigApplicationContext mainContext =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);

        return stats;
    }

}

