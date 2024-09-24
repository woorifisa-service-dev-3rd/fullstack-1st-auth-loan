package dev.service.cloud.loan.service;

import dev.service.cloud.loan.dto.request.LoanRequestDto;
import dev.service.cloud.loan.dto.response.LoanResponseDto;
import dev.service.cloud.loan.exception.ErrorCode;
import dev.service.cloud.loan.exception.LoanException;
import dev.service.cloud.loan.model.LoanProduct;
import dev.service.cloud.loan.model.Member;
import dev.service.cloud.loan.model.MemberLoanProduct;
import dev.service.cloud.loan.repository.LoanProductRepository;
import dev.service.cloud.loan.repository.MemberLoanProductRepository;
import dev.service.cloud.loan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {
    private final MemberLoanProductRepository memberLoanProductRepository;
    private final MemberRepository memberRepository;
    private final LoanProductRepository loanProductRepository;

    public List<LoanResponseDto> findAllLoans() {
        List<MemberLoanProduct> all = memberLoanProductRepository.findAll();
        return LoanResponseDto.toDtos(all);
    }

    @Override
    @Transactional
    public LoanResponseDto addNewLoan(LoanRequestDto loanRequestDto) {
        Member member = memberRepository.findById(loanRequestDto.getMemberId()).orElseThrow(() -> new LoanException(ErrorCode.MEMBER_NOT_FOUND, "회원 아이디 : " + loanRequestDto.getMemberId()));
        LoanProduct loanProduct = loanProductRepository.findById(loanRequestDto.getLoanProductId()).orElseThrow(() -> new LoanException(ErrorCode.LOAN_PRODUCT_NOT_FOUND, "대출 상품 아이디 : " + loanRequestDto.getLoanProductId()));
        long loanAmount = loanRequestDto.getLoanAmount();
        LocalDate loanDueDate = LocalDate.now().plusMonths(loanProduct.getRepaymentPeriod());

        checkLoanCondition(member.getCreditScore(), loanProduct.getRequiredCreditScore(), loanProduct.getMaxLimit(), loanAmount);

        BigDecimal totalRepaymentAmount = getTotalRepaymentAmount(loanProduct.getInterestRate(), loanAmount);
        log.debug("totalRepaymentAmount : " + totalRepaymentAmount);

        BigDecimal goalAmount = getGoalAmount(loanProduct.getRepaymentPeriod(), totalRepaymentAmount);
        log.debug("goalAMount : " + goalAmount + " 상환 기간 : " + loanProduct.getRepaymentPeriod());

        MemberLoanProduct memberLoanProduct = MemberLoanProduct.createMemberLoanProduct(member, loanProduct, loanAmount, loanDueDate, totalRepaymentAmount.longValue(), goalAmount.longValue());

        memberLoanProductRepository.save(memberLoanProduct);

        return LoanResponseDto.toDto(memberLoanProduct);
    }

    private static BigDecimal getGoalAmount(Long repaymentPeriod, BigDecimal totalRepaymentAmount) {
        return totalRepaymentAmount.divide(BigDecimal.valueOf(repaymentPeriod), RoundingMode.HALF_UP);
    }

    private static BigDecimal getTotalRepaymentAmount(BigDecimal interestRate, long loanAmount) {
        return interestRate
                .divide(BigDecimal.valueOf(100))
                .multiply(BigDecimal.valueOf(loanAmount))
                .add(BigDecimal.valueOf(loanAmount))
                .setScale(0, RoundingMode.HALF_UP);
    }

    private void checkLoanCondition(int memberCreditScore, int requiredCreditScore, int loanMaxLimit, long loanAmount) {
        if (memberCreditScore < requiredCreditScore) {
            throw new LoanException(ErrorCode.LOW_CREDIT_SCORE, "신용점수 : " + memberCreditScore + " 필요한 신용점수 : " + requiredCreditScore);
        }

        if (loanAmount > loanMaxLimit) {
            throw new LoanException(ErrorCode.OVER_MAX_LIMIT_LOAN_AMOUNT, "신청 금액 : " + loanAmount + " 최대 대출 한도 : " + loanMaxLimit);
        }
    }

    @Override
    @Transactional
    public LoanResponseDto repay(Long loanId) {
        MemberLoanProduct loan = memberLoanProductRepository.findById(loanId).orElseThrow(() -> new LoanException(ErrorCode.LOAN_NOT_FOUND, "대출 아이디 : " + loanId));

        checkLoanIsActive(loan.getEndDate());

        loan.repay();

        int lateMonth = checkSequence(loan.getStartDate(), loan.getRepaymentCount());
        loan.late(lateMonth);

        checkRepaymentCompleted(loan);

        return LoanResponseDto.toDto(loan);
    }

    private void checkLoanIsActive(LocalDate endDate) {
        if (endDate.isBefore(LocalDate.now()) && endDate.isEqual(LocalDate.now()))
            throw new LoanException(ErrorCode.LOAN_FINISHED, "대출 종료일 : " + endDate);
    }

    private int checkSequence(LocalDate startDate, int repaymentCount) {
        int lateMonth = 0;
        log.debug("startDate : {}, current : {}", startDate, LocalDate.now());
        int sequence = getSequence(startDate);
        log.debug("sequence : " + sequence);

        if (repaymentCount > sequence)
            throw new LoanException(ErrorCode.OVER_REPAY_COUNT, "상환 회차 : " + sequence + " 상환 횟수 : " + repaymentCount);

        if (repaymentCount < sequence) {
            lateMonth = sequence - repaymentCount;
            log.debug("lateMonth : " + lateMonth);
        }

        return lateMonth;
    }

    private int getSequence(LocalDate startDate) {
        LocalDate current = LocalDate.now();
        Period period = Period.between(startDate, current);

        return period.getYears() * 12 + period.getMonths();
    }

    private void checkRepaymentCompleted(MemberLoanProduct loan) {
        if (loan.isRepaymentCompleted()) {
            log.debug("complete repayment");
            loan.completeRepayment();
        }
    }


    /**
     * findByMemberId : JpaRepository 에서 제공하는 쿼리 메소드를 사용
     * 해당 회원이 포함된 모든 대출 이력 조회
     *
     * @param memberId
     * @return List<LoanResponseDto>
     */
    @Override
    public List<LoanResponseDto> getLoanListByMemberId(Long memberId) {
        List<LoanResponseDto> servingLoanList
                = LoanResponseDto.toHistoryDtos(memberLoanProductRepository.findByMemberId(memberId));
        if (servingLoanList.isEmpty()) {
            throw new LoanException(ErrorCode.LOAN_HISTORY_NOT_FOUND, "회원 아이디 : " + memberId);
        }
        return servingLoanList;
    }
}
