package com.lightsperfections.slackrelay;


import com.lightsperfections.slackrelay.beans.Book;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Define all reading plans. Currently not maintained in DB or anything - just defined in this config file.
 *
 * Created by jon on 1/17/16.
 */
@Configuration
public class ReadingPlanConfig {

    @Bean(name = "simple")
    public ReadingPlan getSimpleReadingPlan() {
        ReadingPlan readingPlan = new ReadingPlan("simple", "Test plan for debugging");

        readingPlan.addTrack("One chapter book", new Book[]{Book.OB});
        readingPlan.addTrack("Two chapter book", new Book[]{Book.HAG});
        readingPlan.addTrack("Three chapter book", new Book[]{Book.HAB});

        return readingPlan;
    }

    @Bean(name = "horner")
    public ReadingPlan getHorner() {
        ReadingPlan readingPlan = new ReadingPlan("horner", "The original plan by Professor Grant Horner");
        readingPlan.addTrack("Gospels", new Book[]{
                Book.MT, Book.MR, Book.LU, Book.JOH});
        readingPlan.addTrack("Pentateuch", new Book[]{
                Book.GE, Book.EX, Book.LE, Book.NU, Book.DE});
        readingPlan.addTrack("Major Epistles", new Book[]{
                Book.RO, Book.CO1, Book.CO2, Book.GA, Book.EPH, Book.PHP, Book.COL, Book.HEB});
        readingPlan.addTrack("Minor Epistles, Revelation", new Book[]{
                Book.TH1, Book.TH2, Book.TI1, Book.TI2, Book.TIT, Book.PHM, Book.JAS,
                Book.PE1, Book.PE2, Book.JO1, Book.JO2, Book.JO3, Book.JUDE, Book.RE});
        readingPlan.addTrack("Wisdom sans Proverbs", new Book[]{
                Book.JOB, Book.EC, Book.SO});
        readingPlan.addTrack("Psalms", new Book[]{
                Book.PS});
        readingPlan.addTrack("Proverbs", new Book[]{
                Book.PR});
        readingPlan.addTrack("History", new Book[]{
                Book.JOS, Book.JUD, Book.RU, Book.SA1, Book.SA2, Book.KI1, Book.KI2, Book.CH1,
                Book.CH2, Book.EZR, Book.NE, Book.ES});
        readingPlan.addTrack("Prophets", new Book[]{
                Book.ISA, Book.JER, Book.LA, Book.EZE, Book.DA, Book.HO, Book.JOE, Book.AM,
                Book.OB, Book.JON, Book.MIC, Book.NA, Book.HAB, Book.ZEC, Book.HAG, Book.ZEC, Book.MAL});
        readingPlan.addTrack("Acts", new Book[]{
                Book.AC});

        return readingPlan;
    }

    @Bean(name="jdh")
    public ReadingPlan getJDH() {
        ReadingPlan readingPlan = new ReadingPlan("jdh", "Jon's plan for daily reading");

        readingPlan.addTrack("Psalms...", new Book[]{
                Book.PS});
        readingPlan.addTrack("... and the prophets point to...", new Book[]{
                Book.ISA, Book.JER, Book.LA, Book.EZE, Book.HO, Book.JOE, Book.AM,
                Book.OB, Book.JON, Book.MIC, Book.NA, Book.HAB, Book.ZEC, Book.HAG,
                Book.ZEC, Book.MAL});
        readingPlan.addTrack("... the Gospel", new Book[]{
                Book.JOH, Book.MR, Book.MT, Book.LU, Book.AC});
        readingPlan.addTrack("Major Paul, in order of writing", new Book[]{
                Book.TH1, Book.TH2, Book.CO1, Book.CO2, Book.GA, Book.RO, Book.COL,
                Book.EPH, Book.PHP});
        readingPlan.addTrack("Minor Paul and the rest of the epistles, in order of writing", new Book[]{
                Book.PHM, Book.JAS, Book.PE1, Book.HEB, Book.TI1, Book.TIT, Book.PE2,
                Book.TI2, Book.JUDE, Book.JO1, Book.JO2, Book.JO3});
        readingPlan.addTrack("Wisdom", new Book[]{
                Book.JOB, Book.SO, Book.PR, Book.EC});
        readingPlan.addTrack("OT history, doubled up", 2, new Book[]{
                Book.GE, Book.EX, Book.LE, Book.NU, Book.DE, Book.JOS, Book.JUD, Book.RU,
                Book.SA1, Book.SA2, Book.KI1, Book.KI2, Book.EZR, Book.CH1, Book.CH2, Book.NE, Book.ES});
        readingPlan.addTrack("Apocalyptic", new Book[]{
                Book.DA, Book.RE});

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
