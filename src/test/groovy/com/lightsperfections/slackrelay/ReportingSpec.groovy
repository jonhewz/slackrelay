package com.lightsperfections.slackrelay

import com.lightsperfections.slackrelay.beans.logos.HistoryEntry
import com.lightsperfections.slackrelay.beans.logos.dynamodb.DynamoDBHistoryEntry
import com.lightsperfections.slackrelay.utils.logos.Reporting
import com.lightsperfections.slackrelay.utils.logos.ReportingException
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/18/15
 * Time: 2:31 PM
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = Application.class)
public class ReportingSpec extends Specification {

    @Test
    def "Calculate longest streak empty history"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:

        when:
        def msg = "no prob";
        try {
            Reporting.calculateLongestStreak(historyEntryList).toString();
        } catch (ReportingException e) {
            msg = e.getMessage();
        }

        then:
        msg.contentEquals("No history to report on.")
    }

    @Test
    def "Calculate longest streak single history entry"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 3, 0, 0), "-"));

        when:
        def longestStreak = Reporting.calculateLongestStreak(historyEntryList).toString();

        then:
        longestStreak.contentEquals("1 day (3 Jan 16 - 3 Jan 16)")
    }

    @Test
    def "Calculate longest streak spanning months"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 3, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 4, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 5, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 30, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 31, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 1, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 2, 0, 0), "-"));

        when:
        def longestStreak = Reporting.calculateLongestStreak(historyEntryList).toString();

        then:
        longestStreak.contentEquals("4 days (30 Jan 16 - 2 Feb 16)")
    }

    @Test
    def "Calculate longest streak spanning leap day"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 3, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 4, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 5, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 27, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 28, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 3, 1, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 3, 2, 5, 30), "-"));

        when:
        def longestStreak = Reporting.calculateLongestStreak(historyEntryList).toString();

        then:
        longestStreak.contentEquals("3 days (3 Jan 16 - 5 Jan 16)")
    }

    @Test
    def "Calculate longest streak random date order"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 6, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 1, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 2, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 3, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 14, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 9, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 4, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 7, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 4, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 26, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 13, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 8, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 5, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 31, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 11, 5, 30), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 2, 3, 5, 30), "-"));

        when:
        def longestStreak = Reporting.calculateLongestStreak(historyEntryList).toString();

        then:
        longestStreak.contentEquals("6 days (31 Jan 16 - 5 Feb 16)")
    }

    @Test
    def "Calculate best day with dupe HistoryEntry"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 12, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 23, 59), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 1, 0, 0), "-"));

        when:
        def bestDay = Reporting.calculateBestDay(historyEntryList).toString();

        then:
        bestDay.contentEquals("31 Dec 15 (4 chapters)")
    }

    @Test
    def "Calculate best day with no HistoryEntries"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:

        when:
        def msg = "no prob";
        try {
            Reporting.calculateBestDay(historyEntryList).toString();
        } catch (ReportingException e) {
            msg = e.getMessage();
        }

        then:
        msg.contentEquals("No history to report on.")
    }

    @Test
    def "Calculate best day with single HistoryEntry"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 0, 0), "-"));

        when:
        def bestDay = Reporting.calculateBestDay(historyEntryList).toString();

        then:
        bestDay.contentEquals("31 Dec 15 (1 chapter)")
    }

    @Test
    def "Calculate best day with competing champions"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 1, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2016, 1, 1, 0, 0), "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.of(2015, 12, 31, 0, 0), "-"));

        when:
        def bestDay = Reporting.calculateBestDay(historyEntryList).toString();

        then:
        bestDay.contentEquals("1 Jan 16 (2 chapters)")
    }

    @Test
    def "Calculate average(all) with no HistoryEntries"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:

        when:
        def msg = "no prob";
        try {
            Reporting.calculateAverageForAllDays(historyEntryList).toString();
        } catch (ReportingException e) {
            msg = e.getMessage();
        }

        then:
        msg.contentEquals("No history to report on.")
    }

    @Test
    def "Calculate average(all)"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(2) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(2) , "-"));

        when:
        def av = Reporting.calculateAverageForAllDays(historyEntryList).toString();

        then:
        av.contentEquals("2.5 chapters")
    }

    @Test
    def "Calculate average(reading) with no HistoryEntries"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:

        when:
        def msg = "no prob";
        try {
            Reporting.calculateAverageForReadingDays(historyEntryList).toString();
        } catch (ReportingException e) {
            msg = e.getMessage();
        }

        then:
        msg.contentEquals("No history to report on.")
    }

    @Test
    def "Calculate average(reading)"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(4) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(2) , "-"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(2) , "-"));

        when:
        def av = Reporting.calculateAverageForReadingDays(historyEntryList).toString();

        then:
        av.contentEquals("5.0 chapters")
    }

    @Test
    def "Calculate average(all), with rounding"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now().minusDays(3) , "-"));

        when:
        def av = Reporting.calculateAverageForAllDays(historyEntryList).toString();

        then:
        av.contentEquals("0.3 chapters")
    }


    @Test
    def "Calculate books read"() {
        List<HistoryEntry> historyEntryList = new ArrayList<>();

        setup:
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Obadiah 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Obadiah 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Haggai 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Haggai 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 2"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 3"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 2"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 3"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 1"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 2"));
        historyEntryList.add(new DynamoDBHistoryEntry("a", LocalDateTime.now(), "Joel 4"));

        when:
        def br = Reporting.calculateBooksRead(historyEntryList).toString();

        then:
        br.contentEquals("JOE|2 OB|2")
    }
}
