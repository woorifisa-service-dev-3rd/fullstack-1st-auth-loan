package dev.service.cloud.loan.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "loan_products_features")
public class LoanProductsFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "loanProductsFeature")
    @Builder.Default
    @ToString.Exclude
    private List<LoanProduct> loanProducts = new ArrayList<>();
}