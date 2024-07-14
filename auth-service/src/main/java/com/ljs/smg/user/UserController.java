package com.ljs.smg.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/exists/{userId}")
    public ResponseEntity<UserExistsResponse> checkUserExists(@PathVariable String userId) {
        return ResponseEntity.ok(userService.checkUserExists(userId));
    }
}
