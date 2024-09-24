package dev.service.cloud.loan.service;

import com.eatthepath.otp.TimeBasedOneTimePasswordGenerator;
import lombok.SneakyThrows;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

@Service
public class OtpService {

    private JavaMailSender mailSender;

    @SneakyThrows
    public String generateOtp() {
        TimeBasedOneTimePasswordGenerator TOTP = new TimeBasedOneTimePasswordGenerator();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
        keyGenerator.init(160);
        SecretKey secretKey = keyGenerator.generateKey();
        long timeIndex = System.currentTimeMillis() / 1000 / 30; // 30초 간격
        return String.valueOf(TOTP.generateOneTimePassword(secretKey,timeIndex));
    }
    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP code is: " + otp);
        mailSender.send(message);
    }

}