package com.lightsperfections.slackrelay.beans.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.lightsperfections.slackrelay.beans.ReadingPlanBookmark;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Followed the example here:
 * https://github.com/awslabs/aws-dynamodb-examples/blob/master/src/main/java/com/amazonaws/codesamples/datamodeling/ObjectPersistenceCRUDExample.java
 *
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:43 AM
 */
@DynamoDBTable(tableName="ReadingPlanBookmark")
public class DynamoDBReadingPlanBookmark implements ReadingPlanBookmark {

    private String userName;
    private String planName;
    private List<Integer> trackIndexes;
    private LocalDateTime startDate;

    @Override
    @DynamoDBHashKey(attributeName="User")
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    @DynamoDBAttribute(attributeName="PlanName")
    public String getPlanName() {
        return planName;
    }

    @Override
    public void setPlanName(String planName) {
        this.planName = planName;
    }

    @Override
    @DynamoDBAttribute(attributeName="TrackIndexes")
    public List<Integer> getTrackIndexes() {
        return trackIndexes;
    }

    @Override
    public void setTrackIndexes(List<Integer> trackIndexes) {
        this.trackIndexes = trackIndexes;
    }

    @Override
    @DynamoDBAttribute(attributeName = "StartDate")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String toString() {
        return "ReadingPlan [userName=" + userName + "planName=" + planName + ", trackIndexes=" + trackIndexes
                + ", startDate=" + startDate + "]";
    }

}
