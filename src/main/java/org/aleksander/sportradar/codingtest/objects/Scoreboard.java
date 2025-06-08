package org.aleksander.sportradar.codingtest.objects;

import org.aleksander.sportradar.codingtest.objects.comparator.MatchComparator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        final Match matchToUpdate = getMatch(matchId);
        matchToUpdate.setMatchScore(newScore);
    }

    public void finishMatch(final String matchId) {
        matchesInProgress.remove(matchId);
    }

    public List<Match> getMatchesInOrder() {
        final List<Match> listOfMatches = this.matchesInProgress.values().stream().toList();
        return listOfMatches.stream()
                .sorted(new MatchComparator())
                .collect(Collectors.toList());
    }
}
