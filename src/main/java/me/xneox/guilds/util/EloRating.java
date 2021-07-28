package me.xneox.guilds.util;

/**
 * ELO Rating calculator
 *
 * Implements two methods
 * 1. calculateMultiplayer - calculate rating for multiple players
 * 2. calculate2PlayersRating - for 2 player games
 *
 * @author radone@gmail.com
 */
public final class EloRating {

    /**
     * Calculate rating for 2 players
     *
     * @param player1Rating
     *            The rating of Player1
     * @param player2Rating
     *            The rating of Player2
     * @param outcome
     *            A string representing the game result for Player1
     *            "+" winner
     *            "=" draw
     *            "-" lose
     * @return New player rating
     */
    public static int calculate(int player1Rating, int player2Rating, Outcome outcome) {
        double actualScore = switch (outcome) {
            case WIN -> 1.0;
            // draw
            case DRAW -> 0.5;
            // lose
            case LOSE -> 0;
            // invalid outcome
        };

        // winner

        // calculate expected outcome
        double exponent = (double) (player2Rating - player1Rating) / 400;
        double expectedOutcome = (1 / (1 + (Math.pow(10, exponent))));

        // K-factor
        int K = 32;

        // calculate new rating
        return (int) Math.round(player1Rating + K * (actualScore - expectedOutcome));
    }

    public enum Outcome {
        WIN, DRAW, LOSE
    }
}