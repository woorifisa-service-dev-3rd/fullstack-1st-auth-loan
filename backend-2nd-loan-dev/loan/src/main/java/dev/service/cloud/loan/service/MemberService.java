package dev.service.cloud.loan.service;

import dev.service.cloud.loan.dto.request.MemberRequestDto;
import dev.service.cloud.loan.dto.response.MemberResponseDto;

public interface MemberService {
    boolean login(MemberRequestDto memberRequestDto);
}
