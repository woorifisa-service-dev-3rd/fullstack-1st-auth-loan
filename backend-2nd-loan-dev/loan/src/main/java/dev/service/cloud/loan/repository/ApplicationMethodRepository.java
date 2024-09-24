package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.ApplicationMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationMethodRepository extends JpaRepository<ApplicationMethod, Long> {
}
