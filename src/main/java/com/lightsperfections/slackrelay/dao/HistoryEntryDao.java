package com.lightsperfections.slackrelay.dao;

import com.lightsperfections.slackrelay.beans.logos.HistoryEntry;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 11:56 AM
 */
public interface HistoryEntryDao {
    void createHistoryEntryForUserName(String userName, LocalDateTime dateTime, String reference);
    List<? extends HistoryEntry> findHistoryEntriesByUserName(String userName);
}
