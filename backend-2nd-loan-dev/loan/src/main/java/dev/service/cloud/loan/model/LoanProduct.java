package dev.service.cloud.loan.model;

import dev.service.cloud.loan.dto.response.LoanProductResponseDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "loan_products")
public class LoanProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "start_date")
    @CreationTimestamp
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "interest_rate")
    private BigDecimal interestRate;
    @Column(name = "max_limit")
    private Integer maxLimit;
    @Column(name = "repayment_period")
    private Long repaymentPeriod;
    @Column(name = "required_credit_score")
    private Integer requiredCreditScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private LoanProductsType loanProductsType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    private Provider provider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_products_feature_id")
    private LoanProductsFeature loanProductsFeature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_method_id")
    private ApplicationMethod applicationMethod;

    @OneToMany(mappedBy = "loanProduct")
    @Builder.Default
    private List<MemberLoanProduct> memberLoanProducts = new ArrayList<>();


}