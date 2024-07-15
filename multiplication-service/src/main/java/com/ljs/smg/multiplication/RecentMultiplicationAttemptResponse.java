package com.ljs.smg.multiplication;

import java.util.List;

public record RecentMultiplicationAttemptResponse(
        String userId,
        List<MultiplicationAttemptDetail> multiplications
) {
}
