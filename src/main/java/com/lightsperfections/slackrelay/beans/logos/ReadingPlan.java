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
    private String description;

    public ReadingPlan(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void addTrack(String description, Book[] books) {
        addTrack(description, 1, books);
    }

    public void addTrack(String description, int frequency, Book[] books) {
        tracks.add(new Track(description, frequency, books));
    }

    /**
     * Example:
     *
     * Plan Name: jdh
     * This is the plan I came up with
     * [0] Wisdom Books | JOB SO PR EC x 2
     * [1] Psalms | PS
     *
     * @return
     */
    public String toString() {
        String rv = "Plan Name: " + getName() + "\n" + getDescription() + "\n";

        for (int i = 0; i < tracks.size(); i++) {
            rv += "[" + i + "] " + tracks.get(i).toString() + "\n";
        }

        return rv;
    }


}
