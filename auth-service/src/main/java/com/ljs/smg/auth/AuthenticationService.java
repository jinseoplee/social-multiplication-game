package com.ljs.smg.auth;

import com.ljs.smg.exception.PasswordMismatchException;
import com.ljs.smg.exception.UserIdAlreadyExistsException;
import com.ljs.smg.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest request) {
        validateAuthenticationRequest(request);

        var encryptedPassword = passwordEncoder.encode(request.password());
        var user = authenticationMapper.toUser(request, encryptedPassword);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userId(),
                        request.password()
                )
        );

        return userRepository.findByUserId(request.userId())
                .map(user -> {
                    var accessToken = jwtService.generateToken(user);
                    return new AuthenticationResponse(accessToken);
                })
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
    }

    private void validateAuthenticationRequest(RegisterRequest request) {
        userRepository.findByUserId(request.userId())
                .ifPresent(user -> {
                    throw new UserIdAlreadyExistsException("이미 가입된 회원입니다.");
                });

        if (!request.passwordsMatch()) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
    }
}
