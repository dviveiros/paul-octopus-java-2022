package com.ciandt.paul.entity;

import org.apache.commons.csv.CSVRecord;

import java.util.List;

/**
 * Fifa rank for a specific year
 */
public class FifaRank {

    private Integer year;
    private Integer rank;
    private String teamCode;
    private String teamName;
    private Double points;

    public Integer getYear() {
        return year;
    }

    public Integer getRank() {
        return rank;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public Double getPoints() {
        return points;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "FifaRank{" +
                "year=" + year +
                ", rank=" + rank +
                ", teamCode='" + teamCode + '\'' +
                ", teamName='" + teamName + '\'' +
                ", points=" + points +
                '}';
    }
}
