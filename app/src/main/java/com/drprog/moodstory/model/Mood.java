package com.drprog.moodstory.model;

public enum Mood {
    VERY_ANGRY (-5),
    JUST_ANGRY (-4),
    VERY_SAD (-3),
    SAD (-2),
    WORRIED (-1),
    NEUTRAL (0),
    GOOD (1),
    PRETTY_GOOD (2),
    EXITED (3),
    HAPPY (4),
    VERY_HEPPY (5)
    ;


    private int mLevel;

    Mood(int level) {
        this.mLevel = level;
    }

    public int getLevel() {
        return mLevel;
    }
}
