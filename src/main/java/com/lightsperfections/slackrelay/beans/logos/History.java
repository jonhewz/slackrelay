package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:20 AM
 */
public interface History {

    public String getUserName();
    public LocalDateTime getDateTime();
    public String getReference();

}
