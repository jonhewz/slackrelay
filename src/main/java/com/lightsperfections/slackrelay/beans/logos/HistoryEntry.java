package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:20 AM
 */
public interface HistoryEntry {
    String getUserName();
    void setUserName(String userName);

    LocalDateTime getEntryTime();
    void setEntryTime(LocalDateTime entryTime);

    String getReference();
    void setReference(String reference);
}
