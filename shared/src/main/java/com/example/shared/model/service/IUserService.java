package com.example.shared.model.service;

import com.example.shared.model.domain.User;
import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public interface IUserService {

    public UserResponse getUserByAlias(UserRequest request) throws IOException, TweeterRemoteException;
}