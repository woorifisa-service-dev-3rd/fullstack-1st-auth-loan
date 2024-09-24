package dev.service.cloud.loan.service;


import dev.service.cloud.loan.dto.response.LoanProductResponseDto;
import dev.service.cloud.loan.exception.ErrorCode;
import dev.service.cloud.loan.exception.RecommendException;
import dev.service.cloud.loan.model.LoanProduct;
import dev.service.cloud.loan.model.MemberLoanProduct;
import dev.service.cloud.loan.repository.LoanProductRepository;
import dev.service.cloud.loan.repository.MemberLoanProductRepository;
import dev.service.cloud.loan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecommendServiceImpl implements RecommendService {
    private final LoanProductRepository loanProductRepository;
    private final MemberLoanProductRepository memberLoanProductRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<LoanProductResponseDto> recommendByPoint(int point) {
        if (point > 1000 || point < 0) {
            throw new RecommendException(ErrorCode.WRONG_CREDIT_SCORE, "");
        }
        List<LoanProduct> recommendedProducts = loanProductRepository.findEligibleLoanProducts(point);
        List<LoanProductResponseDto> recommendedProductsDtos;

        if (recommendedProducts.isEmpty()) {
            throw new RecommendException(ErrorCode.RECOMMEND_NOT_FOUND, "");
        } else {
            recommendedProductsDtos = LoanProductResponseDto.toDtos(recommendedProducts);
        }
        log.info("서빙되는 리스트 확인 {}", recommendedProductsDtos);
        return recommendedProductsDtos;
    }


    @Transactional
    public List<LoanProductResponseDto> recommendLoanProductsforMember(Long memberId) {
        List<MemberLoanProduct> loanHistory = memberLoanProductRepository.findByMemberId(memberId);
        int memberCreditScore = memberRepository.findById(memberId).orElseThrow(() -> new RecommendException(ErrorCode.RECOMMEND_NOT_FOUND, " memberId:" + memberId + "없음")).getCreditScore();
        if (loanHistory.isEmpty()) {
            // 대출 이력이 없는 경우
            log.info("멤버 ID {}는 대출 이력이 없습니다. 초기 사용자에게 맞는 대출 상품을 추천합니다.", memberId);

            return recommendForNewUser(memberCreditScore);
        } else {
            // 대출 이력이 있는 경우
            log.info("멤버 ID {}의 대출 이력을 분석하여 맞춤형 대출 상품을 추천합니다.", memberId);
            return recommendBasedOnLoanHistory(loanHistory, memberCreditScore);
        }
    }

    /**
     * 대출 이력이 없는 사용자에게 신용 점수에 맞는 대출 상품을 추천
     *
     * @param memberCreditScore 회원의 신용 점수
     * @return 사용자 신용점수 > 상품 신용점수 조건의 대출 상품 목록
     */
    private List<LoanProductResponseDto> recommendForNewUser(Integer memberCreditScore) {
        // 신용 점수 이하의 대출 상품을 조회
        List<LoanProduct> recommendedLoans = loanProductRepository.findByRequiredCreditScoreLessThanEqual(memberCreditScore);

        // 조회된 대출 상품을 DTOs로 변환해 반환
        return LoanProductResponseDto.toDtos(recommendedLoans);
    }

    /**
     * 대출 이력과 신용 점수를 기반으로 맞춤형 대출 상품을 추천
     *
     * @param loanHistory       사용자의 대출 이력
     * @param memberCreditScore 사용자의 신용 점수
     * @return 맞춤형 추천 대출 상품 목록
     */
    private List<LoanProductResponseDto> recommendBasedOnLoanHistory(List<MemberLoanProduct> loanHistory, int memberCreditScore) {
        // 대출 이력에서 현재 활성화된 대출이 있는지 확인 및 할당
        boolean hasActiveLoans = hasActiveLoans(loanHistory);

        // 대출 이력에서 평균 상환 기간을 계산 및 할당
        double averageRepaymentPeriod = calculateAverageRepaymentPeriod(loanHistory);

        // 대출 이력에서 평균 연체 횟수를 계산 및 할당
        double averageLatePaymentCount = calculateAverageLatePaymentCount(loanHistory);

        // 신용 점수에 맞는 대출 상품을 조회
        List<LoanProduct> recommendedLoans = loanProductRepository.findByRequiredCreditScoreLessThanEqual(memberCreditScore);

        // 연체 횟수에 따라 대출 상품을 필터링
        recommendedLoans = filterByLatePaymentCount(recommendedLoans, averageLatePaymentCount);

        // 대출 중인 상품이 없는 경우, 금리가 낮은 상품으로 추가 필터링
        if (!hasActiveLoans) {
            recommendedLoans = filterByInterestRate(recommendedLoans, new BigDecimal("5.50"));
        }

        // 상환 기간을 기준 대출 상품의 우선순위로 정렬
        List<LoanProduct> prioritizedLoans = prioritizeByRepaymentPeriod(recommendedLoans, averageRepaymentPeriod);

        // 최종적으로 필터링 및 정렬된 대출 상품을 DTO로 변환하여 리턴
        return LoanProductResponseDto.toDtos(prioritizedLoans);
    }

    /**
     * 대출 이력에서 현재 활성화된 대출이 존재 여부를 확인
     *
     * @param loanHistory 대출 이력 목록
     * @return 유효한 대출 이력 있으면 : true, 없으면 false
     */
    private boolean hasActiveLoans(List<MemberLoanProduct> loanHistory) {
        return loanHistory.stream()
                .anyMatch(loan -> loan.getEndDate() == null || loan.getEndDate().isAfter(LocalDate.now()));
    }

    /**
     * 대출 이력에서 평균 상환 기간을 계산
     *
     * @param loanHistory 대출 이력 목록
     * @return 평균 상환 기간
     */
    private double calculateAverageRepaymentPeriod(List<MemberLoanProduct> loanHistory) {
        return loanHistory.stream()
                .mapToInt(MemberLoanProduct::getRepaymentCount)
                .average()
                .orElse(0);
    }

    /**
     * 대출 이력에서 평균 연체 횟수를 계산
     *
     * @param loanHistory 대출 이력 목록
     * @return 평균 연체 횟수
     */
    private double calculateAverageLatePaymentCount(List<MemberLoanProduct> loanHistory) {
        return loanHistory.stream()
                .mapToInt(MemberLoanProduct::getLatePaymentCount)
                .average()
                .orElse(0);
    }

    /**
     * 연체 횟수에 따른 대출 상품을 필터링
     *
     * @param loans                   대출 상품 목록
     * @param averageLatePaymentCount 평균 연체 횟수
     * @return 필터링된 대출 상품 목록
     */
    private List<LoanProduct> filterByLatePaymentCount(List<LoanProduct> loans, double averageLatePaymentCount) {
        BigDecimal minInterestRate;
        if (averageLatePaymentCount > 5) {
            minInterestRate = new BigDecimal("9.00");
        } else if (averageLatePaymentCount > 2) {
            minInterestRate = new BigDecimal("7.00");
        } else {
            minInterestRate = new BigDecimal("5.90");
        }
        return loans.stream()
                .filter(loan -> loan.getInterestRate().compareTo(minInterestRate) > 0)
                .collect(Collectors.toList());
    }

    /**
     * 금리 기준 대출 상품을 필터링
     *
     * @param loans           대출 상품 목록
     * @param maxInterestRate 최대 금리
     * @return 필터링된 대출 상품 목록
     */
    private List<LoanProduct> filterByInterestRate(List<LoanProduct> loans, BigDecimal maxInterestRate) {
        return loans.stream()
                .filter(loan -> loan.getInterestRate().compareTo(maxInterestRate) <= 0)
                .collect(Collectors.toList());
    }

    /**
     * 상환기간 기준 대출 상품의 우선순위로 정렬
     *
     * @param loans                  대출 상품 목록
     * @param averageRepaymentPeriod 평균 상환 기간
     * @return 상환 기간을 기준으로 정렬된 대출 상품 목록
     */
    private List<LoanProduct> prioritizeByRepaymentPeriod(List<LoanProduct> loans, double averageRepaymentPeriod) {
        return loans.stream()
                .sorted((loan1, loan2) -> {
                    boolean isLoan1Longer = loan1.getRepaymentPeriod() >= averageRepaymentPeriod;
                    boolean isLoan2Longer = loan2.getRepaymentPeriod() >= averageRepaymentPeriod;
                    return Boolean.compare(isLoan2Longer, isLoan1Longer);
                })
                .collect(Collectors.toList());
    }
}
