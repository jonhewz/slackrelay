package com.lightsperfections.slackrelay;


import com.lightsperfections.slackrelay.beans.Book;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jon on 1/17/16.
 */
@Configuration
public class ReadingPlanConfig {

    @Bean(name = "simple_reading_plan")
    public ReadingPlan getSimpleReadingPlan() {
        ReadingPlan readingPlan = new ReadingPlan("Simple");

        readingPlan.addTrack(new Book[]{Book.GE, Book.EX, Book.LE});
        readingPlan.addTrack(2, new Book[]{Book.MT, Book.MR, Book.LU, Book.JOH});
        readingPlan.addTrack(3, new Book[]{Book.RO, Book.GA, Book.EPH, Book.HEB});

        return readingPlan;
    }
}

/*
 * What would be ideal is specifying a simple file format that is parsed at configuration initialization.
 *
 * Simple
 *  [GE, EX, LE] x 1
 *  [MT, MK, LU, JOH] x 2
 *  [RO-PHM] x 2
 *
 */
