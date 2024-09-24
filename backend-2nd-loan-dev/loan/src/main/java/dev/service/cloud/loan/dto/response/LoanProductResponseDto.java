package dev.service.cloud.loan.dto.response;

import dev.service.cloud.loan.model.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@ToString
public class LoanProductResponseDto {

    private String loanProductsType;  //대출유형
    private BigDecimal interestRate;    //이자율
    private Integer maxLimit;   //대출한도
    private Long repaymentPeriod;  //상환기간
    private String loanProductsFeature;    //상품특징Id에 대한 이름
    private String applicationMethod;    //대출신청방법 id에 대한 대출신청방법
    private Integer requiredCreditScore;
    private String provider;

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String loanProductsTypeName;  // loanProductsType의 이름만 반환


    public static LoanProductResponseDto detailToDto(LoanProduct loanProduct) {
        LoanProductsFeature loanProductsFeature = loanProduct.getLoanProductsFeature();
        ApplicationMethod applicationMethod = loanProduct.getApplicationMethod();
        LoanProductsType loanProductsType = loanProduct.getLoanProductsType();
        Provider provider = loanProduct.getProvider();

//        System.out.println("featureName = " + featureName);
//        System.out.println("loanProductsFeature = " + loanProductsFeature);
//        System.out.println("applicationMethod = " + applicationMethod);

        return LoanProductResponseDto.builder()
                .loanProductsType(loanProductsType.getName())
                .interestRate(loanProduct.getInterestRate())
                .loanProductsFeature(loanProductsFeature.getName())
                .maxLimit(loanProduct.getMaxLimit())
                .repaymentPeriod(loanProduct.getRepaymentPeriod())
                .applicationMethod(String.valueOf(applicationMethod.getName()))
                .requiredCreditScore(loanProduct.getRequiredCreditScore())
                .provider(provider.getName())
                .build();
    }

    /**
     * 대출상품 엔티티 -> 대출상품 DTO로 변환 메소드
     *
     * @param loanProduct
     * @return LoanProductResponseDto
     */
    public static LoanProductResponseDto toDto(LoanProduct loanProduct) {
        return LoanProductResponseDto.builder()
                .id(loanProduct.getId())
                .startDate(loanProduct.getStartDate())
                .endDate(loanProduct.getEndDate())
                .interestRate(loanProduct.getInterestRate())
                .maxLimit(loanProduct.getMaxLimit())
                .repaymentPeriod(loanProduct.getRepaymentPeriod())
                .requiredCreditScore(loanProduct.getRequiredCreditScore())
                .loanProductsTypeName(loanProduct.getLoanProductsType().getName())
                .provider(loanProduct.getProvider().getName())
                .loanProductsFeature(loanProduct.getLoanProductsFeature().getName())
                .applicationMethod(loanProduct.getApplicationMethod().getName())
                .build();
    }

    /**
     * 대출상품 엔티티 리스트 -> 대출상품 엔티티 DTO 변환 메소드
     *
     * @param loanProducts
     * @return List<LoanProductResponseDto> 으로 변환
     */
    public static List<LoanProductResponseDto> toDtos(List<LoanProduct> loanProducts) {
        return loanProducts.stream().map(LoanProductResponseDto::toDto).collect(Collectors.toList());    // List.
    }
}
