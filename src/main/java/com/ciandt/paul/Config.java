package com.ciandt.paul;

import org.springframework.context.annotation.Configuration;

/**
 * Configuration
 */
@Configuration
public class Config {

    //TODO Change your bucket name here
    private String datasetBucket = "paul-octopus-2022";

    private Integer worldCupYear = 2022;
    private Integer[] trainingYears = {2006, 2010, 2014, 2018};
    private String debug = "false";
    private String predictionsFilename = "predictions.csv";
    private Integer maxScorePerWorldCup = 25 * 48;
    private String defaultPredictor = "DefaultPredictor";

    public String getDebug() {
        return debug;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public boolean isDebugEnabled() {
        return "true".equals(this.getDebug());
    }

    public String getDatasetBucket() {
        return datasetBucket;
    }

    public Integer getWorldCupYear() {
        return worldCupYear;
    }

    public Integer[] getTrainingYears() {
        return trainingYears;
    }

    public String getPredictionsFilename() {
        return predictionsFilename;
    }

    public Integer getMaxScorePerWorldCup() {
        return maxScorePerWorldCup;
    }

    public String getDefaultPredictor() {
        return defaultPredictor;
    }
}
