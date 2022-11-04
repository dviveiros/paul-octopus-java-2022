package com.ciandt.paul.utils;

import com.ciandt.paul.Config;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

/**
 * Test S3 services
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {S3Utils.class, Config.class})
public class S3UtilsTest {

    @Autowired
    private S3Utils s3Utils;

    @Autowired
    private Config config;

    @Before
    public void setUp() {
        config.setDebug("true");
    }

    /**
     * Test the read method
     */
    @Test
    public void shouldReadFileFromS3() throws Exception {
        String strContent = s3Utils.readFile(config.getDatasetBucket(), "historical-results.csv");
        assertNotNull(strContent);
    }

}
