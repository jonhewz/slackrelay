package com.lightsperfections.slackrelay.beans.logos;

import com.lightsperfections.slackrelay.beans.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 1/23/16.
 */
public class Track {
    private String description;
    private Book[] books;
    private int frequency;
    private List<String> references = new ArrayList<String>();

    /**
     * This object is being created with a list of books, but will be internally stored as a list of
     * book/chapter references.
     *
     * @param books
     */
    public Track(String description, int frequency, Book[] books) {
        this.description = description;
        this.frequency = frequency;
        this.books = books;

        for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {
            for (int chapterIndex = 1; chapterIndex <= books[bookIndex].chapterCount; chapterIndex++ ) {
                references.add(books[bookIndex].displayName + " " + chapterIndex);
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public int getFrequency() {
        return frequency;
    }

    public List<String> getReferences() {
        return references;
    }

    public Book[] getBooks() {
        return books;
    }

    /**
     * Example:
     *
     * Wisdom Books | JOB SO PR EC x 2
     *
     * @return
     */
    public String toString() {
        String rv = getDescription() + " | ";

        for (int i = 0; i < books.length; i++) {
            rv += books[i];
            if (i < books.length - 1) {
                rv += " ";
            }
        }

        if (frequency > 1) {
            rv += " x " + frequency;
        }

        return rv;
    }
}
