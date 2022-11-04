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

import static org.junit.Assert.assertEquals;
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
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2018);
        assertNotNull(fifaRankList);
        assertEquals(206, fifaRankList.size());
    }

    @Test
    public void shouldFetchFifaRankFor2022() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2018);
        assertNotNull(fifaRankList);
        assertEquals(206, fifaRankList.size());
    }

    @Test
    public void shouldFetchFifaRankFor2006() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2006);
        assertNotNull(fifaRankList);
        assertEquals(204, fifaRankList.size());

        System.out.println(fifaRankList.get(0));

        assertEquals("BRA", fifaRankList.get(0).getTeamCode());
        assertEquals("Brazil", fifaRankList.get(0).getTeamName());
        assertEquals(Integer.valueOf(827), fifaRankList.get(0).getPoints());

        assertEquals("ASA", fifaRankList.get(203).getTeamCode());
        assertEquals("American Samoa", fifaRankList.get(203).getTeamName());
        assertEquals(Integer.valueOf(14), fifaRankList.get(203).getPoints());
    }

    @Test
    public void shouldFetchFifaRankFor2010() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2010);
        assertNotNull(fifaRankList);
        assertEquals(201, fifaRankList.size());

        assertEquals("BRA", fifaRankList.get(0).getTeamCode());
        assertEquals("Brazil", fifaRankList.get(0).getTeamName());
        assertEquals(Integer.valueOf(1611), fifaRankList.get(0).getPoints());
    }

    @Test
    public void shouldFetchFifaRankFor2014() throws Exception {
        List<FifaRank> fifaRankList = fifaRankDAO.fetch(2014);
        assertNotNull(fifaRankList);
        assertEquals(206, fifaRankList.size());

        assertEquals("ESP", fifaRankList.get(0).getTeamCode());
        assertEquals("Spain", fifaRankList.get(0).getTeamName());
        assertEquals(Integer.valueOf(1460), fifaRankList.get(0).getPoints());
    }

}
