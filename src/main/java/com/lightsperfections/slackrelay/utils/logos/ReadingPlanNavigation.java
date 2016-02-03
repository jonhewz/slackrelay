package com.lightsperfections.slackrelay.utils.logos;

import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import com.lightsperfections.slackrelay.beans.logos.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Static utility methods to deal with the navigation of the complex ReadingPlan structure. The intent is to keep
 * all of the looping in a single place where it can be properly optimized and refactored.
 *
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 2/2/16
 * Time: 11:26 AM
 */
public class ReadingPlanNavigation {
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
    public static String getReference(ReadingPlan plan, int planIndex) {
        return loop(plan, planIndex).getReferences().get(0);
    }

    /**
     * Given a Reading Plan, and an index representing the user's "place" within the plan (how many "pops" in are they),
     * determine their place within each track as well as the next set of references that will be read.
     *
     * @param plan
     * @param planIndex one-based counter, because the user will set it. A counter of 1 should give the first
     *                  chapter back. If 0 is passed in, it will be treated as a 1.
     * @return
     */
    public static ProgressReport getProgressReport(ReadingPlan plan, int planIndex) {
        return loop(plan, planIndex);
    }

    /**
     * Loop through the ReadingPlan gathering important data to be used elsewhere.
     *
     * @param plan
     * @param planIndex
     * @return
     */
    public static ProgressReport loop(ReadingPlan plan, int planIndex) {

        ProgressReport progressReport = new ProgressReport();

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
        while (planCounter <= planIndex + tracks.size() - 1) {


            // Counters will always be in a good state. Fetch reference first, then to the business of incrementing
            reference = tracks.get(trackIndex).getReferences().get(referenceIndexes.get(trackIndex));


            // Save off extra references
            if (planCounter >= planIndex) {
                progressReport.addReference(reference);
            }

            // Now increment
            planCounter++;

            // Snapshot the track markers when you reach the desired spot.
            if (planCounter >= planIndex) {
                for (Integer index : referenceIndexes) {
                    progressReport.addReferenceIndex(index);
                }

            }

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
        return progressReport;
    }


}
