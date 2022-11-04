package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.TeamHistory;
import com.ciandt.paul.utils.S3Utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for reading team history data
 */
@Service
public class TeamHistoryDAO {

    private static Logger logger = LoggerFactory.getLogger(TeamHistoryDAO.class.getName());

    @Autowired
    private Config config;

    private static final S3Utils s3Utils = new S3Utils();

    private static Map<String, TeamHistory> cache;

    static {
        cache = new HashMap<>();
    }

    /**
     * Return the history for a specific team.
     */
    public TeamHistory fetch(String homeTeam, String awayTeam) throws IOException {

        if (cache.get(homeTeam + awayTeam) == null) {
            this.buildTeamHashMap();
        }

        return cache.get(homeTeam + awayTeam);
    }

    private void buildTeamHashMap() throws IOException {

        TeamHistory history = null;

        String historicalCSV = s3Utils.readFile( config.getDatasetBucket(), "historical_win-loose-draw_ratios.csv");
        Reader in = new StringReader(historicalCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            history = new TeamHistory();
            history.setHomeTeam(record.get("country1"));
            history.setAwayTeam(record.get("country2"));
            history.setGames(Integer.parseInt(record.get("games")));
            history.setWins(Double.parseDouble(record.get("wins")));
            history.setLooses(Double.parseDouble(record.get("looses")));
            history.setDraws(Double.parseDouble(record.get("draws")));
            cache.put(history.getHomeTeam() + history.getAwayTeam(), history);
        }
    }
}
