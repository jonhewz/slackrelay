package com.lightsperfections.slackrelay.beans.logos;

import com.lightsperfections.slackrelay.beans.Book;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 2/19/16
 * Time: 12:17 PM
 */
public class BookReferenceTracker {
        Collection<String> references = new HashSet<>();

        public BookReferenceTracker(Book book) {
            for (int i = 1; i <= book.chapterCount; i++) {
                references.add(book.displayName + " " + i);
            }
        }

        public boolean checkIfComplete(String reference) {
            references.remove(reference);
            return references.isEmpty() ? true : false;
        }
}
