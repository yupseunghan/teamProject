DROP DATABASE IF EXISTS `BROAD`;

CREATE DATABASE `BROAD`;

USE `BROAD`;

DROP TABLE IF EXISTS `BROAD_WEEK`;

CREATE TABLE `BROAD_WEEK` (
	`WE_KEY`	INT PRIMARY KEY	NOT NULL,
	`WE_NAME`	CHAR(3)	NOT NULL
);

DROP TABLE IF EXISTS `USER_INDEX`;

CREATE TABLE `USER_INDEX` (
	`IN_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`IN_US_KEY`	INT	NOT NULL,
	`IN_PG_KEY`	VARCHAR(6)	NOT NULL
);

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
	`US_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`US_ID`	VARCHAR(20) UNIQUE	NOT NULL,
	`US_PW`	VARCHAR(20)	NOT NULL,
	`US_NAME`	VARCHAR(10)	NOT NULL,
	`US_AUTHORITY`	ENUM("ADMIN","USER") DEFAULT "USER"	NOT NULL
);

DROP TABLE IF EXISTS `BROADTIME`;

CREATE TABLE `BROADTIME` (
	`BT_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`BT_DATE`	DATE	NOT NULL,
	`BT_STARTTIME`	TIME	NOT NULL,
	`BT_ENDTIME`	TIME	NULL,
	`BT_STATE`	ENUM("생","재","대")	NULL,
	`BT_WE_KEY`	INT	NOT NULL,
	`BT_CP_NUM`	INT	NOT NULL
);

DROP TABLE IF EXISTS `CHANNEL_PRO`;

CREATE TABLE `CHANNEL_PRO` (
	`CP_NUM`	INT  PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`CP_PG_KEY`	VARCHAR(6)	NOT NULL,
	`CP_CH_NUM`	INT	NOT NULL
);

DROP TABLE IF EXISTS `PROGRAM`;

CREATE TABLE `PROGRAM` (
	`PG_KEY`	VARCHAR(6) PRIMARY KEY	NOT NULL,
	`PG_NAME`	VARCHAR(20)	NOT NULL
);

DROP TABLE IF EXISTS `CHANNEL`;

CREATE TABLE `CHANNEL` (
	`CH_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`CH_NAME`	VARCHAR(20) UNIQUE	NOT NULL
);

DROP TABLE IF EXISTS `PROGRAMGENRE`;

CREATE TABLE `PROGRAMGENRE` (
	`PR_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`PR_PG_KEY`	VARCHAR(6)	NOT NULL,
	`PR_GR_NUM`	INT	NOT NULL
);

DROP TABLE IF EXISTS `GENRE`;

CREATE TABLE `GENRE` (
	`GR_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`GR_NAME`	VARCHAR(10)	NULL
);

DROP TABLE IF EXISTS `PROGRAM_DETAIL`;

CREATE TABLE `PROGRAM_DETAIL` (
	`PG_DT_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`PG_DT_AGE`	ENUM("7","12","15","전체","청불")	NULL,
	`PG_DT_WE_KEY`	INT	NULL,
	`PG_BTIME`	ENUM("새벽","아침","점심","저녁","심야")	NULL,
	`PG_DT_CONTENT`	LONGTEXT	NULL,
	`PG_DT_PG_KEY`	VARCHAR(6)	NOT NULL
);

ALTER TABLE `USER_INDEX` ADD CONSTRAINT `FK_USER_TO_USER_INDEX_1` FOREIGN KEY (
	`IN_US_KEY`
)
REFERENCES `USER` (
	`US_KEY`
);

ALTER TABLE `USER_INDEX` ADD CONSTRAINT `FK_PROGRAM_TO_USER_INDEX_1` FOREIGN KEY (
	`IN_PG_KEY`
)
REFERENCES `PROGRAM` (
	`PG_KEY`
);

ALTER TABLE `BROADTIME` ADD CONSTRAINT `FK_BROAD_WEEK_TO_BROADTIME_1` FOREIGN KEY (
	`BT_WE_KEY`
)
REFERENCES `BROAD_WEEK` (
	`WE_KEY`
);

ALTER TABLE `BROADTIME` ADD CONSTRAINT `FK_CHANNEL_PRO_TO_BROADTIME_1` FOREIGN KEY (
	`BT_CP_NUM`
)
REFERENCES `CHANNEL_PRO` (
	`CP_NUM`
);

ALTER TABLE `CHANNEL_PRO` ADD CONSTRAINT `FK_PROGRAM_TO_CHANNEL_PRO_1` FOREIGN KEY (
	`CP_PG_KEY`
)
REFERENCES `PROGRAM` (
	`PG_KEY`
);

ALTER TABLE `CHANNEL_PRO` ADD CONSTRAINT `FK_CHANNEL_TO_CHANNEL_PRO_1` FOREIGN KEY (
	`CP_CH_NUM`
)
REFERENCES `CHANNEL` (
	`CH_NUM`
);

ALTER TABLE `PROGRAMGENRE` ADD CONSTRAINT `FK_PROGRAM_TO_PROGRAMGENRE_1` FOREIGN KEY (
	`PR_PG_KEY`
)
REFERENCES `PROGRAM` (
	`PG_KEY`
);

ALTER TABLE `PROGRAMGENRE` ADD CONSTRAINT `FK_GENRE_TO_PROGRAMGENRE_1` FOREIGN KEY (
	`PR_GR_NUM`
)
REFERENCES `GENRE` (
	`GR_NUM`
);

ALTER TABLE `PROGRAM_DETAIL` ADD CONSTRAINT `FK_BROAD_WEEK_TO_PROGRAM_DETAIL_1` FOREIGN KEY (
	`PG_DT_WE_KEY`
)
REFERENCES `BROAD_WEEK` (
	`WE_KEY`
);

ALTER TABLE `PROGRAM_DETAIL` ADD CONSTRAINT `FK_PROGRAM_TO_PROGRAM_DETAIL_1` FOREIGN KEY (
	`PG_DT_PG_KEY`
)
REFERENCES `PROGRAM` (
	`PG_KEY`
);


/*
DELIMITER //
CREATE TRIGGER PG_CODE_GENERATE
BEFORE INSERT ON PROGRAM
FOR EACH ROW
BEGIN
    DECLARE IDNEXT INT;
    SET IDNEXT = (SELECT COUNT(*) + 1 FROM PROGRAM WHERE PG_KEY LIKE CONCAT(NEW.PG_KEY_PREFIX, '%'));
    SET NEW.PG_KEY = CONCAT(NEW.PG_KEY_PREFIX, LPAD(IDNEXT, 3, '0'));
END//
DELIMITER ;	
*/

