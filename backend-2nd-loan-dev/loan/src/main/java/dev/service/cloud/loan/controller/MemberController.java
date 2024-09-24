package dev.service.cloud.loan.controller;

import dev.service.cloud.loan.dto.request.MemberRequestDto;
import dev.service.cloud.loan.dto.response.LoanResponseDto;
import dev.service.cloud.loan.dto.response.MemberResponseDto;
import dev.service.cloud.loan.service.LoanService;
import dev.service.cloud.loan.service.MemberService;
import dev.service.cloud.loan.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final LoanService loanService;
    private final OtpService otpService;

    /**
     * 회원의 대출이력 목록 조회
     * @param memberId
     * @return List<LoanResponseDto> 해당 회원의 대출이력 목록 반환
     */
    @GetMapping("{id}/loans")
    public ResponseEntity<List<LoanResponseDto>> getMemberLoanlist(@PathVariable("id") Long memberId) {
        List<LoanResponseDto> resDTO =loanService.getLoanListByMemberId(memberId);
        return new ResponseEntity<>(resDTO, HttpStatus.OK);
    }

    @PostMapping("/login")
//    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<Boolean> login(@RequestBody MemberRequestDto memberRequestDto) {
        log.info("Login cllad() !!!!!!");
        Boolean checked;
        if(memberService.login(memberRequestDto)){
            checked=true;
            String otp = otpService.generateOtp();
//            otpService.sendOtpEmail(member.getEmail(), otp);
            System.out.println(otp);
            return new ResponseEntity<>(checked, HttpStatus.OK);

        }else{
            checked=false;
            return new ResponseEntity<>(checked, HttpStatus.UNAUTHORIZED);
        }

    }
}
