package com.ljs.smg.user;

import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserProfileResponse mapToUserProfileResponse(User user) {
        return new UserProfileResponse(
                user.getUserId(),
                user.getEmail() == null ? "" : user.getEmail()
        );
    }
}
