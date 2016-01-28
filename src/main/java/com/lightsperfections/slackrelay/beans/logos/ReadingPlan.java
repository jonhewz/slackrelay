package com.lightsperfections.slackrelay.beans.logos;

import com.lightsperfections.slackrelay.beans.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 1/16/16.
 */
public class ReadingPlan {
    private List<Track> tracks = new ArrayList<Track>();
    private String name;

    public ReadingPlan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(Book[] books) {
        addTrack(1, books);
    }

    public void addTrack(int frequency, Book[] books) {
        tracks.add(new Track(frequency, books));
    }




}
