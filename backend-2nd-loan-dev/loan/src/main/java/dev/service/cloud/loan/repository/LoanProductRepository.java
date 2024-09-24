package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.LoanProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface LoanProductRepository extends JpaRepository<LoanProduct, Long> {

    @EntityGraph(attributePaths = {"loanProductsFeature", "applicationMethod", "loanProductsType", "provider"})
    Optional<LoanProduct> findById(Long loanId);


    /**
     * creditScore : 신용점수 or 조사점수
     * 전달받은 점수를 기준으로 신청 가능한 상품중 대출중인 상품들을
     * 최대한도 오름차순, 이자율 오름차순으로 정렬헤서 꺼내온다
     *
     * @param point
     * @return List<LoanProduct> sorted
     */
    @Query(value =
            "SELECT lp " +
                    "FROM LoanProduct lp " +
                    "LEFT JOIN FETCH lp.provider " +
                    "LEFT JOIN FETCH lp.loanProductsType " +
                    "WHERE lp.requiredCreditScore <= :creditScore " +
                    "AND CURRENT_DATE <= lp.endDate " +
                    "ORDER BY lp.maxLimit ASC, lp.interestRate ASC")
    List<LoanProduct> findEligibleLoanProducts(@Param("creditScore") int point);

    /**
     * 회원 대출 이력이 없는 경우 신용점수 기준으로 상품을 추천
     *
     * @param memberCreditSocre
     * @return
     */
    @EntityGraph(attributePaths = {"loanProductsType", "provider", "loanProductsFeature", "applicationMethod"})
    List<LoanProduct> findByRequiredCreditScoreLessThanEqual(Integer memberCreditSocre);

    List<LoanProduct> findAllByOrderByMaxLimitAsc();
    List<LoanProduct> findAllByOrderByInterestRateAsc();
    List<LoanProduct> findAllByOrderByRequiredCreditScoreAsc();

    List<LoanProduct> findAllByLoanProductsFeatureName(String ConditionName);
    List<LoanProduct> applicationMethodName(String ConditionName);
    List<LoanProduct> findAllByRepaymentPeriod(Long ConditionName);
    List<LoanProduct> findAllByProviderName(String ConditionName);
    List<LoanProduct> findAllByLoanProductsTypeName(String ConditionName);

}
