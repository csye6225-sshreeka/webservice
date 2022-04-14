package com.springboot.springbootapp.service;



//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.AWSCredentialsProvider;
//import com.amazonaws.auth.InstanceProfileCredentialsProvider;
//import com.amazonaws.regions.Region;
//import com.amazonaws.regions.Regions;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3ClientBuilder;
//import com.amazonaws.services.sns.AmazonSNS;
//import com.amazonaws.services.sns.AmazonSNSClientBuilder;
//import com.amazonaws.services.sns.model.*;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.regions.Region;

import java.time.Instant;
import java.util.*;


@Service
public class EmailSNSService {
    SnsClient snsClient;
    @Value("${aws.sns.topic.MailNotification.ARN}")
    String snsTopicARN;
    private final static Logger logger = LoggerFactory.getLogger(EmailSNSService.class);
    public void postToTopic(String recipientEmail, String requestType) {
        try {
            System.out.println("in sns postToTopic");
            Random rand = new Random();
            int randomInt = rand.nextInt(10000);
            String snsMessage = requestType + "|" + recipientEmail + "|"+ randomInt;
            System.out.println("message generated, now publishing");
            PublishRequest request = PublishRequest.builder()
                    .message(snsMessage)
                    .topicArn(snsTopicARN)
                    .build();
            if (snsClient == null) {
                System.out.println("snsClient object is still   ..........null");
            }
            SnsClient snsClient = SnsClient.builder()
                    .region(Region.US_EAST_1)
                    .build();
            PublishResponse result = snsClient.publish(request);
            System.out.println("Publishing done");
            System.out.println("Message " + result.messageId() + "is successfully published to SNS Topic 'Notification_Email'");
            logger.info("Message " + result.messageId() + " is successfully published to SNS Topic 'Notification_Email'.");
            logger.info(snsMessage);
//            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
//            DynamoDB dynamoDb = new DynamoDB(client);
//            Table table = dynamoDb.getTable("UsernameTokenTable");
////
//            long now = Instant.now().getEpochSecond(); // unix time
//            long ttl = 300; // 24 hours in sec
//
//            Item item = new Item()
//                    .withPrimaryKey("emailID", recipientEmail)
//                    .with("TimeToLive",now+ttl)
//                    .with("Token",randomInt);
//           // client.updateTimeToLive(add5Min());
//            PutItemOutcome outcome = table.putItem(item);
        } catch (SnsException e) {
            System.out.println("sns exception: " + e.getMessage());
            e.printStackTrace();
            logger.error("SNS Exception Warning - " + e.getMessage());
        }
    }
    public static String add5Min() {
        Date date1 = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        c.add(Calendar.MINUTE, 5); //adds five minute to the calendar's date
        //System.out.println(date1.getTime()); //current time
        return c.getTime().getTime() + ""; //current time + 5 minutes
    }

}

