package org.aleksander.sportradar.codingtest.scoreboard.match;

import java.time.Instant;
import java.util.Comparator;

/**
 * Comparator for comparing matches. It compares the total score of the two matches, if they are equal it will compare the time they are created.
 * Using it on a list of matches will result in the following behavior:
 * - Returns a list with the highest total score first.
 * - If total score of two matches are alike, it will put the most recently created one first.
 */
public class MatchComparator implements Comparator<Match> {

    @Override
    public int compare(final Match match1, final Match match2) {
        return compare(match1.getTotalScore(), match2.getTotalScore(), match1.getTimeStarted(), match2.getTimeStarted());
    }

    private int compare(final int totalScore1, final int totalScore2, final Instant timeStarted1, final Instant timeStarted2) {
        final int totalScoreCompare = Integer.compare(totalScore2, totalScore1);

        if (totalScoreCompare != 0) {
            return totalScoreCompare;
        }

        return timeStarted2.compareTo(timeStarted1);
    }
}
