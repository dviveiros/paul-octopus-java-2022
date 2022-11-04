package com.ciandt.paul.entity;

import java.util.List;

/**
 * World cup match
 */
public class Match {

    private Integer year;
    private String homeTeam;
    private String awayTeam;



    public Integer getYear() {
        return year;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Override
    public String toString() {
        return "Match{" +
                "year=" + year +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam +
                '}';
    }
}
