package com.pghub.user.controllers;


import static org.springframework.http.HttpHeaders.SET_COOKIE;

import com.pghub.user.dto.EmailVerificationRequestDto;
import com.pghub.user.security.jwt.JwtUtils;
import com.pghub.user.services.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailVerificationController {

    private final EmailVerificationService emailVerificationService;

    private final JwtUtils jwtUtils;

    @PostMapping("/request-verification-email")
    @PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
            "hasRole('COOK') or " +
            "hasRole('ADMIN')")
    public ResponseEntity<Void> resendVerificationOtp(@RequestParam final String email) {
        emailVerificationService.resendEmailVerificationOtp(email);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify-email")
    @PreAuthorize("hasRole('USER') or " + // Require USER, MODERATOR, or ADMIN role
            "hasRole('COOK') or " +
            "hasRole('ADMIN')")
    public ResponseEntity<Boolean> verifyOtp(@Valid @RequestBody final EmailVerificationRequestDto requestDto) {
        emailVerificationService.verifyEmailOtp(requestDto.email(), requestDto.otp(),requestDto.pgName(),requestDto.roomNo());

//        final var authTokens = authenticationService.authenticate(verifiedUser);

        return ResponseEntity.ok()
                .body(true);
    }

}