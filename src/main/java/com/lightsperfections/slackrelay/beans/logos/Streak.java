package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by jon on 2/11/16.
 */
public class Streak {
    private LocalDateTime begin;
    private LocalDateTime end;

    public Streak(LocalDateTime begin, LocalDateTime end) {
        this.begin = begin;
        this.end = end;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * Example:
     * 21 days (1 Oct 2015 - 21 Oct 2015)
     *
     * @return
     */
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yy");
        return (ChronoUnit.DAYS.between(begin, end) + 1) + " days (" +
                begin.format(formatter) + " - " + end.format(formatter) + ")";
    }
}
