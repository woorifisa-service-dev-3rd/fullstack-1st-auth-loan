package dev.service.cloud.loan.service;

import dev.service.cloud.loan.dto.request.LoanRequestDto;
import dev.service.cloud.loan.dto.response.LoanResponseDto;

import java.util.List;

public interface LoanService {
    List<LoanResponseDto> findAllLoans();

    LoanResponseDto addNewLoan(LoanRequestDto loanRequestDto);

    LoanResponseDto repay(Long loanId);

    List<LoanResponseDto> getLoanListByMemberId(Long memberId);
}
