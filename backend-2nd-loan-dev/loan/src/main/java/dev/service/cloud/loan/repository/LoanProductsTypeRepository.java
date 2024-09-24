package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.LoanProductsType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanProductsTypeRepository extends JpaRepository<LoanProductsType, Long> {
}
