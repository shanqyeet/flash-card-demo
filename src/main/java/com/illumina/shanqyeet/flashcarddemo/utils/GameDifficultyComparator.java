package com.illumina.shanqyeet.flashcarddemo.utils;

import java.util.Comparator;

import static com.illumina.shanqyeet.flashcarddemo.enums.GameDifficulty.MathTableGame.*;

public class GameDifficultyComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if(o1.equals(o2)) return 0;
        if(o1.equals(HARD.name()) && o2.equals(MEDIUM.name()) || o2.equals(EASY.name())) return 1;
        if(o1.equals(EASY.name()) || o1.equals(MEDIUM.name())) return -1;
        return o1.compareTo(o2);
    }
}
