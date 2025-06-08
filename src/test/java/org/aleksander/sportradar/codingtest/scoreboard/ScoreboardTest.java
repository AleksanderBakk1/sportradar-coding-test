package org.aleksander.sportradar.codingtest.scoreboard;


import org.aleksander.sportradar.codingtest.exceptions.EntryNotFoundException;
import org.aleksander.sportradar.codingtest.exceptions.InvalidArgumentException;
import org.aleksander.sportradar.codingtest.scoreboard.match.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreboardTest {
    private final static Team TEST_HOME_TEAM = new Team("test_home_team");
    private final static Team TEST_AWAY_TEAM = new Team("test_away_team");

    private Scoreboard scoreboard;

    @BeforeEach
    void setup() {
        scoreboard = new Scoreboard();
    }

    // Start new match
    @Test
    void scoreboard_can_add_a_new_match() throws EntryNotFoundException {
        // When a new match is started
        final String matchId = scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // Then the match is stored and can be retrieved by using the match ID
        final Match storedMatch = scoreboard.getMatch(matchId);
        final Score storedMatchScore = storedMatch.getMatchScore();

        assertEquals(TEST_HOME_TEAM, storedMatch.getHomeTeam());
        assertEquals(TEST_AWAY_TEAM, storedMatch.getAwayTeam());
        assertEquals(0, storedMatchScore.homeScore());
        assertEquals(0, storedMatchScore.awayScore());
    }

    @Test
    void scoreboard_getting_a_match_with_an_invalid_matchId_leads_to_an_exception() {
        // Given a new match is started
        scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // When using an invalid matchId, and trying to get a match it leads to exception
        final EntryNotFoundException entryNotFoundException = assertThrows(EntryNotFoundException.class, () -> scoreboard.getMatch("123"));

        // Then a descriptive error message is given
        assertEquals("No match entry was found when trying to get match with matchId '123'", entryNotFoundException.getMessage());
    }

    @Test
    void scoreboard_can_add_several_matches() throws EntryNotFoundException {
        // Given 6 different teams, when a new match is started
        final String matchId1 = scoreboard.startNewMatch(new Team("home1"), new Team("away1"));
        final String matchId2 = scoreboard.startNewMatch(new Team("home2"), new Team("away2"));
        final String matchId3 = scoreboard.startNewMatch(new Team("home3"), new Team("away3"));

        // Then the match is stored and can be retrieved by using the match ID
        final Match storedMatch1 = scoreboard.getMatch(matchId1);
        final Match storedMatch2 = scoreboard.getMatch(matchId2);
        final Match storedMatch3 = scoreboard.getMatch(matchId3);

        assertEquals("home1", storedMatch1.getHomeTeam().teamName());
        assertEquals("away1", storedMatch1.getAwayTeam().teamName());
        assertEquals(0, storedMatch1.getMatchScore().homeScore());
        assertEquals(0, storedMatch1.getMatchScore().awayScore());

        assertEquals("home2", storedMatch2.getHomeTeam().teamName());
        assertEquals("away2", storedMatch2.getAwayTeam().teamName());
        assertEquals(0, storedMatch2.getMatchScore().homeScore());
        assertEquals(0, storedMatch2.getMatchScore().awayScore());

        assertEquals("home3", storedMatch3.getHomeTeam().teamName());
        assertEquals("away3", storedMatch3.getAwayTeam().teamName());
        assertEquals(0, storedMatch3.getMatchScore().homeScore());
        assertEquals(0, storedMatch3.getMatchScore().awayScore());
    }

    // Update match
    @Test
    void scoreboard_can_update_match() throws EntryNotFoundException {
        // Given a new match is started
        final String matchId = scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // When the match is updated by using the match ID
        scoreboard.updateScoreOfMatch(matchId, new Score(3, 7));

        // Then the match has been updated with the new score
        final Match storedMatch = scoreboard.getMatch(matchId);
        final Score storedMatchScore = storedMatch.getMatchScore();

        assertEquals(TEST_HOME_TEAM, storedMatch.getHomeTeam());
        assertEquals(TEST_AWAY_TEAM, storedMatch.getAwayTeam());
        assertEquals(3, storedMatchScore.homeScore());
        assertEquals(7, storedMatchScore.awayScore());
    }

    @Test
    void scoreboard_updating_a_match_with_an_invalid_matchId_leads_to_an_exception() {
        // Given a new match is started
        scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // When using an invalid matchId, and trying to update a match it leads to exception
        final EntryNotFoundException entryNotFoundException = assertThrows(EntryNotFoundException.class, () -> scoreboard.updateScoreOfMatch("123", new Score(1, 0)));

        // Then a descriptive error message is given
        assertEquals("No match entry was found when trying to get match with matchId '123'", entryNotFoundException.getMessage());
    }

    @Test
    void scoreboard_updating_a_match_with_an_invalid_score_leads_to_an_exception() {
        // Given a new match is started
        final String matchId = scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // When using an invalid score of 'null', and trying to update a match it leads to exception
        final InvalidArgumentException invalidArgumentException = assertThrows(InvalidArgumentException.class, () -> scoreboard.updateScoreOfMatch(matchId, null));

        // Then a descriptive error message is given
        assertEquals("The score provided can not be 'null'", invalidArgumentException.getMessage());
    }

    // Finish match
    @Test
    void scoreboard_can_finish_match() throws EntryNotFoundException {
        // Given a new match is started and updated
        final String matchId = scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);
        scoreboard.updateScoreOfMatch(matchId, new Score(3, 7));

        // When the match is finished by using the match ID
        scoreboard.finishMatch(matchId);

        // Then the match has been removed from the map of matches and throws when trying to retrieve
        assertThrows(EntryNotFoundException.class, () -> scoreboard.getMatch(matchId));
    }

    @Test
    void scoreboard_finishing_a_match_with_an_invalid_matchId_leads_to_an_exception() {
        // Given a new match is started
        scoreboard.startNewMatch(TEST_HOME_TEAM, TEST_AWAY_TEAM);

        // When using an invalid matchId, and trying to update a match it leads to exception
        final EntryNotFoundException entryNotFoundException = assertThrows(EntryNotFoundException.class, () -> scoreboard.finishMatch("123"));

        // Then a descriptive error message is given
        assertEquals("No match entry was found when trying to get match with matchId '123'", entryNotFoundException.getMessage());
    }

    // Get matches in order
    @Test
    void scoreboard_can_return_ordered_list() throws InterruptedException, EntryNotFoundException {
        // Given matches are created in a specific order
        final Scoreboard scoreboard = new Scoreboard();
        final String mexicoCanadaId = scoreboard.startNewMatch(new Team("Mexico"), new Team("Canada"));
        sleep(10);
        final String spainBrazilId = scoreboard.startNewMatch(new Team("Spain"), new Team("Brazil"));
        sleep(10);
        final String germanyFranceId = scoreboard.startNewMatch(new Team("Germany"), new Team("France"));
        sleep(10);
        final String uruguayItalyId = scoreboard.startNewMatch(new Team("Uruguay"), new Team("Italy"));
        sleep(10);
        final String argentinaAustraliaId = scoreboard.startNewMatch(new Team("Argentina"), new Team("Australia"));

        // When updating matches in the same order with specific score values, and getting ordered list
        scoreboard.updateScoreOfMatch(mexicoCanadaId, new Score(0, 5));
        scoreboard.updateScoreOfMatch(spainBrazilId, new Score(10, 2));
        scoreboard.updateScoreOfMatch(germanyFranceId, new Score(2, 2));
        scoreboard.updateScoreOfMatch(uruguayItalyId, new Score(6, 6));
        scoreboard.updateScoreOfMatch(argentinaAustraliaId, new Score(3, 1));

        final List<Match> orderedMatchList = scoreboard.getMatchesInOrder();

        // Then the list is in correct order
        assertEquals(uruguayItalyId, orderedMatchList.get(0).getMatchId());
        assertEquals(spainBrazilId, orderedMatchList.get(1).getMatchId());
        assertEquals(mexicoCanadaId, orderedMatchList.get(2).getMatchId());
        assertEquals(argentinaAustraliaId, orderedMatchList.get(3).getMatchId());
        assertEquals(germanyFranceId, orderedMatchList.get(4).getMatchId());
    }
}
