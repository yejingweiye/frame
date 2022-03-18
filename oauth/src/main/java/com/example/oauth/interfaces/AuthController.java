package com.example.oauth.interfaces;

import com.example.oauth.application.AuthService;
import com.example.oauth.infrastructure.utils.AuthToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/oauth")
public class AuthController {

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    @Resource
    private AuthService authService;


    @RequestMapping("/toLogin")
    public String toLogin(@RequestParam(value = "FROM",required = false,defaultValue = "") String from, Model model){
        System.out.println("======="+from+"=======");
        model.addAttribute("from",from);
        return "login";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public String login(String username, String password, HttpServletResponse response){
        if(username.isEmpty() || password.isEmpty()){
            return "参数为空";
        }
        AuthToken authToken = authService.login(username,password,clientId,clientSecret);
        return authToken.toString();

    }

}
