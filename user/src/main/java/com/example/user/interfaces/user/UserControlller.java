package com.example.user.interfaces.user;


import com.alibaba.fastjson.JSON;
import com.example.common.utils.BCrypt;
import com.example.common.utils.JwtUtil;
import com.example.user.domain.user.entity.User;
import com.example.user.domain.user.service.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserControlller {

    @Resource
    private IUserService userService;


    @GetMapping("/load/{username}")
    public User findUserInfo(@PathVariable("username") String username){
        System.out.println("====feign user====");
        User user = userService.selectUserById(username);
        return user;
    }


    @RequestMapping(value = "/code", method = RequestMethod.GET)
    public String code() {
        return "200";
    }



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "password", required = false) String password,
            HttpServletResponse response) {
        User user = userService.selectUserById(username);
        System.out.println("===="+user);
        if (BCrypt.checkpw(password,user.getPassword())) {
//        if (password.equals(user.getPassword())) {
            // 自定义token 信息
            Map<String,Object> tokenMap = new HashMap<>();
            tokenMap.put("role","USER");
            tokenMap.put("success","SUCCESS");
            tokenMap.put("username",username);
            String token = JwtUtil.createJWt(UUID.randomUUID().toString(), JSON.toJSONString(tokenMap), null);

            //放在cookis
            Cookie cookie = new Cookie("Authorization",token);
            cookie.setDomain("localhost");
            cookie.setPath("/");
            response.addCookie(cookie);

            //

            return token;//直接作为参数返回
        }
        return "登录失败";
    }


}
