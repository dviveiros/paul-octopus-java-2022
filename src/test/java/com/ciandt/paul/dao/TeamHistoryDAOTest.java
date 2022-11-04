package com.ciandt.paul.dao;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.TeamHistory;
import com.ciandt.paul.utils.S3Utils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test Team History data access object
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TeamHistoryDAO.class, Config.class, S3Utils.class})
public class TeamHistoryDAOTest {

    @Autowired
    private TeamHistoryDAO teamHistoryDAO;

    @Autowired
    private Config config;

    @Before
    public void setUp() {
        config.setDebug("true");
    }

    @Test
    public void shouldFetchArgentinaAndAustralia() throws Exception {
        TeamHistory teamHistory = teamHistoryDAO.fetch("Argentina", "Australia");
        assertNotNull(teamHistory);
        assertEquals(Integer.valueOf(7), teamHistory.getGames());
    }

    @Test
    public void shouldFetchBrazilAndArgentina() throws Exception {
        TeamHistory teamHistory = teamHistoryDAO.fetch("Brazil", "Argentina");
        assertNotNull(teamHistory);
        assertEquals(Integer.valueOf(108), teamHistory.getGames());
    }

}
