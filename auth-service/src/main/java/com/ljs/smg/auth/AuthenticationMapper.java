package com.ljs.smg.auth;

import com.ljs.smg.user.Role;
import com.ljs.smg.user.User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationMapper {

    public User toUser(AuthenticationRequest request, String encryptedPassword) {
        return User.builder()
                .userId(request.userId())
                .password(encryptedPassword)
                .email(request.email())
                .role(Role.USER)
                .build();
    }
}
