package com.ljs.smg.auth;

import com.ljs.smg.exception.PasswordException;
import com.ljs.smg.exception.UserIdAlreadyExistsException;
import com.ljs.smg.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(AuthenticationRequest request) {
        validateAuthenticationRequest(request);

        var encryptedPassword = passwordEncoder.encode(request.password());
        var user = authenticationMapper.toUser(request, encryptedPassword);

        userRepository.save(user);
    }

    private void validateAuthenticationRequest(AuthenticationRequest request) {
        userRepository.findByUserId(request.userId())
                .ifPresent(user -> {
                    throw new UserIdAlreadyExistsException("이미 가입된 회원입니다.");
                });

        if (!request.passwordsMatch()) {
            throw new PasswordException("비밀번호가 일치하지 않습니다.");
        }
    }
}
