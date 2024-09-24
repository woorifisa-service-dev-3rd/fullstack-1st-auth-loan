package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.MemberLoanProduct;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberLoanProductRepository extends JpaRepository<MemberLoanProduct, Long> {
    @EntityGraph(attributePaths = {"member", "loanProduct", "loanProduct.loanProductsType"})
    List<MemberLoanProduct> findAll();

    /**
     * 회원 아이디로 회원 모든 대출 이력 조회
     *
     * @param memberId
     * @return List<MemberLoanProduct> 모든 대출 이력
     */
    @EntityGraph(attributePaths = {"member","loanProduct"})
    List<MemberLoanProduct> findByMemberId(Long memberId);

    /**
     * 회원 아이디로 현재 활성화된 대출 이력만 조회
     * 활성화 기준: endDate가 현재 날짜보다 이후일 때
     *
     * @param memberId 회원의 아이디
     * @return List<MemberLoanProduct> 활성화된 대출 이력 리스트
     */
    @Query("SELECT mlp FROM MemberLoanProduct mlp WHERE mlp.member.id = :memberId AND mlp.endDate > CURRENT_DATE")
    List<MemberLoanProduct> findActiveLoansByMemberId(@Param("memberId") Long memberId);

}
