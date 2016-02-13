package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.beans.logos.HistoryEntry;
import com.lightsperfections.slackrelay.dao.HistoryEntryDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.utils.logos.Reporting;
import com.lightsperfections.slackrelay.utils.logos.ReportingException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;
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
     * NOTES
     * HashMap<Date, List<Reference>> - loop through and track largest value size
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

        String stats;

        // This context for Database stored user history
        AnnotationConfigApplicationContext mainContext =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);

        HistoryEntryDao historyEntryDao = mainContext.getBean(HistoryEntryDao.class);

        Collection<HistoryEntry> historyEntries = historyEntryDao.findHistoryEntriesByUserName(userName);
        if (historyEntries.size() < 1) {
            return ("Not enough history to report on.");
        }

        try {
            stats =
                    "Best Day: " + Reporting.calculateBestDay(historyEntries) + "\n" +
                    "Longest Streak: " + Reporting.calculateLongestStreak(historyEntries) + "\n";

                    /*
                "Average volume (reading days): " + calculateAverageForReadingDays(historyEntries) + "\n" +
                "Average volume (all days): " + calculateAverageForAllDays(historyEntries);*/

        } catch (ReportingException e) {
            return e.getMessage();
        }

        return stats;
    }

}

