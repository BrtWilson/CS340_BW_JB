package com.example.shared.model.service;

import com.example.shared.model.net.TweeterRemoteException;
import com.example.shared.model.service.request.LoginRequest;
import com.example.shared.model.service.request.LogoutRequest;
import com.example.shared.model.service.response.LoginResponse;
import com.example.shared.model.service.response.BasicResponse;

import java.io.IOException;


public interface ILoginService {

    public LoginResponse login(LoginRequest request) throws IOException, TweeterRemoteException;

    public BasicResponse logout(LogoutRequest request) throws IOException, TweeterRemoteException;
}