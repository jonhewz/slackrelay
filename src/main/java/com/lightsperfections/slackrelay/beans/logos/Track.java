package com.lightsperfections.slackrelay.beans.logos;

import com.lightsperfections.slackrelay.beans.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jon on 1/23/16.
 */
public class Track {
    private Book[] books;
    private int frequency;
    private List<String> references = new ArrayList<String>();

    /**
     * This object is being created with a list of books, but will be internally stored as a list of
     * book/chapter references.
     *
     * @param books
     */
    public Track(int frequency, Book[] books) {
        this.frequency = frequency;
        this.books = books;

        for (int bookIndex = 0; bookIndex < books.length; bookIndex++) {
            for (int chapterIndex = 1; chapterIndex <= books[bookIndex].chapterCount; chapterIndex++ ) {
                references.add(books[bookIndex].displayName + " " + chapterIndex);
            }
        }
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
}
