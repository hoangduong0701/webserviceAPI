package com.dripg.drip_shop.auth.controller;

import com.dripg.drip_shop.auth.DTO.LoginRequest;
import com.dripg.drip_shop.auth.DTO.RegistrationRepose;
import com.dripg.drip_shop.auth.DTO.RegistrationRequest;
import com.dripg.drip_shop.auth.DTO.UserToken;
import com.dripg.drip_shop.auth.config.JWTTokenHelper;
import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.auth.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTTokenHelper jwtTokenHelper;


    @PostMapping("/login")
    public ResponseEntity<UserToken> login(@RequestBody LoginRequest loginRequest){
        try{
            Authentication authentication= UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.getUserName(),
                    loginRequest.getPassword());

            Authentication authenticationResponse = this.authenticationManager.authenticate(authentication);

            if(authenticationResponse.isAuthenticated()){
                User user= (User) authenticationResponse.getPrincipal();
                if(!user.isEnabled()) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                String token =jwtTokenHelper.generateToken(user.getEmail());
                UserToken userToken= UserToken.builder().token(token).build();
                return new ResponseEntity<>(userToken,HttpStatus.OK);
            }

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationRepose> register(@RequestBody RegistrationRequest request) {
        RegistrationRepose registrationRepose = registrationService.createUser(request);
        if (registrationRepose.getCode() == 400){
            return new ResponseEntity<>(registrationRepose, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(registrationRepose,
                registrationRepose.getCode() == 200 ?  HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify")
    public ResponseEntity<UserToken> verifyCode(@RequestBody Map<String, String> map) {
        String userName = map.get("userName");
        String code = map.get("code");

        User user = (User)  userDetailsService.loadUserByUsername(userName);
        if(null != user && user.getVerificationCode().equals(code)){
            registrationService.verifyUser(userName);

            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
