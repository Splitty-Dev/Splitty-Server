package com.chaegangjo.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record SaveMyFcmTokenRequest(
        @Schema(description = "FCM 토큰", example = "dlG5jjy4SvicNcWvENgF91:APA91bHSERS39latr_mu0jh1A")
        String token
) {
}
