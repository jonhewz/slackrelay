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

import java.time.LocalDateTime;
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
     * Display user stats. Example:
     *
     * Daily Average
     *     reading days: 6.0 chapters
     *     all days: 2.0 chapters
     * Best Day: 12 Feb 16 (6 chapters)
     * Longest Streak: 1 day (12 Feb 16 - 12 Feb 16)
     * Completed books: HOS|3 GE|2 PHP|1
     *
     * Times of Day
     * ------------
     *  N|
     *  5|********
     *  8|***
     * 11|
     *  1|
     *  4|*****
     *  7|
     * 10|
     *
     *
     * Past Year
     * ----------
     * F|********
     * J|*****************
     * D|**************
     * N|************
     * O|**********
     * S|*******
     * A|******
     * J|*****
     * J|**********
     * M|**************
     * A|***
     * M|*****
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
                    "Daily Average\n" +
                    "    reading days: " + Reporting.calculateAverageForReadingDays(historyEntries) + "\n" +
                    "    all days: " + Reporting.calculateAverageForAllDays(historyEntries) + "\n" +
                    "Best Day: " + Reporting.calculateBestDay(historyEntries) + "\n" +
                    "Longest Streak: " + Reporting.calculateLongestStreak(historyEntries) + "\n" +
                    "Completed Books: " + Reporting.calculateBooksRead(historyEntries);

        } catch (ReportingException e) {
            return e.getMessage();
        }



        return stats;
    }

}

