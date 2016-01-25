package com.lightsperfections.slackrelay.beans;

/**
 * Created by jon on 1/23/16.
 */
public class Track {
    private int frequency;
    private Book[] books;

    public Book[] getBooks() {
        return books;
    }

    public int getFrequency() {
        return frequency;
    }

    Track(int frequency, Book[] books) {
        this.frequency = frequency;
        this.books = books;
    }
}
