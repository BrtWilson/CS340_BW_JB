package com.example.server.dao;

import com.example.server.dao.dbstrategies.DynamoDBStrategy;
import com.example.shared.model.domain.AuthToken;
import com.example.shared.model.domain.User;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.BasicResponse;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.RegisterResponse;
import com.example.shared.model.service.response.UserResponse;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class AuthTableDAO {
    //DummyDataProvider dataProvider = DummyDataProvider.getInstance();

    private static final String tableName = "AuthTokens";
    private static final String keyAttribute = "Alias";
    private static final String secondaryKey = "Token";
    private static final String additionalAttribute = "TimeStamp";

    private static final long inactivityTimeCap = TimeUnit.MINUTES.toMillis(5);

    /**
     * Checks the AuthTokens table for whether an AuthToken is still valid or not
     * @param authToken Used to identify corresponding item in Database
     * @return boolean of whether the authToken is still valid
     */
    public Boolean getAuthorized(AuthToken authToken) {
        String formerTimeStamp = getDatabaseInteractor().getBasicStringAttributeFromDualKey(tableName, keyAttribute, authToken.getUserName(),secondaryKey, authToken.getToken(), additionalAttribute);
        try {
            if (checkTimePassedValid(formerTimeStamp)) {
                //update time
                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                try {
                    getDatabaseInteractor().updateItemStringAttributeFromDualKey(tableName, keyAttribute, authToken.getUserName(), secondaryKey, authToken.getToken(), additionalAttribute, currentTime.toString());
                } catch (Exception e) {
                    return false;
                }
                return true;
            } else {
                // log out, but at use (not here)
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean checkTimePassedValid(String formerTimeStamp) {
        //TODO: Verify this
        Timestamp formerDate = Timestamp.valueOf(formerTimeStamp);
        Timestamp expiryTime = new Timestamp( formerDate.getTime() + inactivityTimeCap);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if ((currentTime.compareTo(expiryTime)) > 0) {
            //The current time is past the expiry time
            return false;
        } else {
            return true;
        }
    }

    public AuthToken startingAuth(String userAlias) {
        //create AuthToken in table
        AuthToken token = new AuthToken(userAlias);
        String date = new Timestamp(System.currentTimeMillis()).toString();

        getDatabaseInteractor().createItemWithDualKey(tableName, keyAttribute, userAlias, secondaryKey,  token.getToken(), true, additionalAttribute, date);
        return token;
    }

/*    public AuthToken loginAuth(LoginRequest request) {
        //create AuthToken in table
        AuthToken token = new AuthToken(request.getUsername());
        String userAlias = request.getUsername();
        String date = new Timestamp(System.currentTimeMillis()).toString();

        DynamoDBStrategy.createItemWithDualKey(tableName, keyAttribute, userAlias, secondaryKey, token, additionalAttribute, date);
        return token;
    }*/

    public Boolean logoutToken(LogoutRequest request) {
        //delete AuthToken
        String currentUserAlias = request.getUser();
        getDatabaseInteractor().deleteItemWithDualKey(tableName, keyAttribute, currentUserAlias, secondaryKey, request.getToken().getToken());
        return true;
    }

    /*
    public AuthToken registerAuth(RegisterRequest request ) {
        //create AuthToken in table
        AuthToken token = new AuthToken(request.getUserName());
        String userAlias = request.getUserName();
        String date = new Timestamp(System.currentTimeMillis()).toString();

        DynamoDBStrategy.createItemWithDualKey(tableName, keyAttribute, userAlias, secondaryKey, token, additionalAttribute, date);
        return token;
    }*/

    public DynamoDBStrategy getDatabaseInteractor() {
        return new DynamoDBStrategy();
    }
}