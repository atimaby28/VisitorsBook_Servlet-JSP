create database visitorweb;

use visitorweb;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema visitorweb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema visitorweb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `visitorweb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `visitorweb` ;

-- -----------------------------------------------------
-- Table `visitorweb`.`visitors`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `visitorweb`.`visitors` ;

CREATE TABLE IF NOT EXISTS `visitorweb`.`visitors` (
  `visitorid` VARCHAR(16) NOT NULL,
  `visitorname` VARCHAR(20) NOT NULL,
  `visitorpwd` VARCHAR(16) NOT NULL,
  `email` VARCHAR(20) NULL DEFAULT NULL,
  `joindate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`visitorid`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

insert into visitors (visitorid, visitorname, visitorpwd, email, joindate)
values ('admin', '관리자', '1234', 'admin@visitor.com', now());

insert into visitors (visitorid, visitorname, visitorpwd, email, joindate)
values ('visitor', '방문자', 'visitor', 'visitor@visitor.com', now());

commit;

-- -----------------------------------------------------
-- Table `visitorweb`.`visitorsbook`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `visitorweb`.`visitorsbook` ;

CREATE TABLE IF NOT EXISTS `visitorweb`.`visitorsbook` (
  `articleno` INT NOT NULL AUTO_INCREMENT,
  `visitorid` VARCHAR(16) NULL DEFAULT NULL,
  `subject` VARCHAR(100) NULL DEFAULT NULL,
  `content` VARCHAR(2000) NULL DEFAULT NULL,
  `regtime` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`articleno`),
  INDEX `visitorsbook_visitorid_fk_idx` (`visitorid` ASC) VISIBLE,
  CONSTRAINT `visitorsbook_visitorid_fk`
    FOREIGN KEY (`visitorid`)
    REFERENCES `visitorweb`.`visitors` (`visitorid`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
