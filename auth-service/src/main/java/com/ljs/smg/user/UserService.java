package com.ljs.smg.user;

import com.ljs.smg.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserExistsResponse checkUserExists(String userId) {
        boolean exists = userRepository.findByUserId(userId)
                .isPresent();

        return new UserExistsResponse(exists);
    }

    @Transactional(readOnly = true)
    public UserProfileResponse getUserProfile(String userId) {
        return userRepository.findByUserId(userId)
                .map(userMapper::mapToUserProfileResponse)
                .orElseThrow(() -> new UserNotFoundException("존재하지 않는 회원입니다."));
    }
}
