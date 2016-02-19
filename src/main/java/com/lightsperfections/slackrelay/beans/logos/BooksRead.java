package com.lightsperfections.slackrelay.beans.logos;

import com.lightsperfections.slackrelay.beans.Book;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jon on 2/11/16.
 */
public class BooksRead {
    private Map<Book, Integer> bookCount = new HashMap<>();

    public void addBook(Book book) {
        Integer count = bookCount.get(book);
        bookCount.put(book, count == null ? 1 : count + 1);
    }

    /**
     * Examples:
     * GE|1 EX|1 LE|2 RE|14
     *
     * @return
     */
    public String toString() {
        String rv = "";
        boolean first = true;
        for (Book book : Book.values()) {
            Integer count = bookCount.get(book);
            if (count != null && count > 0) {
                if (first) {
                    first = false;
                } else {
                    rv += " ";
                }
                rv += book + "|" + count;
            }
        }
        return rv;
    }
}
