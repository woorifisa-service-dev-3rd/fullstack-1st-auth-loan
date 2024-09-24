package dev.service.cloud.loan.service;

import dev.service.cloud.loan.constants.LoanProductSortCondition;
import dev.service.cloud.loan.dto.response.LoanProductResponseDto;
import dev.service.cloud.loan.exception.ErrorCode;
import dev.service.cloud.loan.exception.LoanException;
import dev.service.cloud.loan.model.LoanProduct;
import dev.service.cloud.loan.repository.LoanProductRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static dev.service.cloud.loan.constants.LoanProductSortCondition.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoanProductServiceImpl implements LoanProductService {
    private final LoanProductRepository loanProductRepository;

    @Override
    public LoanProductResponseDto findById(Long loandId) {
        loanProductRepository.findById(loandId).orElseThrow(() ->
                new LoanException(ErrorCode.LOAN_PRODUCT_NOT_FOUND, "ID " + loandId +"가 없습니다.")
        );
        LoanProduct tmp = loanProductRepository.findById(loandId).get();
        log.info("{}", tmp.toString());
        return LoanProductResponseDto.detailToDto(tmp);
    }

    @Override
    public List<LoanProductResponseDto> searchLoansByCondition(String filterName, String conditionName) {
        List<LoanProduct> loanProducts = new ArrayList<>();
        if(filterName == null && conditionName == null){
            loanProducts = loanProductRepository.findAll();
            List<LoanProductResponseDto> loanProductResponseDtos = LoanProductResponseDto.toDtos(loanProducts);
            return loanProductResponseDtos;

        }
        if(filterName != null && !filterName.isEmpty() && conditionName == null) {
            if (filterName.equals("maxLimit")) {
                loanProducts = loanProductRepository.findAllByOrderByMaxLimitAsc();
            } else if (filterName.equals("interestRate")) {
                loanProducts = loanProductRepository.findAllByOrderByInterestRateAsc();
            } else if (filterName.equals("requiredCreditScore")) {
                loanProducts = loanProductRepository.findAllByOrderByRequiredCreditScoreAsc();
            }
        }
        for (LoanProductSortCondition condition : values()) {
                if (condition.getName().equals(filterName)) {
                    loanProducts = condition.apply(loanProductRepository, conditionName);
                }
            }

        List<LoanProductResponseDto> loanProductResponseDtos = LoanProductResponseDto.toDtos(loanProducts);
        return loanProductResponseDtos;
    }
}
