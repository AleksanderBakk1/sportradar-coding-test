package org.aleksander.sportradar.codingtest.objects;

import org.aleksander.sportradar.codingtest.exceptions.InvalidArgumentException;

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
        this.homeTeam = requireNonNullOrEmpty(homeTeam);
        this.awayTeam = requireNonNullOrEmpty(awayTeam);
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
        // This method could in theory be expanded upon to include that none of the scores could have decreased. But this is not specified in the spec, so will not do.
        if (matchScore != null) {
            this.matchScore = matchScore;
        }
        else {
            // If input is 'null' it will log the event and NOT set the score
            System.out.println("Invalid score was input. Ignoring the input.");
        }
    }

    public Instant getTimeStarted() {
        return timeStarted;
    }

    private Team requireNonNullOrEmpty(final Team team) {
        // teamName being null is covered by Team class
        if (team == null) {
            throw new InvalidArgumentException("A team cannot be 'null'");
        }
        return team;
    }
}
