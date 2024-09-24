package dev.service.cloud.loan.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "member_loan_products")
public class MemberLoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    @CreationTimestamp
    private LocalDate startDate;
    @Column(name = "end_date")
    @Builder.Default
    private LocalDate endDate = LocalDate.of(9999, 12, 31);
    @Column(name = "loan_amount")
    private Long loanAmount;
    @Column(name = "loan_due_date")
    private LocalDate loanDueDate;
    @Column(name = "repayment_count")
    @Builder.Default
    private Integer repaymentCount = 0;
    @Column(name = "late_payment_count")
    @Builder.Default
    private Integer latePaymentCount = 0;
    @Column(name = "goal_amount")
    private Long goalAmount;
    @Column(name = "total_paid_amount")
    @Builder.Default
    private Long totalPaidAmount = 0L;
    @Column(name = "total_repayment_amount")
    private Long totalRepaymentAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    @ToString.Exclude
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_products_id")
    @ToString.Exclude
    private LoanProduct loanProduct;

    public static MemberLoanProduct createMemberLoanProduct(Member member, LoanProduct loanProduct, Long loanAmount, LocalDate loanDueDate, Long totalRepaymentAmount, Long goalAmount) {
        return MemberLoanProduct.builder()
                .member(member)
                .loanProduct(loanProduct)
                .loanAmount(loanAmount)
                .loanDueDate(loanDueDate)
                .totalRepaymentAmount(totalRepaymentAmount)
                .goalAmount(goalAmount)
                .build();
    }

    public void repay() {
        totalPaidAmount += goalAmount;
        repaymentCount++;
    }

    public void late(int lateMonth) {
        latePaymentCount = lateMonth;
    }

    public void completeRepayment() {
        endDate = LocalDate.now();
    }

    public boolean isRepaymentCompleted() {
        return totalRepaymentAmount.equals(totalPaidAmount);
    }
}
