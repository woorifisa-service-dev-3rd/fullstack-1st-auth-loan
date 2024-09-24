package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
