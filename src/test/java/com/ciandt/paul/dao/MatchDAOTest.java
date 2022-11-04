package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.HistoricalMatch;
import com.ciandt.paul.entity.Match;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Test Match data access object
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MatchDAO.class, Config.class})
public class MatchDAOTest {

    @Autowired
    private MatchDAO matchDAO;

    @Autowired
    private Config config;

    @Before
    public void setUp() {
        config.setDebug("true");
    }

    @Test
    public void shouldFetch2022Matches() throws Exception {
        List<Match> matchList = matchDAO.fetch(2022);
        assertNotNull(matchList);
        assertEquals(48, matchList.size());
    }

    @Test
    public void shouldFetch2018Matches() throws Exception {
        List<Match> matchList = matchDAO.fetch(2018);
        assertNotNull(matchList);
        assertEquals("Russia", matchList.get(0).getHomeTeam());
        assertEquals("Saudi Arabia", matchList.get(0).getAwayTeam());
        assertEquals("England", matchList.get(47).getHomeTeam());
        assertEquals("Belgium", matchList.get(47).getAwayTeam());
        assertEquals(48, matchList.size());
    }

    @Test
    public void shouldFetchBrazilAndArgentinaHistoricalMatches() throws Exception {
        List<HistoricalMatch> historicalMatches = matchDAO.fetchHistoricalMatches( "Brazil", "Argentina");
        assertNotNull(historicalMatches);
        assertTrue( "Size must be higher than zero", historicalMatches.size() > 0);
    }
}
