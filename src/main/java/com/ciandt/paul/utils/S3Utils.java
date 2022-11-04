package com.ciandt.paul.utils;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Utility class to handle S3 operations
 */
@Service
public class S3Utils {

    private static Logger logger = LoggerFactory.getLogger(S3Utils.class.getName());

    private final AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard().withCredentials(
            new EnvironmentVariableCredentialsProvider()).build();

    /**
     * Read the content of a text file from S3
     */
    public String readFile(String bucket, String filename) throws IOException {

        StringBuffer content = new StringBuffer();
        String strLine = null;
        try {
            S3Object s3Object = amazonS3Client.getObject(bucket, filename);
            InputStreamReader streamReader = new InputStreamReader(s3Object.getObjectContent(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ( (strLine = reader.readLine()) != null ) {
                content.append(strLine + "\n");
            }
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
        return content.toString();
    }

}
