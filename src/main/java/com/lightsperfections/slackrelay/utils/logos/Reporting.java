package com.lightsperfections.slackrelay.utils.logos;

import com.lightsperfections.slackrelay.beans.logos.HistoryEntry;
import com.lightsperfections.slackrelay.beans.logos.Streak;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 2/11/16.
 */
public class Reporting {

    public static Streak calculateLongestStreak(List<? extends HistoryEntry> historyEntries) throws ReportingException {

        if (historyEntries == null || historyEntries.size() < 1) {
            throw new ReportingException("No history to report on.");
        }

        // List has to be sorted to determine streaks.
        historyEntries.sort((o1, o2)->o1.getEntryTime().compareTo(o2.getEntryTime()));

        // Create a list of streaks, to be sorted later
        List<Streak> streaks = new ArrayList<>();

        LocalDateTime streakBegin = historyEntries.get(0).getEntryTime().truncatedTo(ChronoUnit.DAYS);
        LocalDateTime lastDateTime = streakBegin;
        for (HistoryEntry entry : historyEntries) {
            LocalDateTime currentDateTime = entry.getEntryTime().truncatedTo(ChronoUnit.DAYS);
            if (ChronoUnit.DAYS.between(lastDateTime, currentDateTime) > 1) {
                streaks.add(new Streak(streakBegin, lastDateTime));
                lastDateTime = currentDateTime;
                streakBegin = currentDateTime;
            } else {
                lastDateTime = currentDateTime;
            }
        }
        streaks.add(new Streak(streakBegin, lastDateTime));

        // Sort, smallest streak first
        streaks.sort((o1, o2)->
                new Long(ChronoUnit.DAYS.between(o1.getBegin(), o1.getEnd())).compareTo(
                        new Long(ChronoUnit.DAYS.between(o2.getBegin(), o2.getEnd()))));

        // Return the largest streak from the end of the list
        return streaks.get(streaks.size() - 1);

    }
/*
    public static void calculateBestDay(List<HistoryEntry> historyEntries) {

    }

    public static void calculateAverageForReadingDays(List<HistoryEntry> historyEntries) {

    }

    public static void calculateAverageForAllDays(List<HistoryEntry> historyEntries) {

    }

*/
}
