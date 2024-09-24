package dev.service.cloud.loan.dto.request;

public record MemberRequestDto(
        String name,
        String email,
        String phoneNumber,
        String address,
        Integer creditScore,
        boolean isActive
) {
}
