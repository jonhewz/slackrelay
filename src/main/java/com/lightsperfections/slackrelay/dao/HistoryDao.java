package com.lightsperfections.slackrelay.dao;

import com.lightsperfections.slackrelay.beans.logos.History;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlanBookmark;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 11:56 AM
 */
public interface HistoryDao {
    public void createHistory(String userName, LocalDateTime dateTime, String reference);
}
