package dev.service.cloud.loan.service;

import dev.service.cloud.loan.model.Member;

public interface MemberService {
    Member findUserByUsername(String name);
}
