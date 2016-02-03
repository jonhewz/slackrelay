package com.lightsperfections.slackrelay.beans.logos.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.lightsperfections.slackrelay.beans.logos.History;
import com.lightsperfections.slackrelay.beans.logos.ReadingPlanBookmark;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: jhughes
 * Date: 12/30/15
 * Time: 9:43 AM
 */
@DynamoDBTable(tableName="History")
public class DynamoDBHistory implements History {

    private String userName;
    private LocalDateTime dateTime;
    private String reference;

    public DynamoDBHistory (String userName, LocalDateTime dateTime, String reference) {
        this.userName = userName;
        this.dateTime = dateTime;
        this.reference = reference;
    }

    @Override
    @DynamoDBHashKey(attributeName="UserName")
    public String getUserName() {
        return userName;
    }

    @Override
    @DynamoDBAttribute(attributeName="Reference")
    public String getReference() {
        return reference;
    }


    @Override
    @DynamoDBAttribute(attributeName = "DateTime")
    @DynamoDBMarshalling(marshallerClass = LocalDateTimeConverter.class)
    public LocalDateTime getDateTime() {
        return dateTime;
    }


    public String toString() {
        return dateTime + " " + reference;
    }

    static public class LocalDateTimeConverter implements DynamoDBMarshaller<LocalDateTime> {

        @Override
        public String marshall(LocalDateTime time) {
            return time.toString();
        }

        @Override
        public LocalDateTime unmarshall(Class<LocalDateTime> dimensionType, String stringValue) {
            return LocalDateTime.parse(stringValue);
        }
    }

}