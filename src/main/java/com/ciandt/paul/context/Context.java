package com.ciandt.paul.context;

import com.ciandt.paul.entity.FifaRank;
import com.ciandt.paul.entity.HistoricalMatch;
import com.ciandt.paul.entity.TeamHistory;

import java.util.List;

/**
 * Context with data to be used to create a prediction
 */
public class Context {

    private TeamHistory teamsHistory;
    private FifaRank homeFifaRank;
    private FifaRank awayFifaRank;
    private List<HistoricalMatch> historicalMatchesHomeAway;
    private List<HistoricalMatch> historicalMatchesAwayHome;
    private ContextBuilder contextBuilder;

    /**
     * Constructor
     */
    Context() {
    }

    public TeamHistory getTeamsHistory() {
        return teamsHistory;
    }

    void setTeamsHistory(TeamHistory teamsHistory) {
        this.teamsHistory = teamsHistory;
    }

    public FifaRank getHomeFifaRank() {
        return homeFifaRank;
    }

    void setHomeFifaRank(FifaRank homeFifaRank) {
        this.homeFifaRank = homeFifaRank;
    }

    public FifaRank getAwayFifaRank() {
        return awayFifaRank;
    }

    void setAwayFifaRank(FifaRank awayFifaRank) {
        this.awayFifaRank = awayFifaRank;
    }

    public List<HistoricalMatch> getHistoricalMatchesHomeAway() {
        return historicalMatchesHomeAway;
    }

    public void setHistoricalMatchesHomeAway(List<HistoricalMatch> historicalMatchesHomeAway) {
        this.historicalMatchesHomeAway = historicalMatchesHomeAway;
    }

    public List<HistoricalMatch> getHistoricalMatchesAwayHome() {
        return historicalMatchesAwayHome;
    }

    public void setHistoricalMatchesAwayHome(List<HistoricalMatch> historicalMatchesAwayHome) {
        this.historicalMatchesAwayHome = historicalMatchesAwayHome;
    }
}
