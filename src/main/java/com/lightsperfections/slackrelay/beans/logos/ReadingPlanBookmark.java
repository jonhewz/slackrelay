package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:20 AM
 */
public interface ReadingPlanBookmark {

    String getUserName();
    void setUserName(String userName);

    String getPlanName();
    void setPlanName(String planName);

    LocalDateTime getStartDate();
    void setStartDate(LocalDateTime startDate);

    Integer getIndex();
    void setIndex(Integer index);

}
