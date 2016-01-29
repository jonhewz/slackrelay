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

    @Bean(name = "horner")
    public ReadingPlan getHorner() {
        ReadingPlan readingPlan = new ReadingPlan("horner");
        readingPlan.addTrack(new Book[]{Book.MT, Book.MR, Book.LU, Book.JOH});
        readingPlan.addTrack(new Book[]{Book.GE, Book.EX, Book.LE, Book.NU, Book.DE});
        readingPlan.addTrack(new Book[]{Book.RO, Book.CO1, Book.CO2, Book.GA, Book.EPH, Book.PHP, Book.COL, Book.HEB});
        readingPlan.addTrack(new Book[]{Book.TH1, Book.TH2, Book.TI1, Book.TI2, Book.TIT, Book.PHM, Book.JAS, Book.PE1, Book.PE2, Book.JO1, Book.JO2, Book.JO3, Book.JUDE, Book.RE});
        readingPlan.addTrack(new Book[]{Book.JOB, Book.EC, Book.SO});
        readingPlan.addTrack(new Book[]{Book.PS});
        readingPlan.addTrack(new Book[]{Book.PR});
        readingPlan.addTrack(new Book[]{Book.JOS, Book.JUD, Book.RU, Book.SA1, Book.SA2, Book.KI1, Book.KI2, Book.CH1, Book.CH2, Book.EZR, Book.NE, Book.ES});
        readingPlan.addTrack(new Book[]{Book.ISA, Book.JER, Book.LA, Book.EZE, Book.DA, Book.HO, Book.JOE, Book.AM, Book.OB, Book.JON, Book.MIC, Book.NA, Book.HAB, Book.ZEC, Book.HAG, Book.ZEC, Book.MAL});
        readingPlan.addTrack(new Book[]{Book.AC});

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
