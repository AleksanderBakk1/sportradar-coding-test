package org.aleksander.sportradar.codingtest.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamTest {
    private static final String TEAM_NAME = "test_team_name";

    @Test
    void toString_returns_team_name() {
        // When a team is created
        final Team team = new Team(TEAM_NAME);
        // Then the team name is stored correctly
        assertEquals(TEAM_NAME, team.teamName());
        // And the toString() methods returns the team name when called
        assertEquals(TEAM_NAME, team.toString());
    }
}
