package com.lightsperfections.slackrelay.dao.dynamodb;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.lightsperfections.slackrelay.beans.logos.History;
import com.lightsperfections.slackrelay.beans.logos.dynamodb.DynamoDBHistory;
import com.lightsperfections.slackrelay.dao.HistoryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 12:01 PM
 */
public class DynamoDBHistoryDao implements HistoryDao {

    private Logger logger = LoggerFactory.getLogger("DynamoDBHistoryDao");

    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
    static{
        client.setRegion(Region.getRegion(Regions.US_WEST_2));
    }

    @Override
    public void createHistory(String userName, LocalDateTime dateTime, String reference) {
        History history = new DynamoDBHistory(userName, dateTime, reference);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(history);
    }

}
