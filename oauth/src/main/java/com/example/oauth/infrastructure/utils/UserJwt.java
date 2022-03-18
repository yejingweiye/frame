package com.example.oauth.infrastructure.utils;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 扩展springSecurity 用户对象的信息
 */
@Data
public class UserJwt extends User {

    private String id;
    private String name;

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

}
