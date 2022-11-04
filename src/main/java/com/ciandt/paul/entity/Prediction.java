package com.ciandt.paul.entity;

/**
 * A prediction for a World Cup 2018 game
 */
public class Prediction {

    private Match match;
    private Integer homeScore;
    private Integer awayScore;

    /**
     * Constructor
     *
     * @param match A match to which you wanna create a prediction for
     */
    public Prediction(Match match) {
        this.match = match;
    }

    /**
     * Constructor
     *
     * @return
     */
    public Prediction(String homeTeam, String awayTeam, Integer homeScore, Integer awayScore, Integer year) {
        this.match = new Match();
        this.match.setHomeTeam(homeTeam);
        this.match.setAwayTeam(awayTeam);
        this.match.setYear(year);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Integer getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(Integer homeScore) {
        this.homeScore = homeScore;
    }

    public Integer getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(Integer awayScore) {
        this.awayScore = awayScore;
    }

    @Override
    public String toString() {
        return this.getMatch().getHomeTeam() + " " + this.getHomeScore() + " x "
                + this.getAwayScore() + " " + this.getMatch().getAwayTeam();
    }
}
