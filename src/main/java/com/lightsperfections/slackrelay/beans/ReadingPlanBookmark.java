package com.lightsperfections.slackrelay.beans;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:20 AM
 */
public interface ReadingPlanBookmark {

    public String getUserName();
    public void setUserName(String userName);

    public String getPlanName();
    public void setPlanName(String planName);

    public List<Integer> getTrackIndexes();
    public void setTrackIndexes(List<Integer> trackIndexes);

    public LocalDateTime getStartDate();
    public void setStartDate(LocalDateTime startDate);

}
