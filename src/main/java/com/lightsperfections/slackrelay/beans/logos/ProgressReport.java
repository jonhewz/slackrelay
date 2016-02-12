package com.lightsperfections.slackrelay.beans.logos;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 2/2/16
 * Time: 3:45 PM
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Private bean to store the referenceIndexes and a list of the next references.
 */
public class ProgressReport {
    private List<Integer> referenceIndexes = new ArrayList<Integer>();
    private List<String> references = new ArrayList<String>();

    public List<Integer> getReferenceIndexes() {
        return referenceIndexes;
    }

    public void addReferenceIndex(Integer referenceIndex) {
        referenceIndexes.add(referenceIndex);
    }

    public List<String> getReferences() {
        return references;
    }

    public void addReference(String reference) {
        references.add(reference);
    }

}