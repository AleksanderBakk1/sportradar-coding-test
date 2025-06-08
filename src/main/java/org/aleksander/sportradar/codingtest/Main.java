package org.aleksander.sportradar.codingtest;

import org.aleksander.sportradar.codingtest.objects.Match;
import org.aleksander.sportradar.codingtest.objects.Score;
import org.aleksander.sportradar.codingtest.objects.Scoreboard;
import org.aleksander.sportradar.codingtest.objects.Team;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Sportradar coding test!");
        sportradarProvidedExample();
    }

    public static void sportradarProvidedExample() {
        // Create matches in order provided
        final Scoreboard scoreboard = new Scoreboard();
        final String mexicoCanadaId = scoreboard.startNewMatch(new Team("Mexico"), new Team("Canada"));
        final String spainBrazilId = scoreboard.startNewMatch(new Team("Spain"), new Team("Brazil"));
        final String germanyFranceId = scoreboard.startNewMatch(new Team("Germany"), new Team("France"));
        final String uruguayItalyId = scoreboard.startNewMatch(new Team("Uruguay"), new Team("Italy"));
        final String argentinaAustraliaId = scoreboard.startNewMatch(new Team("Argentina"), new Team("Australia"));

        // Update matches in order provided with scores provided
        scoreboard.updateScoreOfMatch(mexicoCanadaId, new Score(0, 5));
        scoreboard.updateScoreOfMatch(spainBrazilId, new Score(10, 2));
        scoreboard.updateScoreOfMatch(germanyFranceId, new Score(2, 2));
        scoreboard.updateScoreOfMatch(uruguayItalyId, new Score(6, 6));
        scoreboard.updateScoreOfMatch(argentinaAustraliaId, new Score(3, 1));

        // Print out list in order returned
        final List<Match> listOfMatches = scoreboard.getMatchesInOrder();
        printMatchesToConsole(listOfMatches);
    }

    private static void printMatchesToConsole(final List<Match> listOfMatches) {
        System.out.println("Here is a list of current matches: ");
        for (final Match match : listOfMatches) {
            System.out.println(match.toString());
        }
    }
}
