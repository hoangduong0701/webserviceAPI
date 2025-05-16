package com.dripg.drip_shop.auth.services;

import com.dripg.drip_shop.auth.DTO.RegistrationRepose;
import com.dripg.drip_shop.auth.DTO.RegistrationRequest;
import com.dripg.drip_shop.auth.entities.User;
import com.dripg.drip_shop.auth.helper.VerificationCodeGenerate;
import com.dripg.drip_shop.auth.repositories.UserDetailRepository;
import com.dripg.drip_shop.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    public RegistrationRepose createUser(RegistrationRequest request) {
        User exiting = userDetailRepository.findByEmail(request.getEmail());
        if (exiting != null) {
            return RegistrationRepose.builder()
                    .code(400)
                    .message("xác minh email nay")
                    .build();
        }

        try{
            User user = new User();
            user.setFirstName(request.getFistName());
            user.setLastName(request.getLastName());
            user.setEmail(request.getEmail());
            user.setEnabled(false);
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setProvider("manual");

            String code = VerificationCodeGenerate.generateCode();
            user.setVerificationCode(code);
            user.setAuthorities(authorityService.getUserAuthority());
            userDetailRepository.save(user);
            emailService.sendEmail(user);


            return RegistrationRepose.builder()
                    .code(200)
                    .message("tạo tài khoản thành công!")
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void verifyUser(String username) {
        User user = userDetailRepository.findByEmail(username);
        user.setEnabled(true);
        userDetailRepository.save(user);
    }

}
