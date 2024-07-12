package com.ljs.smg.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "아이디를 입력해 주세요.")
        String userId,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        String password,

        @NotBlank(message = "비밀번호를 입력해 주세요.")
        String confirmPassword,

        String email
) {
    public boolean passwordsMatch() {
        return password.equals(confirmPassword);
    }
}
