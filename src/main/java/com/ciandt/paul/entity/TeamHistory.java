package com.ciandt.paul.entity;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

public class TeamHistory {

    private String homeTeam;
    private String awayTeam;
    private Integer games;
    private Double wins;
    private Double looses;
    private Double draws;

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Integer getGames() {
        return games;
    }

    public void setGames(Integer games) {
        this.games = games;
    }

    public Double getWins() {
        return wins;
    }

    public void setWins(Double wins) {
        this.wins = wins;
    }

    public Double getLooses() {
        return looses;
    }

    public void setLooses(Double looses) {
        this.looses = looses;
    }

    public Double getDraws() {
        return draws;
    }

    public void setDraws(Double draws) {
        this.draws = draws;
    }

    @Override
    public String toString() {
        return "TeamHistory{" +
                "homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", games=" + games +
                ", wins=" + wins +
                ", looses=" + looses +
                ", draw=" + draws +
                '}';
    }
}
