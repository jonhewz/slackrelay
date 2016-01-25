package com.lightsperfections.slackrelay.dao.dynamodb;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;
import com.lightsperfections.slackrelay.beans.dynamodb.DynamoDBReadingPlanBookmark;
import com.lightsperfections.slackrelay.dao.ReadingPlanBookmarkDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 12:01 PM
 */
public class DynamoDBReadingPlanBookmarkDao implements ReadingPlanBookmarkDao {

    private Logger logger = LoggerFactory.getLogger("DynamoDBReadingPlanBookmarkDao");

    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());

    @Override
    public ReadingPlanBookmark findByUserName(String userName) {
        long startTime = System.currentTimeMillis();

        DynamoDBMapper mapper = new DynamoDBMapper(client);
        ReadingPlanBookmark readingPlanBookmark = mapper.load(DynamoDBReadingPlanBookmark.class, userName);

        logger.debug("findByUserName(" + userName + ") took " + (System.currentTimeMillis() - startTime) + "ms.");
        return readingPlanBookmark;
    }

    @Override
    public void updateReadingPlanBookmark(ReadingPlanBookmark readingPlanBookmark) {
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        mapper.save(readingPlanBookmark);
    }

    @Override
    public ReadingPlanBookmark createReadingPlanBookmark(String userName, String planName, Integer currentTrack,
                                                         List<Integer> trackIndexes, LocalDateTime startDate) {
        ReadingPlanBookmark readingPlanBookmark = new DynamoDBReadingPlanBookmark();
        readingPlanBookmark.setUserName(userName);
        readingPlanBookmark.setCurrentTrack(currentTrack);
        readingPlanBookmark.setPlanName(planName);
        readingPlanBookmark.setTrackIndexes(trackIndexes);
        readingPlanBookmark.setStartDate(startDate);

        updateReadingPlanBookmark(readingPlanBookmark);
        return readingPlanBookmark;
    }
}
