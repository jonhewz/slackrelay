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
import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Status implements SlackRelayService {
    private static final int COLUMN_SIZE = 22;

    private final String name;

    public Status(String name) {
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

        String status = "Plan Name: " + readingPlanBookmark.getPlanName() +
                "\nStart Date: " + readingPlanBookmark.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE) + "\n\n";


        ProgressReport progressReport = ReadingPlanNavigation.getProgressReport(readingPlan, planIndex);
        List<Track> tracks = readingPlan.getTracks();
        int maxTrackSize = getMaxTrackSize(tracks);


        // Generate an overview of the list of books in each track
        status += "Plan Details\n";
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            status += "[" + i + "] ";
            for (int j = 0; j < track.getBooks().length; j++) {
                status += track.getBooks()[j] + (j < track.getBooks().length - 1 ? " " : "");
            }
            if (track.getFrequency() > 1) {
                status += " x " + track.getFrequency();
            }
            status += "\n";
        }

        status += "\n";

        // Generate progress bars for each track showing where the user is in each one.
        status += "Progress\n";
        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            status += "[" + i + "] " +
                    getTrackProgress(maxTrackSize, track, progressReport.getReferenceIndexes().get(i)) + "\n";
        }

        // Show the list of upcoming references.
        status += "Index: " + readingPlanBookmark.getIndex() + "\nNext: ";
        List<String> references = progressReport.getReferences();
        for (int i = 0; i < references.size(); i++) {
            status += references.get(i);
            if (i < references.size()) {
                status += ", ";
            }
        }

        return status;
    }

    /**
     * Generate a progress bar for a specific track.
     *
     * @param maxTrackSize
     * @param track
     * @param trackIndex
     * @return
     */
    private static String getTrackProgress(int maxTrackSize, Track track, Integer trackIndex) {
        String progress = "";

        float multiplier = (float) track.getReferences().size() / (float)maxTrackSize;
        int totalTildes = Math.round(multiplier * COLUMN_SIZE);

        float tildeValue = (float) maxTrackSize / COLUMN_SIZE;
        int adjustedIndex = Math.round((float) trackIndex / tildeValue);

        for (int i = 0; i < adjustedIndex - 1; i++) {
            progress += "—";
        }

        progress += "|";

        for (int i = adjustedIndex + 1; i < totalTildes; i++) {
            progress += "—";
        }

        return progress;
    }

    /**
     * Given a list of tracks, what is the size of the longest one?
     * @param tracks
     * @return
     */
    private static int getMaxTrackSize(List<Track> tracks) {
        int max = 0;
        for (Track track : tracks) {
            if (track.getReferences().size() > max) {
                max = track.getReferences().size();
            }
        }
        return max;
    }
}

