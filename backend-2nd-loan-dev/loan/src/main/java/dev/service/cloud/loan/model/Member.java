package dev.service.cloud.loan.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Entity
@Table(name = "member")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "registered_date")
    @CreationTimestamp
    private LocalDate registeredDate;
    @Column( name = "credit_score")
    private Integer creditScore;
    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "member")
    @Builder.Default
    private List<MemberLoanProduct> memberLoanProducts = new ArrayList<>();

}