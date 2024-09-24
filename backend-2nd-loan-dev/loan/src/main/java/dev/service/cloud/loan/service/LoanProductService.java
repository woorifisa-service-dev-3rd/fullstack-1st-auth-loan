package dev.service.cloud.loan.service;

import dev.service.cloud.loan.dto.response.LoanProductResponseDto;

import java.util.List;

public interface LoanProductService {

    // id값으로 대출상품 상세 조회
    LoanProductResponseDto findById(Long loandId);

    // 대출상품 리스트조회
    List<LoanProductResponseDto> searchLoansByCondition(String filterName, String ConditionName);
}
