package com.ljs.smg.user;

import com.ljs.smg.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserProfileWhenUserExists() {
        // given
        String userId = "ljs";
        String email = "test-email@test.com";
        User user = User.builder()
                .userId(userId)
                .email(email)
                .build();

        UserProfileResponse userProfileResponse = new UserProfileResponse(userId, email);

        given(userRepository.findByUserId(userId))
                .willReturn(Optional.of(user));
        given(userMapper.mapToUserProfileResponse(user))
                .willReturn(userProfileResponse);

        // when
        UserProfileResponse response = userService.getUserProfile(userId);

        // then
        assertEquals(userProfileResponse.userId(), response.userId());
        assertEquals(userProfileResponse.email(), response.email());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() {
        // given
        String userId = "nonexistent";
        given(userRepository.findByUserId(userId))
                .willReturn(Optional.empty());

        // when
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserProfile(userId);
        });

        // then
        assertEquals("존재하지 않는 회원입니다.", exception.getMessage());
    }
}