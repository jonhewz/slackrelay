package com.lightsperfections.slackrelay.dao;

import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 11:56 AM
 */
public interface ReadingPlanBookmarkDao {
    public ReadingPlanBookmark findByUserName(String userName);
    public void updateReadingPlanBookmark(ReadingPlanBookmark readingPlan);
    public ReadingPlanBookmark createReadingPlanBookmark(String userName, String planName,
                                          List<Integer> TrackIndexes, LocalDateTime startDate);
}
