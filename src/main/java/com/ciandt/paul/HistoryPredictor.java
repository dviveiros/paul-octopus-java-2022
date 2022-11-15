package com.ciandt.paul;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;
import com.ciandt.paul.entity.TeamHistory;

public class HistoryPredictor implements Predictor {
    @Override
    public Prediction predict(Match match, Context context) {
        Prediction prediction = new Prediction(match);

        TeamHistory teamHistory = context.getTeamsHistory();
        if (teamHistory == null) {
            prediction.setHomeScore(0);
            prediction.setAwayScore(0);
        } else {
            Double homeOdds = teamHistory.getWins();
            Double awayOdds = teamHistory.getLooses();
            Double drawOdds = teamHistory.getDraws();

            if (drawOdds > 0.34) {
                prediction.setHomeScore(0);
                prediction.setAwayScore(0);
            } else if (homeOdds > awayOdds) {
                prediction.setHomeScore(1);
                prediction.setAwayScore(0);
            } else {
                prediction.setHomeScore(0);
                prediction.setAwayScore(1);
            }
        }

        return prediction;
    }
}
