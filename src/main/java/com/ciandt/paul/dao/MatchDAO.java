package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.HistoricalMatch;
import com.ciandt.paul.entity.Match;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class responsible for reading matches data
 */
@Service
public class MatchDAO {

    private static Logger logger = LoggerFactory.getLogger(MatchDAO.class.getName());

    @Autowired
    private Config config;

    private static final S3Utils s3Utils = new S3Utils();

    private static List<HistoricalMatch> allMatches;
    private static Map<Integer, List<Match>> matchesCache;

    static {
        matchesCache = new HashMap<>();
    }

    /**
     * Return the matches to be predicted for a specific year
     */
    public List<Match> fetch(Integer year) throws IOException, InterruptedException, DataNotAvailableException {
        if (matchesCache.get(year) != null) {
            return matchesCache.get(year);
        } else {
            if (config.isDebugEnabled()) {
                logger.debug("Loading " + year + " matches");
            }

            List<Match> matchList = new ArrayList<>();
            Match match = null;
            String query = null;

            if (year == 2022) {
                //load matches from this year
                matchList = this.load2022Matches();
            } else {
                //load historical matches
                matchList = this.loadMatches(year);
            }

            matchesCache.put(year, matchList);

            if (config.isDebugEnabled()) {
                logger.debug("Data loaded. Found " + matchList.size() + " games");
            }

            if (matchList.size() == 0) {
                throw new DataNotAvailableException("Match", 2018);
            }

            return matchList;
        }
    }

    /**
     * Load matches from 2022
     */
    private List<Match> load2022Matches() throws IOException {
        String matchesScheduleCSV = s3Utils.readFile( config.getDatasetBucket(), "matches-schedule.csv");
        Reader in = new StringReader(matchesScheduleCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        List<Match> matchList = new ArrayList<>();
        Match match = null;
        for (CSVRecord record : records) {
            match = new Match();
            match.setYear(2022);
            match.setHomeTeam(record.get("country1"));
            match.setAwayTeam(record.get("country2"));
            matchList.add(match);
        }
        return matchList;
    }

    /**
     * Load matches from 2022
     */
    private List<Match> loadMatches(Integer year) throws IOException {
        String matchesScheduleCSV = s3Utils.readFile( config.getDatasetBucket(), "historical-results.csv");
        Reader in = new StringReader(matchesScheduleCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        List<Match> matchList = new ArrayList<>();
        Match match = null;
        int count = 0;
        for (CSVRecord record : records) {
            if (!record.get("tournament").equals("FIFA World Cup")) {
                continue;
            }
            if (!record.get("date").startsWith(year.toString())) {
                continue;
            }
            match = new Match();
            match.setYear(year);
            match.setHomeTeam(record.get("home_team"));
            match.setAwayTeam(record.get("away_team"));
            matchList.add(match);
            count++;
            if (count == 48) {
                //just group phase, first 48 games
                break;
            }
        }
        return matchList;
    }

    /**
     * Return history data prior to this world cup
     *
     * @param year This method will return data prior to this year
     * @return List of matches prior to the year ordered by year desc
     */
    public List<HistoricalMatch> fetchHistoryData(Integer year) throws IOException, InterruptedException, DataNotAvailableException {

        /*
        this.loadHistoricalMatches();

        List<HistoricalMatch> matchList = new ArrayList<>();
        for (HistoricalMatch match : allMatches) {
            if (match.getYear() >= year) {
                continue;
            } else {
                matchList.add(match);
            }
        }

        if ((matchList.size() == 0) && (year > 1930)) {
            throw new DataNotAvailableException("HistoricalMatch", year);
        }

        return matchList;

         */
        return null;
    }

    /**
     * Return the actual results for a specific year
     *
     * @param year Year for the results
     * @return Results for the year
     */
    public List<HistoricalMatch> fetchResults(Integer year)
            throws IOException, InterruptedException, DataNotAvailableException {

        /*
        this.loadHistoricalMatches();

        List<HistoricalMatch> matchList = new ArrayList<>();
        for (HistoricalMatch match : allMatches) {
            if (match.getYear().equals(year)) {
                matchList.add(match);
            }
        }

        if (matchList.size() == 0) {
            throw new DataNotAvailableException("HistoricalMatch", year);
        }

        return matchList;

         */
        return null;
    }



    /**
     * Load historical matches
     */
    private void loadHistoricalMatches() throws IOException, InterruptedException {
        /*
        //check the cache
        if (allMatches == null) {
            allMatches = new ArrayList<>();

            if (config.isDebugEnabled()) {
                logger.debug("Loading history matches data");
            }


            HistoricalMatch match = null;
            String query = "SELECT * FROM paul_the_octopus_dataset.matches_history order by year desc";
            List<List<String>> queryResult = bigQueryUtils.executeQuery(query);

            for (List<String> row : queryResult) {
                match = new HistoricalMatch(row);
                allMatches.add(match);
            }

            if (config.isDebugEnabled()) {
                logger.debug("Data loaded. Found " + allMatches.size() + " games");
            }
        }

         */
    }

}

