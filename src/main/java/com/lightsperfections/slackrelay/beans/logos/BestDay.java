package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by jon on 2/11/16.
 */
public class BestDay {
    private LocalDateTime day;
    private Integer count;

    public BestDay(LocalDateTime day, Integer count) {
        this.day = day;
        this.count = count;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public Integer getCount() {
        return count;
    }

    /**
     * Example:
     * 1 Oct 2015 (21 chapters)
     *
     * @return
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy");
        return day.format(formatter) + " (" + count + " chapter" + (count > 1 ? "s" : "") + ")";
    }
}
