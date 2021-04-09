package com.example.server.lambda.statusUpdateLambdas;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.server.service.FollowerService;
import com.example.shared.model.service.request.NewStatusRequest;
import com.example.shared.model.service.response.FollowerResponse;

import java.util.Map;

public class BatchFeedUpdater implements RequestHandler<SQSEvent, Void> {

    private static final String followerAliasesAttribute = "FollowerAliases";
    private static final String postedStatusAttribute = "PostedStatus";

    @Override
    public Void handleRequest(SQSEvent input, Context context) {
        for (SQSEvent.SQSMessage msg : input.getRecords()) {
            Map attributes = msg.getAttributes();
            String followerResponseStr = extractAttribute(attributes.get(followerAliasesAttribute));
            String newStatusRequestStr = extractAttribute(attributes.get(postedStatusAttribute));
            FollowerResponse followerResponse = convertToRequestObject(followerResponseStr, FollowerResponse.class);
            NewStatusRequest newStatusRequest = convertToRequestObject(newStatusRequestStr, NewStatusRequest.class);
            handleBatch(newStatusRequest, followerResponse);
        }

        //Uses FollowerService(?) to get batches of followers whose feeds are to be updated
        return null;
    }

    private <T> T convertToRequestObject(String objectString, Class<T> toClass) {
        return JsonSerializer.deserialize(objectString, toClass);
    }

    private String extractAttribute(Object tempMessageObject) {
        MessageAttributeValue tempMessageAttributeValue = (MessageAttributeValue) tempMessageObject;
        return tempMessageAttributeValue.getStringValue();
    }

    public void handleBatch(NewStatusRequest newStatusRequest, FollowerResponse followerResponse) { // Uses an input Status or an input NewStatusRequest
        //From parameter info, discerns user
        String correspondingUserAlias = newStatusRequest.getUserAlias();

        FollowerService followerService = new FollowerService();
        // TODO: follow along what Blake did below to
        //  get each batch of followers, send request to queue with corresponding info (batch of followers (25) and the status)
       /* try {
            //return followerService.gets(newStatusRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }*/
    }


    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withRegion("us-east-1")
            .build();
    private static DynamoDB dynamoDB = new DynamoDB(client);



    //Generating Data to DB
    public static void main(String[] args) {

        String dbPrimaryKey = "Alias";
        TableWriteItems items = new TableWriteItems("Users");

        for(int i =0 ; i < 10000; i ++){
            String userName = "@user"+ i;
            String firstName = "@user";
            String lastName = String.valueOf(i);
            String profileImage = "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.pngitem.com%2Fmiddle%2FwomThJ_ash-ketchum-hd-png-download%2F&psig=AOvVaw2h43_Bi3x5gdd1y2tRmAhq&ust=1616605412770000&source=images&cd=vfe&ved=0CAIQjRxqFwoTCJjVytTyxu8CFQAAAAAdAAAAABAL";
            Item item = new Item().withPrimaryKey(dbPrimaryKey, userName).withString("FirstName", firstName).withString("ImageUrl", profileImage).withString("LastName", lastName);
            items.addItemToPut(item);

             if(i - 1 % 25 == 0){
            //then add a list for the Batch
                 loopBatchWriter(items);
                 items = new TableWriteItems("Users");
             }
        }

        if(items.getItemsToPut() !=null && items.getItemsToPut().size() == 25){
            loopBatchWriter(items);
            items = new TableWriteItems("Users");
        }
        if (items.getItemsToPut() != null && items.getItemsToPut().size() > 0) {
            loopBatchWriter(items);
        }

        //Thgis will be for Followers
        for(int i =0; i < 10000; i++){
            //create a test user have 100000 followers
            String userName = "@user"+ i;

            if(i - 1 % 25 == 0){
                //then add a list for the Batch
            }
        }
    }
}
