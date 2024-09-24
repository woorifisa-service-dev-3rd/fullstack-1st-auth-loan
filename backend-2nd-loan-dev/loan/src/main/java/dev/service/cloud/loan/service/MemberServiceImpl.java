package dev.service.cloud.loan.service;

import dev.service.cloud.loan.dto.request.MemberRequestDto;
import dev.service.cloud.loan.dto.response.MemberResponseDto;
import dev.service.cloud.loan.exception.ErrorCode;
import dev.service.cloud.loan.exception.LoanException;
import dev.service.cloud.loan.exception.MemberException;
import dev.service.cloud.loan.model.Member;
import dev.service.cloud.loan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    @Override
    public boolean login(MemberRequestDto memberRequestDto) {
//        memberRepository.findMemberByEmail(memberRequestDto.email())
//                .orElseThrow(() -> new LoanException(ErrorCode.MEMBER_NOT_FOUND, "회원 EMAIL : " + memberRequestDto.email()));

        return memberRepository.findMemberByEmail(memberRequestDto.email()).isPresent();
    }

    // Member 엔티티를 MemberResponseDto로 변환
    public MemberResponseDto convertToDto(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getPhoneNumber(),
                member.getAddress(),
                member.getRegisteredDate(),
                member.getCreditScore(),
                member.isActive()
        );
    }


}
