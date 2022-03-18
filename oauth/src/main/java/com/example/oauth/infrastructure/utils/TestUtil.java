package com.example.oauth.infrastructure.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateCrtKey;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建令牌，必须用keyTool工具制造钥匙
 */
public class TestUtil {

    public static void main(String[] args) {
//        new TestUtil().createToken();
//        String token ="eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJuaWsiOiJ5ZWp3Iiwicm9sZSI6ImFkbWluIiwibnVtIjoxMH0.rR3OQF_t1jHMdsHFad1q0FnoyjMJ-rSQqWadL1gZOxjmhkxJC3PDBMNK1-2NwOGgQLEf7V-iKFAc8PfcEoS_fMZNKa6PzNVOYPKOG0JaMtkI4yrVtgSLqyBcv61zY0aQ-pvgTk9HzFmWdszfmdS_62MKS_ytrRoQt8n65KowhRbt7lhW3ntPTW8ww1-1rpr7yEe3TyA3OwdNKzfg8EUAlcr0jMW4ys8eT8wNTVnXvSsUu2uvCNcyYwZhPNO3By0Vb0dxvODsUno_xS6gUUE5_8ocNxufCB5-bzqGuh9_Vatw751PcUWCmlIH_x7DO9_dsm3I9HrO7Vx_BD4La2_nOg";
//
//        new TestUtil().parseToken(token);
        // 生成密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("123456");
        System.out.println(password);

    }

    /**
     * 鉴权服务器加密
     */
    public void createToken() {

        //读取证书
        ClassPathResource resource = new ClassPathResource("frame666.jks");// 证书路径


        //获取证书的内容,访问密码加载数据
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(resource, "frame666".toCharArray());

        //获取一堆密钥,别名和库密码
        KeyPair keyPair = keyStoreKeyFactory.getKeyPair("frame666", "frame666".toCharArray());

        //获取私钥
        RSAPrivateCrtKey privatekey = (RSAPrivateCrtKey) keyPair.getPrivate();

        // 创建令牌
        Map<String,Object> payload = new HashMap<>();
        payload.put("nik","yejw");
        payload.put("num",10);
        payload.put("role","admin");

        // 私钥作为一个盐，不参与解密
        Jwt jwt = JwtHelper.encode(JSON.toJSONString(payload), new RsaSigner(privatekey));

        // 获取token
        String token = jwt.getEncoded();
        System.out.println(token);

    }


    /**
     *    微服务端，本地解密
     */
    public void parseToken(String token){
        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAweoCxgVZoipjiBz2vaUCFaM1Qg+nGdHjizzWtPkrF59W7uSsDaqs3bQVp5NI8gXD6aP0YjDrfOUHCDqKJR+K+3UUY+an8++ZQpd6xOudAnZwoSz2K1d4/KpMsrxYKJAaRZvR0GFddQzU5SvTanreMWKJvuyM86Wjv9F4H9teOR6/yakWS8B+JCQXkQln6YYYGfF3rNk5PjU8LKDTJtgcg7EH/evLMmcomm95uS+cD6kpEe+kTjiWJ7/BGcXybrKZtc8dAs3fg5FirNxaguJl78bClU6R25tDMa/P06MIETZv4HdaAAoXQM4Ti3Dd4v17T5QlKwhSrSDpgZmw4Jyv2QIDAQAB-----END PUBLIC KEY-----";// 直接拷贝上来
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publicKey));
        String claims = jwt.getClaims();
        System.out.println(claims);

    }


}
