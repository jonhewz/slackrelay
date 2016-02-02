package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.ReadingPlanConfig;
import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import com.lightsperfections.slackrelay.beans.logos.Track;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlanBookmark;
import com.lightsperfections.slackrelay.utils.logos.ReadingPlanNavigation;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;


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

    public String getName() {
        return name;
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
        AnnotationConfigApplicationContext mainContext =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);

        // This context for Application managed reading plan
        AnnotationConfigApplicationContext readingPlanContext =
                new AnnotationConfigApplicationContext(ReadingPlanConfig.class);

        // Get the bookmark for this specific user
        ReadingPlanBookmarkDao readingPlanBookmarkDao = mainContext.getBean(ReadingPlanBookmarkDao.class);
        ReadingPlanBookmark readingPlanBookmark = readingPlanBookmarkDao.findByUserName((userName));
        if (readingPlanBookmark == null) {
            return "No reading plan found for user " + userName;
        }

        // Find the plan from the user's plan name
        ReadingPlan readingPlan = readingPlanContext.getBean(readingPlanBookmark.getPlanName(), ReadingPlan.class);
        if (readingPlan == null) {
            return "No reading plan exists with the name " + readingPlanBookmark.getPlanName();
        }

        // Adjust for 0 index. If that got set somehow, adjust it to 1.
        Integer planIndex = readingPlanBookmark.getIndex();
        if (planIndex == 0) planIndex = 1;
        String reference = ReadingPlanNavigation.getReference(readingPlan, planIndex);

        if (reference == null) {
            return "No reference could be found at your bookmarked location.";
        } else {

            // Increment the bookmark
            readingPlanBookmark.setIndex(planIndex + 1);
            readingPlanBookmarkDao.updateReadingPlanBookmark(readingPlanBookmark);

        }
        return reference;

        // Look up the reference and send it back
        //SlackRelayService service = mainContext.getBean("esv.passagequery", SlackRelayService.class);
        //return service.performAction(userName, reference);
    }


}

