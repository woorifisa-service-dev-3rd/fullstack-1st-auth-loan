package dev.service.cloud.loan.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class LoanRequestDto {
    private Long memberId;
    private Long loanProductId;
    private Long loanAmount;

}
