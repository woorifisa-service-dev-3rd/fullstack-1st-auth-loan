package dev.service.cloud.loan.constants;

import dev.service.cloud.loan.model.LoanProduct;
import dev.service.cloud.loan.repository.LoanProductRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum LoanProductSortCondition {
    TYPE_NAME("typeName") {
        @Override
        public List<LoanProduct> apply(LoanProductRepository repository, String conditionName) {
            return repository.findAllByLoanProductsTypeName(conditionName);
        }
    },
    FEATURE_NAME("featureName") {
        @Override
        public List<LoanProduct> apply(LoanProductRepository repository, String conditionName) {
            return repository.findAllByLoanProductsFeatureName(conditionName);
        }
    },
    METHOD_NAME("methodName") {
        @Override
        public List<LoanProduct> apply(LoanProductRepository repository, String conditionName) {
            return repository.applicationMethodName(conditionName);
        }
    },
    PERIOD("period") {
        @Override
        public List<LoanProduct> apply(LoanProductRepository repository, String conditionName) {
            return repository.findAllByRepaymentPeriod(Long.parseLong(conditionName));
        }
    },
    PROVIDER_NAME("providerName") {
        @Override
        public List<LoanProduct> apply(LoanProductRepository repository, String conditionName) {
            return repository.findAllByProviderName(conditionName);
        }
    };
private final String name;
    public abstract List<LoanProduct> apply(LoanProductRepository repository, String conditionName);

}
