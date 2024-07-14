package com.ljs.smg.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserExistsResponse checkUserExists(String userId) {
        boolean exists = userRepository.findByUserId(userId)
                .isPresent();

        return new UserExistsResponse(exists);
    }
}
