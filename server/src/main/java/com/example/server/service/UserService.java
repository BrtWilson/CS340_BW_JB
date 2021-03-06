package com.example.server.service;

import com.example.server.dao.UsersTableDAO;
import com.example.shared.model.service.IUserService;
import com.example.shared.model.service.request.UserRequest;
import com.example.shared.model.service.response.UserResponse;

import java.io.IOException;

/**
 * Contains the business logic to support the login operation.
 */
public class UserService implements IUserService {

    public UserResponse getUserByAlias(UserRequest request) throws IOException {
        UserResponse response = getUserDao().getUserByAlias(request);

        return response;
    }

    /**
     * Returns an instance of {}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    public UsersTableDAO getUserDao() {
        return new UsersTableDAO();
    }
}