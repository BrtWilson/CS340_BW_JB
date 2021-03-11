package edu.byu.cs.client.model.service;

import java.io.IOException;
import com.example.shared.model.domain.User;
import edu.byu.cs.client.model.net.ServerFacade;

import com.example.shared.model.service.IRegisterService;
import com.example.shared.model.service.request.RegisterRequest;
import com.example.shared.model.service.response.RegisterResponse;
import edu.byu.cs.client.util.ByteArrayUtils;

public class RegisterService implements IRegisterService {

    public static ServerFacade serverFacade;
    static final String URL_PATH = "/register";

    public RegisterResponse register(RegisterRequest request) throws IOException {
        serverFacade = getServerFacade();
        RegisterResponse registerResponse = serverFacade.register(request, URL_PATH);

        if(registerResponse.isSuccess()) {
            loadImage(registerResponse.getUser());
        }

        return registerResponse;
    }

    private void loadImage(User user) throws IOException {
        byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
        user.setImageBytes(bytes);
    }

    static ServerFacade getServerFacade() {
        if(serverFacade == null) {
            serverFacade = new ServerFacade();
        }
        return serverFacade;
    }
}