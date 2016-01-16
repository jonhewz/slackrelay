package com.lightsperfections.slackrelay.services.logos;

import com.lightsperfections.slackrelay.SlackRelayConfig;
import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import com.lightsperfections.slackrelay.services.DependentServiceException;
import com.lightsperfections.slackrelay.services.InternalImplementationException;
import com.lightsperfections.slackrelay.services.SlackRelayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Use this service to start a new plan
 *      i.e. /logos set bible-in-a-year-plan
 * This will initialize all tracks to the beginning, and set the start date to now.
 *
 * Use this service to update the indexes for their existing ReadingPlan.
 *      i.e. /logos set 10 10 10 9 9 9 9 9 9 9
 *
 * Created by jon on 1/15/16.
 */
public class Set implements SlackRelayService {

    private final String name;

    public Set(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String performAction(String userName, String userText)
            throws DependentServiceException, InternalImplementationException {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(SlackRelayConfig.class);
        ReadingPlanBookmarkDao readingPlanBookmarkDao = context.getBean(ReadingPlanBookmarkDao.class);

        // There will either be a non-numeric string plan name, or else a list of one or more integer track indexes.
        userText = userText.trim().toLowerCase();
        String[] tokens = userText.split("\\s+");

        // If the only token is empty, not enough info was provided
        if (tokens.length == 1 && "".equals(tokens[0])) {
            return "You did not provide enough parameters for the 'set' command.";

            // If the only token isn't solely numeric, treat as a plan name
        } else if (tokens.length == 1 && !tokens[0].matches("^[0-9]+$")) {
            readingPlanBookmarkDao.createReadingPlanBookmark(userName, tokens[0],
                    new ArrayList<Integer>(), LocalDateTime.now());

            return "Your reading plan was changed to " + tokens[0];

        // Otherwise iterate through the token(s) and make a list of indexes
        }  else {
            ReadingPlanBookmark readingPlanBookmark = readingPlanBookmarkDao.findByUserName(userName);
            if (readingPlanBookmark == null) {
                return "You do not have a Reading Plan Bookmark yet. You can not set the indexes.";
            }

            ArrayList<Integer> trackIndexes = new ArrayList<Integer>();
            for (int i = 0; i < tokens.length; i++) {
                try {
                    trackIndexes.add(Integer.valueOf(tokens[i]));
                } catch (NumberFormatException e) {
                    return "Indexes are not valid numbers";
                }
            }

            if (trackIndexes.size() < 1) {
                return "Your bookmark track indexes were NOT updated, since they were none provided.";
            }

            readingPlanBookmark.setTrackIndexes(trackIndexes);
            readingPlanBookmarkDao.updateReadingPlanBookmark(readingPlanBookmark);
            return "Success! Your bookmark track indexes have been updated to " + trackIndexes.toString();
        }
    }
}
