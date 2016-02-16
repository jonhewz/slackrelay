package com.lightsperfections.slackrelay.beans.logos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by jon on 2/11/16.
 */
public class ChapterCount {
    private double count;

    public ChapterCount(double count) {
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    /**
     * Examples:
     * 8.4 chapters
     * 0.5 chapters
     *
     * @return
     */
    public String toString() {
        return (Math.round(count * 10) / 10.0) + " chapters";
    }
}
