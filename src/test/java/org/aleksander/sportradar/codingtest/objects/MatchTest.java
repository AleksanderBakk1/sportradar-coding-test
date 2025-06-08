package org.aleksander.sportradar.codingtest.objects;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.security.InvalidParameterException;
import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MatchTest {
    private final static Team TEST_HOME_TEAM = new Team("test_home_team");
    private final static Team TEST_AWAY_TEAM = new Team("test_away_team");

    @Test
    void a_new_match_contains_correct_team_names() {
        // When a new match is created
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // Then the team names are created correctly
        assertEquals("test_home_team", match.getHomeTeam().teamName());
        assertEquals("test_away_team", match.getAwayTeam().teamName());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void a_new_match_must_not_have_null_or_empty_team_names(final String invalidTeamName) {
        // When a new match is created with invalid home team
        final InvalidParameterException invalidParameterExceptionHome = assertThrows(
                InvalidParameterException.class, () -> new Match(new Team(invalidTeamName), TEST_AWAY_TEAM)
        );
        // and a new match is created with invalid away team
        final InvalidParameterException invalidParameterExceptionAway = assertThrows(
                InvalidParameterException.class, () -> new Match(TEST_HOME_TEAM, new Team(invalidTeamName))
        );
        // Then both teams resulted in an exception with a descriptive error message
        assertEquals("Parameter homeTeam cannot be null or empty!", invalidParameterExceptionHome.getMessage());
        assertEquals("Parameter awayTeam cannot be null or empty!", invalidParameterExceptionAway.getMessage());
    }

    @Test
    void a_new_match_starts_at_0_score_for_both_teams() {
        // When a new match is created
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // Then the match score is set to 0 - 0
        assertEquals(0, match.getMatchScore().homeScore());
        assertEquals(0, match.getMatchScore().awayScore());
    }

    @ParameterizedTest
    @MethodSource("totalScoreTestSource")
    void a_match_will_return_correct_total_score(final int homeScore, final int awayScore, final int expectedTotalScore) {
        // Given a new match
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // When the score is updated
        match.setMatchScore(new Score(homeScore, awayScore));
        // Then the correct scores is set in match
        assertEquals(homeScore, match.getMatchScore().homeScore());
        assertEquals(awayScore, match.getMatchScore().awayScore());
        // And total score is correct
        assertEquals(expectedTotalScore, match.getTotalScore());
    }

    public static Stream<Arguments> totalScoreTestSource() {
        return Stream.of(
                Arguments.of(7, 3, 10),
                Arguments.of(5, 2, 7),
                Arguments.of(0, 1, 1),
                Arguments.of(123, 123, 246)
        );
    }

    @Test
    void a_match_will_not_be_updated_if_score_is_null() {
        // Given a new match
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // When the score is updated with a null object
        match.setMatchScore(null);
        // Then the score in the match is not updated
        assertEquals(0, match.getMatchScore().homeScore());
        assertEquals(0, match.getMatchScore().awayScore());
    }

    @Test
    void a_new_match_sets_the_time_started_to_the_current_time() {
        // When a new match is created
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // Then the createTime is set to mocked current time
        // TODO: Look into using mock to ensure this test does not fail if it runs slowly or during debug mode
        final Instant expectedInstant = Instant.now();
        assertEquals(expectedInstant, match.getTimeStarted());
    }

    @ParameterizedTest
    @MethodSource("toStringTestSource")
    void toString_returns_expected_format_of_match_and_has_correct_values(final int homeScore, final int awayScore, final String expectedString) {
        // Given a new match
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // When the score is updated
        match.setMatchScore(new Score(homeScore, awayScore));
        // Then total score is correct
        assertEquals(expectedString, match.toString());
    }

    public static Stream<Arguments> toStringTestSource() {
        return Stream.of(
                Arguments.of(7, 3, "test_home_team 7 - test_away_team 3"),
                Arguments.of(5, 2, "test_home_team 5 - test_away_team 2"),
                Arguments.of(0, 1, "test_home_team 0 - test_away_team 1"),
                Arguments.of(123, 123, "test_home_team 123 - test_away_team 123")
        );
    }
}
