package com.dripg.drip_shop.auth.controller;

import com.dripg.drip_shop.auth.config.JWTTokenHelper;
import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.auth.services.Oauth2Service;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.dialect.JsonHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/oauth2")
public class Oauth2Controller {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;
    @Autowired
    Oauth2Service oauth2Service;
    @GetMapping("/success")
    public void callBackOauth2(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) throws IOException {
        String username = oAuth2User.getAttribute("email");
        User user = oauth2Service.getUser(username);
        if (null == user){
            user = oauth2Service.createUser(oAuth2User, "google");
        }
        String token = jwtTokenHelper.generateToken(user.getUsername());
        response.sendRedirect("http://localhost:3002/oauth2/callback?token="+ token);


    }
}
