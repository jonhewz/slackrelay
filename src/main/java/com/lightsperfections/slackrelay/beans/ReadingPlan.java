package com.lightsperfections.slackrelay.beans;

import java.util.List;

/**
 * Created by jon on 1/16/16.
 */
public class ReadingPlan {
    private List<Track> tracks;
    private String name;

    public ReadingPlan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addTrack(Book[] books) {
        addTrack(1, books);
    }

    public void addTrack(int frequency, Book[] books) {
        tracks.add(new Track(frequency, books));
    }

    private class Track {
        private int frequency;
        private Book[] books;
        Track(int frequency, Book[] books) {
            this.frequency = frequency;
            this.books = books;
        }
    }
}
