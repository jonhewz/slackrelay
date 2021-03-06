package com.lightsperfections.slackrelay.utils.logos;

import com.lightsperfections.slackrelay.beans.Book;
import com.lightsperfections.slackrelay.beans.logos.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by jon on 2/11/16.
 */
public class Reporting {

    /**
     * Given a Collection of unordered HistoryEntries, determine the largest number of consecutive days
     * that have HistoryEntries
     * @param collectionHistoryEntries
     * @return
     * @throws ReportingException
     */
    public static Streak calculateLongestStreak(Collection<HistoryEntry> collectionHistoryEntries)
            throws ReportingException {

        if (collectionHistoryEntries == null || collectionHistoryEntries.size() < 1) {
            throw new ReportingException("No history to report on.");
        }

        // List has to be sorted to determine streaks.
        List<HistoryEntry> historyEntries = new ArrayList<>(collectionHistoryEntries);
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
                        ChronoUnit.DAYS.between(o2.getBegin(), o2.getEnd())));

        // Return the largest streak from the end of the list
        return streaks.get(streaks.size() - 1);

    }

    /**
     * Given a Collection of unordered HistoryEntries, determine the day with the most HistoryEntries. Tiebreakers
     * are decided by the most recent entry.
     * @param historyEntries
     * @return
     * @throws ReportingException
     */
    public static BestDay calculateBestDay(Collection<HistoryEntry> historyEntries) throws ReportingException {
        if (historyEntries == null || historyEntries.size() < 1) {
            throw new ReportingException("No history to report on.");
        }

        Map<LocalDateTime, Integer> entriesPerDay = new HashMap<>();
        for (HistoryEntry entry : historyEntries) {
            LocalDateTime day = entry.getEntryTime().truncatedTo(ChronoUnit.DAYS);
            Integer currentCount = entriesPerDay.get(day);
            entriesPerDay.put(day, currentCount != null ? currentCount + 1 : 1);
        }

        Map.Entry<LocalDateTime, Integer> champion = null;
        for (Map.Entry<LocalDateTime, Integer> contender : entriesPerDay.entrySet()) {
            if (champion == null || contender.getValue() > champion.getValue() ||
                    // If the contender is not greater than the champion, at least check if they're
                    // equal in count. If so, replace, since this way it guarantees the most recent
                    // tiebreaker.
                    (contender.getValue().equals(champion.getValue()) &&
                    contender.getKey().compareTo(champion.getKey()) > 0)) {
                champion = contender;
            }
        }
        return new BestDay(champion.getKey(), champion.getValue());
    }


    /**
     * Given a Collection of unordered HistoryEntries, determine the average number of entries per day from
     * the earliest entry's date til now.
     * @param historyEntries
     * @return
     * @throws ReportingException
     */
    public static ChapterCount calculateAverageForReadingDays(Collection<HistoryEntry> historyEntries)
            throws ReportingException {


        if (historyEntries == null || historyEntries.size() < 1) {
            throw new ReportingException("No history to report on.");
        }

        // Store unique days
        Set<LocalDateTime> days = new HashSet<>();
        for (HistoryEntry entry : historyEntries) {
            days.add(entry.getEntryTime().truncatedTo(ChronoUnit.DAYS));
        }

        return new ChapterCount(historyEntries.size() / (double) days.size());

    }

    /**
     * Given a Collection of unordered HistoryEntries, determine the average number of entries per day for the
     * days with recorded entries.
     * @param collectionHistoryEntries
     * @return
     * @throws ReportingException
     */
    public static ChapterCount calculateAverageForAllDays(Collection<HistoryEntry> collectionHistoryEntries)
            throws ReportingException {


        if (collectionHistoryEntries == null || collectionHistoryEntries.size() < 1) {
            throw new ReportingException("No history to report on.");
        }

        // List has to be sorted to determine first day.
        List<HistoryEntry> historyEntries = new ArrayList<>(collectionHistoryEntries);
        historyEntries.sort((o1, o2) -> o1.getEntryTime().compareTo(o2.getEntryTime()));

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

        long totalDays = ChronoUnit.DAYS.between(
                historyEntries.get(0).getEntryTime().truncatedTo(ChronoUnit.DAYS), now);

        return new ChapterCount(historyEntries.size() / (double) totalDays);
    }

    /**
     * Given a Collection of unordered HistoryEntries, determine the number of times each book has been read.
     *
     */
    public static BooksRead calculateBooksRead(Collection<HistoryEntry> historyEntries) {
        BooksRead booksRead = new BooksRead();

        // Initialize a book tracker per Book.
        Map<Book, BookReferenceTracker> bookReferenceTracker = new HashMap<>();
        for (Book b : Book.values()) {
            bookReferenceTracker.put(b, new BookReferenceTracker(b));
        }

        // Loop through the history (in any order).
        for (HistoryEntry entry : historyEntries) {
            Book book = Book.findByReference(entry.getReference());
            if (bookReferenceTracker.get(book).checkIfComplete(entry.getReference())) {
                booksRead.addBook(book);
                bookReferenceTracker.put(book, new BookReferenceTracker(book));
            }

        }

        return booksRead;

    }





}
