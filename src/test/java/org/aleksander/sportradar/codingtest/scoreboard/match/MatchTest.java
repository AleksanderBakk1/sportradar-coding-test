package org.aleksander.sportradar.codingtest.scoreboard.match;


import org.aleksander.sportradar.codingtest.exceptions.InvalidArgumentException;
import org.aleksander.sportradar.codingtest.scoreboard.Score;
import org.aleksander.sportradar.codingtest.scoreboard.Team;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void a_new_match_must_not_have_null_team() {
        // When a new match is created with null home team
        final InvalidArgumentException invalidArgumentExceptionHome = assertThrows(
                InvalidArgumentException.class, () -> new Match(null, TEST_AWAY_TEAM)
        );
        // and a new match is created with null away team
        final InvalidArgumentException invalidArgumentExceptionAway = assertThrows(
                InvalidArgumentException.class, () -> new Match(TEST_HOME_TEAM, null)
        );
        // Then both teams resulted in an exception with a descriptive error message
        assertEquals("A team cannot be 'null'", invalidArgumentExceptionHome.getMessage());
        assertEquals("A team cannot be 'null'", invalidArgumentExceptionAway.getMessage());
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
    void a_new_match_sets_the_time_started_to_the_current_time() throws InterruptedException {
        // When a new match is created
        final Match match = new Match(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        // Then the createTime is set to mocked current time
        sleep(10);
        assertNotNull(match.getTimeStarted());
        assertTrue(match.getTimeStarted().isBefore(Instant.now()));
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
