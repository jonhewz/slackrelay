package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.ReadingPlanConfig;
import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.beans.Book;
import com.lightsperfections.slackrelay.beans.ReadingPlan;
import com.lightsperfections.slackrelay.beans.Track;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Pop implements SlackRelayService {

    private final String name;

    public Pop(String name) {
        this.name = name;
    }

    /**
     * Display usage
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

        // This context for Database stored bookmark
        AnnotationConfigApplicationContext bookmarkContext =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);

        // This context for Application managed reading plan
        AnnotationConfigApplicationContext readingPlanContext =
                new AnnotationConfigApplicationContext(ReadingPlanConfig.class);

        // Get the bookmark for this specific user
        ReadingPlanBookmarkDao readingPlanBookmarkDao = bookmarkContext.getBean(ReadingPlanBookmarkDao.class);
        ReadingPlanBookmark readingPlanBookmark = readingPlanBookmarkDao.findByUserName((userName));
        if (readingPlanBookmark == null) {
            return "No reading plan found for user " + userName;
        }

        // Find the plan from the user's plan name
        ReadingPlan readingPlan = readingPlanContext.getBean(readingPlanBookmark.getPlanName(), ReadingPlan.class);
        if (readingPlan == null) {
            return "No reading plan exists with the name " + readingPlanBookmark.getPlanName();
        }

        return findReference(readingPlan, readingPlanBookmark);

    }

    public String getName() {
        return name;
    }


    /**
     * Consider a plan:
     * Track 1 = Mark x 2
     * Track 2 = Philemon, 3 John, Jude x 1
     *
     *  i | Passage
     * ------------
     * 00 | Mark 1
     * 01 | Mark 2
     * 02 | Philemon
     * 03 | Mark 3
     * 04 | Mark 4
     * 05 | 3 John
     * 06 | Mark 5
     * 07 | Mark 6
     * 08 | Jude
     * 09 | Mark 7
     * 10 | Mark 8
     * 11 | Philemon
     * 12 | Mark 9
     * 13 | Mark 10
     * 14 | 3 John
     * 15 | Mark 11
     * 16 | Mark 12
     * 17 | Jude
     * 18 | Mark 13
     * 19 | Mark 14
     * 18 | Philemon
     * 20 | Mark 15
     * 21 | Mark 16
     * 22 | 3 John
     * 23 | Mark 1
     * 24 | Mark 2
     *
     *
     *
     * @param plan
     * @param bookmark
     * @return
     */
    private static String findReference(ReadingPlan plan, ReadingPlanBookmark bookmark) {
        int planIndex = 0;

        for (Track currentTrack : plan.getTracks()) {
            Book[] books = currentTrack.getBooks();
            int frequency = currentTrack.getFrequency();

            for (Book book : books) {
                for (int i = 0; i < frequency; i++) {
                    for (int i = 1; i <= book.chapterCount; i++) {
                        if (planIndex++ >= bookmark.getIndex()) {
                            return book.displayName + " " + i;
                        }
                    }
                }
            }
        }
    }
}

/**
 Scratchpad

 A Plan can internally  pointers

 Static fully exploded circular linked lists

 Plan = List<Tracks>

 Track =
 List<Books>
 Frequency

 Book =
 name
 numChapters


 Database Tables:

 BookCounter
 -----------
 User -> | Genesis:1
         | Exodus:2

 ReadingPlan
 -----------
 User -> | PlanName: blah
         | Indexes: 1,5,14,9...
         | DateStarted: 2015-5-12

 Statistics
 ----------
 User -> | BeginDate: 2015-5-12 (for rate calculations)
         | TotalChapters: 12
         | MostInADay: 30
         | MostInADayDate: 2015-2-2
         | LongestStreak: 12
         | LongestStreakStartDate: 2015-2-3
         | LastMonthDailyAverage:









 */