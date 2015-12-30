package com.lightsperfections.slackrelay.dao.dynamodb;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;
import com.lightsperfections.slackrelay.beans.dynamodb.DynamoDBReadingPlanBookmark;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 12:01 PM
 */
public class DynamoDBReadingPlanBookmarkDao implements ReadingPlanBookmarkDao {

    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

    @Override
    public ReadingPlanBookmark findByUserName(String userName) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        return mapper.load(DynamoDBReadingPlanBookmark.class, userName);
    }

    @Override
    public void updateReadingPlanBookmark(ReadingPlanBookmark readingPlanBookmark) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(readingPlanBookmark);
    }

}
