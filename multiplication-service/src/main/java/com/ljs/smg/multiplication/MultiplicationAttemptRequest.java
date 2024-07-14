package com.ljs.smg.multiplication;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MultiplicationAttemptRequest(
        @NotBlank(message = "아이디를 입력해 주세요.")
        String userId,

        @Min(value = 1, message = "1 이상의 값을 입력해 주세요.")
        @Max(value = 9, message = "9 이하의 값을 입력해 주세요.")
        int factorA,

        @Min(value = 1, message = "1 이상의 값을 입력해 주세요.")
        @Max(value = 9, message = "9 이하의 값을 입력해 주세요.")
        int factorB,

        @Min(value = 1, message = "올바른 정답을 입력해 주세요.")
        int answer
) {
}
