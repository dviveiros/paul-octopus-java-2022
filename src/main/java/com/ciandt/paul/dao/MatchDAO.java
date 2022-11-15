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

    private static Map<String, List<HistoricalMatch>> historicalCache;
    private static Map<Integer, List<HistoricalMatch>> resultsCache;
    private static Map<Integer, List<Match>> matchesCache;

    static {
        matchesCache = new HashMap<>();
        historicalCache = new HashMap<>();
        resultsCache = new HashMap<>();
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

            List<Match> matchList;

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
    public List<HistoricalMatch> fetchResults(Integer year) throws IOException {

        if (resultsCache.get(year) != null) {
            return resultsCache.get(year);
        }

        String matchesScheduleCSV = s3Utils.readFile( config.getDatasetBucket(), "historical-results.csv");
        Reader in = new StringReader(matchesScheduleCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        List<HistoricalMatch> matchList = new ArrayList<>();
        HistoricalMatch historicalMatch = null;
        int count = 0;
        for (CSVRecord record : records) {
            if (!record.get("tournament").equals("FIFA World Cup")) {
                continue;
            }
            if (!record.get("date").startsWith(year.toString())) {
                continue;
            }
            historicalMatch = new HistoricalMatch();
            historicalMatch.setDate(record.get("date"));
            historicalMatch.setHomeTeam(record.get("home_team"));
            historicalMatch.setAwayTeam(record.get("away_team"));
            historicalMatch.setHomeScore(Integer.valueOf(record.get("home_score")));
            historicalMatch.setAwayScore(Integer.valueOf(record.get("away_score")));
            historicalMatch.setTournament(record.get("tournament"));
            historicalMatch.setCity(record.get("city"));
            historicalMatch.setCountry(record.get("country"));
            historicalMatch.setNeutral(record.get("neutral").equals("TRUE"));
            matchList.add(historicalMatch);
            count++;
            if (count == 48) {
                //just group phase, first 48 games
                break;
            }
        }
        resultsCache.put(year, matchList);
        return matchList;
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
     */
    public List<HistoricalMatch> fetchHistoricalMatches(String homeTeam, String awayTeam, Integer year) throws IOException {

        if (historicalCache.get(homeTeam+awayTeam) != null) {
            return historicalCache.get(homeTeam+awayTeam);
        }

        Integer minYear = 1990;

        String matchesScheduleCSV = s3Utils.readFile( config.getDatasetBucket(), "historical-results.csv");
        Reader in = new StringReader(matchesScheduleCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        List<HistoricalMatch> historicalMatches = new ArrayList<>();
        HistoricalMatch historicalMatch = null;
        for (CSVRecord record : records) {
            String strRecordYear = record.get("date").substring(0,4);
            Integer recordYear = Integer.parseInt(strRecordYear);
            if (recordYear < minYear) {
                continue;
            }
            if (recordYear >= year ) {
                continue;
            }
            if ((record.get("home_score").length() == 0) || (record.get("away_score").length() == 0)) {
                continue;
            }

            if (homeTeam.equals(record.get("home_team")) && awayTeam.equals(record.get("away_team"))) {
                historicalMatch = new HistoricalMatch();
                historicalMatch.setDate(record.get("date"));
                historicalMatch.setHomeTeam(record.get("home_team"));
                historicalMatch.setAwayTeam(record.get("away_team"));
                historicalMatch.setHomeScore(Integer.valueOf(record.get("home_score")));
                historicalMatch.setAwayScore(Integer.valueOf(record.get("away_score")));
                historicalMatch.setTournament(record.get("tournament"));
                historicalMatch.setCity(record.get("city"));
                historicalMatch.setCountry(record.get("country"));
                historicalMatch.setNeutral(record.get("neutral").equals("TRUE"));
                historicalMatches.add(historicalMatch);
            }
        }

        historicalCache.put(homeTeam+awayTeam, historicalMatches);

        return historicalMatches;
    }

}

