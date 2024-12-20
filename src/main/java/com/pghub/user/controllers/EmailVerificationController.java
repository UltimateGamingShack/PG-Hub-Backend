package com.pghub.user.controllers;


import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.pghub.user.dto.AuthenticationResponseDto;
import com.pghub.user.dto.EmailVerificationRequestDto;
import com.pghub.user.security.jwt.JwtUtils;
import com.pghub.user.services.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    private final JwtUtils jwtUtils;

    @PostMapping("/request-verification-email")
    public ResponseEntity<Void> resendVerificationOtp(@RequestParam final String email) {
        emailVerificationService.resendEmailVerificationOtp(email);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/verify-email")
//    public ResponseEntity<Boolean> verifyOtp(@Valid @RequestBody final EmailVerificationRequestDto requestDto) {
//        final var verifiedUser = emailVerificationService.verifyEmailOtp(requestDto.email(), requestDto.otp());
//        final var authTokens = authenticationService.authenticate(verifiedUser);
//
//        return ResponseEntity.ok()
//                .header(SET_COOKIE, addCookie(REFRESH_TOKEN_COOKIE_NAME, authTokens.refreshToken(), authTokens.refreshTokenTtl()).toString())
//                .body(new AuthenticationResponseDto(authTokens.accessToken()));
//    }

}