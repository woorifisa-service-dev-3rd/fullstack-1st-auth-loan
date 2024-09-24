package dev.service.cloud.loan.dto.response;

import java.time.LocalDate;
import java.util.List;

public record MemberResponseDto(
        Long id,
        String name,
        String email,
        String phoneNumber,
        String address,
        LocalDate registeredDate,
        Integer creditScore,
        boolean isActive
) {
}