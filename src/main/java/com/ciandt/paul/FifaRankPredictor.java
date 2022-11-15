package com.ciandt.paul;

import com.ciandt.paul.context.Context;
import com.ciandt.paul.entity.FifaRank;
import com.ciandt.paul.entity.Match;
import com.ciandt.paul.entity.Prediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FifaRankPredictor implements Predictor {

    private static final Logger logger = LoggerFactory.getLogger(FifaRankPredictor.class.getName());

    @Override
    public Prediction predict(Match match, Context context) {

        FifaRank homeRank = context.getHomeFifaRank();
        FifaRank awayRank = context.getAwayFifaRank();
        Prediction prediction = new Prediction(match);

//        logger.debug("FifaRank Prediction: " + match.getHomeTeam() + " (" + homeRank.getRank()
//                + ") x " + match.getAwayTeam() + " (" + awayRank.getRank() + ")");


        if (homeRank.getRank() < awayRank.getRank()) {
            prediction.setHomeScore(1);
            prediction.setAwayScore(0);
        } else if (homeRank.getRank() > awayRank.getRank()) {
            prediction.setHomeScore(0);
            prediction.setAwayScore(1);
        } else {
            prediction.setHomeScore(0);
            prediction.setAwayScore(0);
        }

        return prediction;
    }
}
