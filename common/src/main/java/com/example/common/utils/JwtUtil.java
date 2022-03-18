package com.example.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

public class JwtUtil {

    public static final Long JWT_TTl = 60*60*1000L; // 一个小时

    public static final String JWT_KEY = "yejingwei"; // 令牌私钥

    /**
     * 创建令牌
     *
     */
    public static String createJWt(String id,String subject,Long ttlMillis){

        // 指定算法HS265
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();

        Date now = new Date(nowMillis);

        // 如果时间为null,默认一个小时
        if(ttlMillis==null){
            ttlMillis = JwtUtil.JWT_TTl;
        }

        // 令牌过期时间设置
        long expMillis = nowMillis+ttlMillis;
        Date expDate = new Date(expMillis);

        //生成密钥
        SecretKey secretKey = generalKey();

        //令牌生成
        JwtBuilder builder = Jwts.builder();
        builder.setId(id);
        builder.setIssuer("admin");
        builder.setIssuedAt(now);
        builder.setSubject(subject);
        builder.signWith(signatureAlgorithm,secretKey);
        builder.setExpiration(expDate);
        return builder.compact();

    }

    /**
     * 加密生成secretKey
     */
    public static SecretKey generalKey(){
        byte[] encodedKey = Base64.getEncoder().encode(JwtUtil.JWT_KEY.getBytes());
        SecretKeySpec key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 令牌解析
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwt)
                .getBody();
    }




}
