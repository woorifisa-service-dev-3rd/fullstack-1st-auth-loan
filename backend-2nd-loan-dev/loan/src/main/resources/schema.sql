DROP TABLE IF EXISTS member_loan_products;
DROP TABLE IF EXISTS loan_products;
DROP TABLE IF EXISTS application_method;
DROP TABLE IF EXISTS loan_products_features;
DROP TABLE IF EXISTS loan_products_type;
DROP TABLE IF EXISTS provider;
DROP TABLE IF EXISTS member;

CREATE TABLE `application_method`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LOAN`.`LoanProductsType`
-- -----------------------------------------------------
CREATE TABLE `loan_products_type`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
-- -----------------------------------------------------
-- Table `LOAN`.`LoanProductsFeatures`
-- -----------------------------------------------------
CREATE TABLE `loan_products_features`
(
    `id`   BIGINT      NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LOAN`.`provider`
-- ----------------------------------------------------
CREATE TABLE `provider`
(
    `id`        BIGINT      NOT NULL AUTO_INCREMENT,
    `name`      VARCHAR(45) NOT NULL,
    `is_active` TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
-- -----------------------------------------------------
-- Table `LOAN`.`LoanProducts`
-- -----------------------------------------------------
CREATE TABLE `loan_products`
(
    `id`                       BIGINT  NOT NULL AUTO_INCREMENT,
    `start_date`               DATE    NOT NULL,
    `end_date`                 DATE    NOT NULL DEFAULT '9999-12-31',
    `interest_rate`            DECIMAL(5,4) NOT NULL,
    `max_limit`                BIGINT  NOT NULL,
    `repayment_period`         INT     NOT NULL,
    `required_credit_score`    INT     NOT NULL,
    `type_id`                  BIGINT  NOT NULL,
    `application_method_id`    BIGINT  NOT NULL,
    `loan_products_feature_id` BIGINT  NOT NULL,
    `provider_id`              BIGINT  NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_LoanProducts_ApplicationMethod_idx` (`application_method_id` ASC) VISIBLE,
    INDEX `fk_LoanProducts_LoanProductsFeatures_idx` (`loan_products_feature_id` ASC) VISIBLE,
    CONSTRAINT `fk_LoanProducts_ApplicationMethod`
    FOREIGN KEY (`application_method_id`)
    REFERENCES `LOAN`.`application_method` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_LoanProducts_LoanProductsType`
    FOREIGN KEY (`type_id`)
    REFERENCES `LOAN`.`loan_products_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_LoanProducts_LoanProductsFeatures`
    FOREIGN KEY (`loan_products_feature_id`)
    REFERENCES `LOAN`.`loan_products_features` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_LoanProducts_provider`
    FOREIGN KEY (`provider_id`)
    REFERENCES `LOAN`.`provider` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LOAN`.`Member`
-- -----------------------------------------------------
CREATE TABLE `member`
(
    `id`              BIGINT      NOT NULL AUTO_INCREMENT,
    `name`            VARCHAR(45) NOT NULL,
    `email`           VARCHAR(45) NOT NULL,
    `phone_number`    VARCHAR(45) NOT NULL,
    `address`         VARCHAR(45) NOT NULL,
    `registered_date` DATE        NOT NULL,
    `credit_score`    INT         NOT NULL,
    `is_active`       TINYINT     NOT NULL DEFAULT 1,
    PRIMARY KEY (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `LOAN`.`MemberLoanProducts`
-- -----------------------------------------------------
CREATE TABLE `member_loan_products`
(
    `id`                 BIGINT NOT NULL AUTO_INCREMENT,
    `member_id`          BIGINT NOT NULL,
    `loan_products_id`   BIGINT NOT NULL,
    `start_date`         DATE   NOT NULL,
    `end_date`           DATE   NOT NULL DEFAULT '9999-12-31',
    `loan_amount`        BIGINT NOT NULL,
    `loan_due_date`      DATE   NOT NULL,
    `repayment_count`    INT    NOT NULL,
    `late_payment_count` INT    NOT NULL,
    -- 매월 상환해야 할 금액
    `goal_amount`        BIGINT NOT NULL,
    -- 여태 상환한 총금액
    `total_paid_amount`  BIGINT NOT NULL default 0,
    -- 목표 상환 금액 (대출금+이자)
    `total_repayment_amount`   BIGINT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_Member_has_LoanProducts_LoanProducts1_idx` (`loan_products_id` ASC) VISIBLE,
    INDEX `fk_Member_has_LoanProducts_Member_idx` (`member_id` ASC) VISIBLE,
    CONSTRAINT `fk_Member_has_LoanProducts_LoanProducts`
    FOREIGN KEY (`loan_products_id`)
    REFERENCES `LOAN`.`loan_products` (`id`),
    CONSTRAINT `fk_Member_has_LoanProducts_Member`
    FOREIGN KEY (`member_id`)
    REFERENCES `LOAN`.`member` (`id`)
    )
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
