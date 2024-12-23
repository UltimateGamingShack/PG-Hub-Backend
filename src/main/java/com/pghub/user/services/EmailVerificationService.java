package com.pghub.user.services;


import static org.springframework.http.HttpStatus.BAD_REQUEST;

import static com.pghub.user.exception.ErrorType.EMAIL_ALREADY_VERIFIED;
import static com.pghub.user.exception.ErrorType.EMAIL_VERIFICATION_FAILED;
import static com.pghub.user.exception.ProblemDetailBuilder.forStatusAndDetail;

import com.pghub.user.exception.RestErrorResponseException;
import com.pghub.user.model.User;
import com.pghub.user.repository.UserRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.http.ProblemDetail;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@Service
@RequiredArgsConstructor
@Slf4j
public class EmailVerificationService {

    private final OtpService otpService;
    private final TemplateEngine templateEngine;
    private final UserRepository userRepository;
    public static final String UTF_8_ENCODING = "UTF-8";
    public static final String NEW_USER_ACCOUNT_VERIFICATION = "New User Account Verification";
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendEmailVerificationOtpHTML(final UUID userId, final String email,final String username) {
       try{
           final var token = otpService.generateAndStoreOtp(userId);
           final String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM,yyyy"));
        final var emailText = "Enter the following email verification code: " + token;

        Context context = new Context();
        context.setVariables(Map.of("name",username,"otp",token,"date",date));
        String text = templateEngine.process("otptemplate",context);
        MimeMessage message = getMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
        helper.setPriority(1);
        helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
        helper.setFrom(fromEmail);
        helper.setText(text, true);
        helper.setTo(email);
        mailSender.send(message);
       } catch (Exception exception) {
           System.out.println(exception.getMessage());
           throw new RuntimeException(exception.getMessage());
       }

    }
    @Async
    public void sendEmailWelcomeHTML( final String email,final String username , final String pgname,final String roomno) {
        try{
            final String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd MMM,yyyy"));
            Context context = new Context();
            context.setVariables(Map.of("name",username,"roomno",roomno,"pgname",pgname,"date",date));
            String text = templateEngine.process("welcometemplate",context);
            MimeMessage message = getMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject(NEW_USER_ACCOUNT_VERIFICATION);
            helper.setFrom(fromEmail);
            helper.setText(text, true);
            helper.setTo(email);
            mailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }

    }

    public void resendEmailVerificationOtp(final String email) {
        userRepository.findByEmail(email).filter(user -> !user.isEmailVerified())
                .ifPresentOrElse(user -> sendEmailVerificationOtpHTML(user.getId(), user.getEmail(),user.getUsername()),
                        () -> log.warn("Attempt to resend verification token for non existing or already validated email: [{}]", email));
    }

    @Transactional
    public User verifyEmailOtp(final String email, final String otp,final String pgname,final String roomno) {
        final var user = userRepository.findByEmail(email).orElseThrow(() -> new RestErrorResponseException(forStatusAndDetail(BAD_REQUEST, "Invalid email or token")
                .withErrorType(EMAIL_VERIFICATION_FAILED)
                .build())
        );

        if (!otpService.isOtpValid(user.getId(), otp)) {
            throw new RestErrorResponseException(forStatusAndDetail(BAD_REQUEST, "Invalid email or token")
                    .withErrorType(EMAIL_VERIFICATION_FAILED)
                    .build()
            );
        }
        otpService.deleteOtp(user.getId());

        if (user.isEmailVerified()) {
            throw new RestErrorResponseException(forStatusAndDetail(BAD_REQUEST, "Email is already verified")
                    .withErrorType(EMAIL_ALREADY_VERIFIED)
                    .build()
            );
        }

        user.setEmailVerified(true);
        sendEmailWelcomeHTML(user.getEmail(),user.getUsername(),pgname,roomno);

        return user;
    }
    private MimeMessage getMimeMessage() {
        return mailSender.createMimeMessage();
    }


}