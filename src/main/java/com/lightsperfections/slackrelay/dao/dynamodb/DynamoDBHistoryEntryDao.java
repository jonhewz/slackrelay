package com.lightsperfections.slackrelay.dao.dynamodb;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.lightsperfections.slackrelay.beans.logos.HistoryEntry;
import com.lightsperfections.slackrelay.beans.logos.dynamodb.DynamoDBHistoryEntry;
import com.lightsperfections.slackrelay.dao.HistoryEntryDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 12:01 PM
 */
public class DynamoDBHistoryEntryDao implements HistoryEntryDao {

    private Logger logger = LoggerFactory.getLogger("DynamoDBHistoryDao");

    static AmazonDynamoDBClient client = new AmazonDynamoDBClient(new ProfileCredentialsProvider());
    static{
        client.setRegion(Region.getRegion(Regions.US_WEST_2));
    }


    @Override
    public void createHistoryEntryForUserName(String userName, LocalDateTime entryTime, String reference) {
        new DynamoDBMapper(client).save(new DynamoDBHistoryEntry(userName, entryTime, reference));
    }


    /**
     *
     * @param userName
     * @return list of HistoryEntry objects, no guarantee of sorting
     */
    @Override
    public List<? extends HistoryEntry> findHistoryEntriesByUserName(String userName) {

        DynamoDBMapper mapper = new DynamoDBMapper(client);

        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":v1", new AttributeValue().withS(userName));

        DynamoDBQueryExpression<DynamoDBHistoryEntry> queryExpression = new DynamoDBQueryExpression<DynamoDBHistoryEntry>()
                //.withKeyConditionExpression("UserName = :v1")
                .withIndexName("UserName-index")
                .withKeyConditionExpression("UserName = :v1")
                .withExpressionAttributeValues(eav);

        List<DynamoDBHistoryEntry> historyEntries =
                mapper.query(DynamoDBHistoryEntry.class, queryExpression);

        return historyEntries;
    }

}
