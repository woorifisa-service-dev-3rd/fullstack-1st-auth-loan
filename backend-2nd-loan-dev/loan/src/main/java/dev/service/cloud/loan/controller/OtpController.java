package dev.service.cloud.loan.controller;

import dev.service.cloud.loan.dto.request.MemberRequestDto;
import dev.service.cloud.loan.model.Member;
import dev.service.cloud.loan.service.MemberService;
import dev.service.cloud.loan.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtpController {
    @Autowired
    private  MemberService memberService;
    @Autowired
    private  OtpService otpService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberRequestDto login) {
        Boolean isLogin = memberService.login(login);
//        System.out.println(member);
        if (isLogin) {
            // OTP 생성
            String otp = otpService.generateOtp();
//            otpService.sendOtpEmail(member.getEmail(), otp);
            System.out.println(otp);
            return ResponseEntity.ok("OTP가 전송되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody String otpRequest) {
        if (otpService.generateOtp().equalsIgnoreCase(otpRequest)) {
            // 로그인 성공
            return ResponseEntity.ok("로그인 성공");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP가 유효하지 않습니다.");
    }
}