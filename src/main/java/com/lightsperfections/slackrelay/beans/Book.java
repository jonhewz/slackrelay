package com.lightsperfections.slackrelay.beans;

/**
 * Created by jon on 1/16/16.
 */
public enum Book {
      GE (50,          "Genesis"),
      EX (40,           "Exodus"),
      LE (27,        "Leviticus"),
      NU (36,          "Numbers"),
      DE (34,      "Deuteronomy"),
     JOS (24,           "Joshua"),
     JUD (21,           "Judges"),
      RU (4,              "Ruth"),
     SA1 (31,       "1st Samuel"),
     SA2 (24,       "2nd Samuel"),
     KI1 (22,        "1st Kings"),
     KI2 (25,        "2nd Kings"),
     CH1 (29,   "1st Chronicles"),
     CH2 (36,   "2nd Chronicles"),
     EZR (10,             "Ezra"),
      NE (13,         "Nehemiah"),
      ES (10,           "Esther"),
     JOB (42,              "Job"),
      PS (150,          "Psalms"),
      PR (31,         "Proverbs"),
      EC (12,     "Ecclesiastes"),
      SO (8,   "Song of Solomon"),
     ISA (66,           "Isaiah"),
     JER (52,         "Jeremiah"),
      LA (5,      "Lamentations"),
     EZE (48,          "Ezekiel"),
      DA (12,           "Daniel"),
      HO (14,            "Hosea"),
     JOE (3,              "Joel"),
      AM (9,              "Amos"),
      OB (1,           "Obadiah"),
     JON (4,             "Jonah"),
     MIC (7,             "Micah"),
      NA (3,             "Nahum"),
     HAB (3,          "Habakkuk"),
     ZEP (3,         "Zephaniah"),
     HAG (2,            "Haggai"),
     ZEC (14,        "Zechariah"),
     MAL (4,           "Malachi"),
      MT (28,          "Matthew"),
      MR (16,             "Mark"),
      LU (24,             "Luke"),
     JOH (21,             "John"),
      AC (28,             "Acts"),
      RO (16,           "Romans"),
     CO1 (16,  "1st Corinthians"),
     CO2 (13,  "2nd Corinthians"),
      GA (6,         "Galatians"),
     EPH (6,         "Ephesians"),
     PHP (4,       "Philippians"),
     COL (4,        "Colossians"),
     TH1 (5, "1st Thessalonians"),
     TH2 (3, "2nd Thessalonians"),
     TI1 (6,       "1st Timothy"),
     TI2 (4,       "2nd Timothy"),
     TIT (3,             "Titus"),
     PHM (1,          "Philemon"),
     HEB (13,          "Hebrews"),
     JAS (5,             "James"),
     PE1 (5,         "1st Peter"),
     PE2 (3,         "2nd Peter"),
     JO1 (5,          "1st John"),
     JO2 (1,          "2nd John"),
     JO3 (1,          "3rd John"),
    JUDE (1,              "Jude"),
      RE (22,       "Revelation");

    public final int chapterCount;
    public final String displayName;

    Book(int chapterCount, String displayName) {
        this.chapterCount = chapterCount;
        this.displayName = displayName;
    }
}
