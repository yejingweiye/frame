package com.example.oauth.infrastructure.utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthToken implements Serializable {

    //令牌信息 jwt
    private String accessToken;
    //刷新token(refresh_token)
    private String refreshToken;
    //jwt短令牌
    private String jti;

}
