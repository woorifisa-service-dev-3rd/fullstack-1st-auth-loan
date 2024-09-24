package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.LoanProductsFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductsFeaturesRepository extends JpaRepository<LoanProductsFeature, Long> {
}
