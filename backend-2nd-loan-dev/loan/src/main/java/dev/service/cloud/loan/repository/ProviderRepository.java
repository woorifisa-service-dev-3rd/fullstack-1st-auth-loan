package dev.service.cloud.loan.repository;

import dev.service.cloud.loan.model.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepository extends JpaRepository<Provider, Long> {
}
