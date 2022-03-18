package com.example.oauth.application;

import com.example.oauth.infrastructure.utils.AuthToken;

public interface AuthService {

    AuthToken login(String username, String password, String clientId, String clientSecret);

}
