package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.FifaRank;
import com.ciandt.paul.utils.S3Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Test FifaRank data access object
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FifaRankDAO.class, Config.class, S3Utils.class})
public class FifaRankDAOTest {

    @Autowired
    private FifaRankDAO fifaRankDAO;

    @Autowired
    private Config config;

    @Before
    public void setUp() {
        config.setDebug("true");
    }

    @Test
    public void shouldFetchFifaRankFor2018() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2018, "05");
        assertNotNull(fifaRankList);
    }

    @Test
    public void shouldFetchFifaRankFor2022() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2018,"05");
        assertNotNull(fifaRankList);
    }

    @Test
    public void shouldFetchFifaRankFor2006() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2006,"05");
        assertNotNull(fifaRankList);
    }

    @Test
    public void shouldFetchFifaRankFor2010() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2010,"05");
        assertNotNull(fifaRankList);
    }

    @Test
    public void shouldFetchFifaRankFor2014() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2014,"05");
        assertNotNull(fifaRankList);
    }

    @Test
    public void shouldFetchBrazilRankIn2006() throws Exception {
        FifaRank fifaRank = fifaRankDAO.fetch( "Brazil", 2006 );
        assertNotNull(fifaRank);
    }

}
