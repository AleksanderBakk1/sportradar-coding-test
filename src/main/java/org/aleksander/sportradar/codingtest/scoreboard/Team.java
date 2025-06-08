package org.aleksander.sportradar.codingtest.scoreboard;

import org.aleksander.sportradar.codingtest.exceptions.InvalidArgumentException;

public record Team(String teamName) {

    public Team {
        requireNonNullOrEmpty(teamName);
    }

    @Override
    public String toString() {
        return this.teamName;
    }

    private void requireNonNullOrEmpty(final String teamName) throws InvalidArgumentException {
        if (teamName == null || teamName.isEmpty()) {
            throw new InvalidArgumentException("Parameter 'teamName' cannot be null or empty!");
        }
    }
}
