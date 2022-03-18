package com.example.common;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        JwtBuilder builder = Jwts.builder();
        builder.setId("888"); // 设置唯一ID
        builder.setIssuer("yejinngwei"); //
        builder.setIssuedAt(new Date());
        builder.setSubject("令牌测试");
        builder.setExpiration(new Date(System.currentTimeMillis()+10000));// 10秒钟过期，30分钟
        builder.signWith(SignatureAlgorithm.HS256,"itcast");
        //自定义载荷
        Map<String,Object> userInfo = new HashMap<>();
        userInfo.put("age",18);
        builder.addClaims(userInfo);
        String token = builder.compact();
        System.out.println(token);
        Thread.sleep(11*1000); // 测试11秒解析
        String s = parseToken(token);
    }

    public static String parseToken(String token) {
        Claims claims = Jwts.parser().setSigningKey("itcast").parseClaimsJws(token).getBody();
        System.out.println(claims.toString());//解析后的数据
        return claims.toString();
    }
}
