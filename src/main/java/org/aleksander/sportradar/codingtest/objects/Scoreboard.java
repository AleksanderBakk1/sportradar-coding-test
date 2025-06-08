package org.aleksander.sportradar.codingtest.objects;

import java.util.*;

public class Scoreboard {
    private final Map<String, Match> matchesInProgress;

    public Scoreboard() {
        matchesInProgress = new HashMap<>();
    }

    public String startNewMatch(final Team homeTeam, final Team awayTeam) {
        final Match newMatch = new Match(homeTeam, awayTeam);
        final String matchId = newMatch.getMatchId();
        matchesInProgress.put(matchId, newMatch);

        return matchId;
    }

    // TODO: determine if this method should throw a NotFoundException if no match is found with that matchId
    public Match getMatch(final String matchId) {
        return matchesInProgress.get(matchId);
    }

    public void updateScoreOfMatch(final String matchId, final Score newScore) {
        final Match matchToUpdate = this.matchesInProgress.get(matchId);
        matchToUpdate.setMatchScore(newScore);
    }

    public void finishMatch(final String matchId) {
        matchesInProgress.remove(matchId);
    }

    public List<Match> getMatchesInOrder() {
        // TODO: Implement logic to determine order the matches are returned in. (Ordered by total score, if some matches have same score, order them by time started)
        return this.matchesInProgress.values().stream().toList();
    }
}
