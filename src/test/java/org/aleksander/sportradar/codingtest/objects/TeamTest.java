package org.aleksander.sportradar.codingtest.objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @ParameterizedTest
    @NullAndEmptySource
    void attempting_to_create_a_team_with_null_or_empty_as_name_throws_exception(final String invalidTeamName) {
        // Given null or empty team name, when creating team, it throws an exception
        final InvalidParameterException invalidParameterException = assertThrows(
                InvalidParameterException.class, () -> new Team(invalidTeamName)
        );
        // and the error message is descriptive
        assertEquals("Parameter teamName cannot be null or empty!", invalidParameterException.getMessage());
    }
}
