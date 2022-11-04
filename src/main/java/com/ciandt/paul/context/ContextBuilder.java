package com.ciandt.paul.context;

import com.ciandt.paul.dao.DataNotAvailableException;
import com.ciandt.paul.dao.FifaRankDAO;
import com.ciandt.paul.dao.MatchDAO;
import com.ciandt.paul.dao.TeamHistoryDAO;
import com.ciandt.paul.entity.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Builds the context for the predictive algorithm
 */
@Service
public class ContextBuilder {

    @Autowired
    private FifaRankDAO fifaRankDAO;
    @Autowired
    private MatchDAO matchDAO;
    @Autowired
    private TeamHistoryDAO teamHistoryDAO;

    /**
     * Build the context
     */
    public Context build(Match match, Integer year) throws InterruptedException, DataNotAvailableException, IOException {
        Context context = new Context();

        //Fifa rank
        context.setHomeFifaRank(fifaRankDAO.fetch(match.getHomeTeam(), year));
        context.setAwayFifaRank(fifaRankDAO.fetch(match.getAwayTeam(), year));

        //Teams history
        context.setTeamsHistory(teamHistoryDAO.fetch(match.getHomeTeam(), match.getAwayTeam()));

        //Historical matches
        context.setHistoricalMatchesHomeAway(matchDAO.fetchHistoricalMatches(match.getHomeTeam(), match.getAwayTeam()));
        context.setHistoricalMatchesAwayHome(matchDAO.fetchHistoricalMatches(match.getAwayTeam(), match.getHomeTeam()));

        return context;
    }
}
