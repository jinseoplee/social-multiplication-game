package com.ljs.smg.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "auth-service"
)
public interface UserClient {

    @GetMapping("/api/v1/users/exists/{userId}")
    UserExistsResponse checkUserExists(@PathVariable("userId") String userId);
}
