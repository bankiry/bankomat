-- MySQL Script generated by MySQL Workbench
-- Thu Feb 13 22:15:44 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema atm
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema atm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `atm` DEFAULT CHARACTER SET utf8 ;
USE `atm` ;

-- -----------------------------------------------------
-- Table `atm`.`banks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`banks` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `default_currency` VARCHAR(5) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`atms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`atms` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bank_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_atms_banks1_idx` (`bank_id` ASC) VISIBLE,
  CONSTRAINT `fk_atms_banks1`
    FOREIGN KEY (`bank_id`)
    REFERENCES `atm`.`banks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`bank_customers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`bank_customers` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`bank_accounts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`bank_accounts` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bank_customer_id` INT UNSIGNED NOT NULL,
  `bank_id` INT UNSIGNED NOT NULL,
  `number` VARCHAR(45) NOT NULL,
  `amount` DECIMAL NOT NULL,
  `currency` VARCHAR(5) NOT NULL,
  `transaction_counter` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_bank_accounts_bank_customers1_idx` (`bank_customer_id` ASC) VISIBLE,
  INDEX `fk_bank_accounts_banks1_idx` (`bank_id` ASC) VISIBLE,
  CONSTRAINT `fk_bank_accounts_bank_customers1`
    FOREIGN KEY (`bank_customer_id`)
    REFERENCES `atm`.`bank_customers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_bank_accounts_banks1`
    FOREIGN KEY (`bank_id`)
    REFERENCES `atm`.`banks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`cards`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`cards` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `number` BIGINT(16) NOT NULL,
  `cardholder_name` VARCHAR(45) NOT NULL,
  `cvv` INT NOT NULL,
  `expiration_date` DATETIME NOT NULL,
  `pin` INT NOT NULL,
  `pin_attemps_count` INT NOT NULL,
  `is_blocked` TINYINT NOT NULL,
  `bank_accounts_id` INT UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `bank_accounts_id`),
  UNIQUE INDEX `number_UNIQUE` (`number` ASC) VISIBLE,
  INDEX `fk_cards_bank_accounts1_idx` (`bank_accounts_id` ASC) VISIBLE,
  CONSTRAINT `fk_cards_bank_accounts1`
    FOREIGN KEY (`bank_accounts_id`)
    REFERENCES `atm`.`bank_accounts` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`transactions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`transactions` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `status` VARCHAR(45) NOT NULL,
  `card_id` INT UNSIGNED NOT NULL,
  `amount` DECIMAL NOT NULL,
  `operation` VARCHAR(45) NOT NULL,
  `currency` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_transactions_cards_idx` (`card_id` ASC) VISIBLE,
  CONSTRAINT `fk_transactions_cards`
    FOREIGN KEY (`card_id`)
    REFERENCES `atm`.`cards` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`exchange_rates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`exchange_rates` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `bank_id` INT UNSIGNED NOT NULL,
  `rate` DECIMAL NOT NULL,
  `currency` VARCHAR(45) NOT NULL,
  `action` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_exchange_rates_banks1_idx` (`bank_id` ASC) VISIBLE,
  CONSTRAINT `fk_exchange_rates_banks1`
    FOREIGN KEY (`bank_id`)
    REFERENCES `atm`.`banks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`banknotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`banknotes` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `denomination` INT NOT NULL,
  `currency` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`atm_languages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`atm_languages` (
  `atm_id` INT UNSIGNED NOT NULL,
  `language` VARCHAR(45) NOT NULL,
  INDEX `fk_atm_languages_atms1_idx` (`atm_id` ASC) VISIBLE,
  CONSTRAINT `fk_atm_languages_atms1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atm`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`atm_operations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`atm_operations` (
  `atm_id` INT UNSIGNED NOT NULL,
  `operation` VARCHAR(45) NOT NULL,
  INDEX `fk_atm_operations_atms1_idx` (`atm_id` ASC) VISIBLE,
  CONSTRAINT `fk_atm_operations_atms1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atm`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `atm`.`atm_banknotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`atm_banknotes` (
  `atm_id` INT UNSIGNED NOT NULL,
  `banknote_id` INT UNSIGNED NOT NULL,
  INDEX `fk_atms_has_banknotes_banknotes1_idx` (`banknote_id` ASC) VISIBLE,
  INDEX `fk_atms_has_banknotes_atms1_idx` (`atm_id` ASC) VISIBLE,
  CONSTRAINT `fk_atms_has_banknotes_atms1`
    FOREIGN KEY (`atm_id`)
    REFERENCES `atm`.`atms` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_atms_has_banknotes_banknotes1`
    FOREIGN KEY (`banknote_id`)
    REFERENCES `atm`.`banknotes` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
