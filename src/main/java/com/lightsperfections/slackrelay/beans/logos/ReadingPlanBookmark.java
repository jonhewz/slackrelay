package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;

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

    public LocalDateTime getStartDate();
    public void setStartDate(LocalDateTime startDate);

    public Integer getIndex();
    public void setIndex(Integer index);

}
