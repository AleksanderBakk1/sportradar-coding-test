package org.aleksander.sportradar.codingtest.objects;

import org.aleksander.sportradar.codingtest.exceptions.EntryNotFoundException;
import org.aleksander.sportradar.codingtest.exceptions.InvalidArgumentException;
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

    public Match getMatch(final String matchId) throws EntryNotFoundException {
        if (matchId == null || matchId.isEmpty()) {
            throw new InvalidArgumentException("Parameter 'matchId' can not be 'null' or empty.");
        }
        final Match retrievedMatch = matchesInProgress.get(matchId);
        if (retrievedMatch == null) {
            throw new EntryNotFoundException("No match entry was found when trying to get match with matchId '" + matchId + "'");
        }
        return retrievedMatch;
    }

    public void updateScoreOfMatch(final String matchId, final Score newScore) throws EntryNotFoundException {
        if (newScore == null) {
            throw new InvalidArgumentException("The score provided can not be 'null'");
        }
        final Match matchToUpdate = getMatch(matchId);
        matchToUpdate.setMatchScore(newScore);
    }

    public void finishMatch(final String matchId) throws EntryNotFoundException {
        // Get the match to see if matchId is valid and if match exists.
        getMatch(matchId);
        // If no exceptions are thrown during getting of match, then we can remove it.
        matchesInProgress.remove(matchId);
    }

    public List<Match> getMatchesInOrder() {
        final List<Match> listOfMatches = this.matchesInProgress.values().stream().toList();
        return listOfMatches.stream()
                .sorted(new MatchComparator())
                .collect(Collectors.toList());
    }
}
