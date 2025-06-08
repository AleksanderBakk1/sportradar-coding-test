package org.aleksander.sportradar.codingtest.objects;

import java.time.Instant;
import java.util.UUID;

public class Match {
    private final String matchId;
    private final Team homeTeam;
    private final Team awayTeam;
    private Score matchScore;
    private final Instant timeStarted;


    public Match(final Team homeTeam, final Team awayTeam) {
        this.matchId = createMatchId();
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        // A match always starts at 0 - 0.
        this.matchScore = new Score(0, 0);
        // Set time of creation
        this.timeStarted = Instant.now();
    }

    private String createMatchId() {
        return UUID.randomUUID().toString();
    }

    public int getTotalScore() {
        return matchScore.homeScore() + matchScore.awayScore();
    }

    @Override
    public String toString() {
        return this.homeTeam + " " + matchScore.homeScore() + " - " + this.awayTeam + " " + matchScore.awayScore();
    }

    // Getters and setters
    public String getMatchId() {
        return matchId;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public Score getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(final Score matchScore) {
        this.matchScore = matchScore;
    }

    public Instant getTimeStarted() {
        return timeStarted;
    }
}
