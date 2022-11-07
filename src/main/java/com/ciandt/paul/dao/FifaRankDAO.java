package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.FifaRank;
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
import java.util.*;

/**
 * Class responsible for reading Fifa Rank data
 */
@Service
public class FifaRankDAO {

    private static Logger logger = LoggerFactory.getLogger(FifaRankDAO.class.getName());

    @Autowired
    private Config config;

    private static final S3Utils s3Utils = new S3Utils();
    private static final FifaRankComparator fifaRankComparator = new FifaRankComparator();

    private static Map<Integer, List<FifaRank>> cache;

    static {
        cache = new HashMap();
    }

    /**
     * Read the rank for a specific team and year
     */
    public FifaRank fetch(String teamName, Integer year) throws IOException, InterruptedException, DataNotAvailableException {
        //quick transformations on country names
        if (teamName.equals("Iran")) {
            teamName = "IR Iran";
        }
        if (teamName.equals("South Korea")) {
            teamName = "Korea Republic";
        }
        if (teamName.equals("North Korea")) {
            teamName = "Korea DPR";
        }
        if (teamName.equals("Ivory Coast")) {
            teamName = "Côte d'Ivoire";
        }
        if (teamName.equals("United States")) {
            teamName = "USA";
        }

        String month = "05";
        if (year == 2022) {
            month = "08";
        }
        String backupMonth = "07";

        List<FifaRank> fifaRankList = this.fetch(year, month);
        for (FifaRank fifaRank : fifaRankList) {
            if (teamName.equals(fifaRank.getTeamName())) {
                return fifaRank;
            }
        }

        cache.remove(year);
        fifaRankList = this.fetch(year, backupMonth);
        for (FifaRank fifaRank : fifaRankList) {
            if (teamName.equals(fifaRank.getTeamName())) {
                return fifaRank;
            }
        }

        throw new DataNotAvailableException("FifaRank for " + teamName, year);
    }

    /**
     * Read the rank for a specific year
     */
    public List<FifaRank> fetch(Integer year, String month) throws IOException, InterruptedException, DataNotAvailableException {

        //check the cache
        if (cache.get(year) != null) {
            return cache.get(year);
        }

        List<FifaRank> fifaRankList = new ArrayList<>();
        FifaRank fifaRank = null;

        String fifaRankingCSV = s3Utils.readFile( config.getDatasetBucket(), "ranking.csv");
        Reader in = new StringReader(fifaRankingCSV);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
        for (CSVRecord record : records) {
            if (record.get("rank_date").startsWith(year.toString() + "-"  + month)) {
                fifaRank = new FifaRank();
                fifaRank.setRank(Integer.valueOf(record.get("rank")));
                fifaRank.setTeamCode(record.get("country_abrv"));
                fifaRank.setTeamName(record.get("country_full"));
                fifaRank.setPoints(Double.parseDouble(record.get("total_points")));
                fifaRankList.add(fifaRank);
            }
        }

        Collections.sort(fifaRankList, fifaRankComparator);

        cache.put(year, fifaRankList);
        return fifaRankList;
    }
}
