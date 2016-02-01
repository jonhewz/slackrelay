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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 11/12/15
 * Time: 3:27 PM
 */
public class Status implements SlackRelayService {
    private static final int COLUMN_SIZE = 30;

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

        String status = "STATUS\n______\nPlan Name: " + readingPlanBookmark.getPlanName() +
                "\nStart Date: " + readingPlanBookmark.getStartDate() + "\nIndex: " +
                readingPlanBookmark.getIndex() + "\n";

        List<Integer> referenceIndexes = getReferenceIndexes(readingPlan, planIndex);
        List<Track> tracks = readingPlan.getTracks();
        int maxTrackSize = getMaxTrackSize(tracks);

        for (int i = 0; i < tracks.size(); i++) {
            Track track = tracks.get(i);
            status += (i < 10 ? i : "0" + i) + getTrackProgress(maxTrackSize, track, referenceIndexes.get(i)) + "\n";
        }

        return status;
    }

    /**
     * Given a Reading Plan, and an index representing the user's "place" within the plan (how many "pops" in are they),
     * determine the next reference.
     * <p/>
     * I acknowledge that this will slow down over time, but I doubt that it will be so inefficient that computation
     * time will be noticeable next to network time. The alternative is to store a complex bookmark for the user
     * representing the list of referenceIndexes, but I prefer this approach to start.
     *
     * @param plan
     * @param planIndex one-based counter, because the user will set it. A counter of 1 should give the first
     *                  chapter back. If 0 is passed in, it will be treated as a 1.
     * @return
     */
    private static List<Integer> getReferenceIndexes(ReadingPlan plan, int planIndex) {

        // The "global" plan counter, which increments through the references until it catches up with the
        // desired planIndex (the user's bookmark in the plan)
        int planCounter = 1; // one-based counter

        // Tracks the current track
        int trackIndex = 0; // zero-based index

        // Keeps the track from incrementing until the track frequency is exhausted
        int frequencyCounter = 1; // one-based counter

        // Create a per-track list of reference indexes, which correspond one-to-one with the tracks. So
        // the index marking the current reference in track[n] is held at referenceIndexes[n]
        List<Track> tracks = plan.getTracks();
        List<Integer> referenceIndexes = new ArrayList<Integer>();
        for (Track track : tracks) {referenceIndexes.add(0);} // zero-based indexes

        // The value to be returned.
        String reference = null;


        // Loop until the planCounter (which increments) catches up with the planIndex (which doesn't).
        while (planCounter <= planIndex) {

            // Counters will always be in a good state. Fetch reference first, then to the business of incrementing
            reference = tracks.get(trackIndex).getReferences().get(referenceIndexes.get(trackIndex));
            planCounter++;

            // Increment the referenceIndex, or else move it back to the beginning of the List if it's time.
            referenceIndexes.set(trackIndex,
                    referenceIndexes.get(trackIndex) >= tracks.get(trackIndex).getReferences().size() - 1 ?
                            0 : referenceIndexes.get(trackIndex) + 1);

            // If the desired frequency of this track is > 1, keep fetching from this track until
            // the frequency is exhausted. Once frequency is exhausted move on to the next track.
            if (frequencyCounter >= tracks.get(trackIndex).getFrequency()) {
                frequencyCounter = 1;
                trackIndex = trackIndex >= (tracks.size() - 1) ? 0 : trackIndex + 1;
            } else {
                frequencyCounter++;
            }
        }
        return referenceIndexes;
    }

    private static String getTrackProgress(int maxTrackSize, Track track, Integer trackIndex) {
        String progress = "";
        int numTildes = Math.round((float) track.getReferences().size() / (float)maxTrackSize * COLUMN_SIZE);

        for (int i = 0; i < numTildes; i++) {
            progress += "~";
        }
        return progress;
    }

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

