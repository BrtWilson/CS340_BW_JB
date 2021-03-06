package com.example.server.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.example.server.service.UserService;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;

import java.io.IOException;

public class UserByAliasHandler implements RequestHandler<UserRequest, UserResponse> {

    @Override
    public UserResponse handleRequest(UserRequest userRequest, Context context) {
        UserService userService = new UserService();
        try {
            userService.getUserByAlias(userRequest);
        } catch (RuntimeException | IOException e) {
            String message = "[Bad Request]";
            throw new RuntimeException(message, e);
        }
        return null;
    }
}
