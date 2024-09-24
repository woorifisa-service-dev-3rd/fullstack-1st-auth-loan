package dev.service.cloud.loan.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "LoanProductsType")
public class LoanProductsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "loanProductsType")
    @Builder.Default
    @ToString.Exclude
    private List<LoanProduct> loanProducts = new ArrayList<>();
}