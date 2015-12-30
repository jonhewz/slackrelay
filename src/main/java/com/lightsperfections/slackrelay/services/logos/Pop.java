package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
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

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);
        ReadingPlanBookmarkDao readingPlanBookmarkDao = context.getBean(ReadingPlanBookmarkDao.class);
        return readingPlanBookmarkDao.findByUserName(userName).toString();

    }

    public String getName() {
        return name;
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