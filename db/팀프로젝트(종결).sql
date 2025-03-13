DROP DATABASE IF EXISTS BROAD;

CREATE DATABASE BROAD;

USE BROAD;

DROP TABLE IF EXISTS `PROGRAM_AGE`;

CREATE TABLE `PROGRAM_AGE` (
	`PA_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`PA_AGE`	VARCHAR(10)	NULL
);

DROP TABLE IF EXISTS `VIEW`;

CREATE TABLE `VIEW` (
	`VW_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`VW_WE_KEY`	INT	NOT NULL,
	`VW_STATE`	ENUM("생방송","재방송","휴방") NOT	NULL,
	`VW_CP_NUM`	INT	NOT NULL
);

DROP TABLE IF EXISTS `BROADTIME`;

CREATE TABLE `BROADTIME` (
	`BT_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`BT_VW_KEY`	INT	NOT NULL,
	`BT_DATE`	DATE NOT	NULL,
	`BT_STARTTIME`	TIME NOT	NULL,
	`BT_ENDTIME`	TIME NOT	NULL
);

DROP TABLE IF EXISTS `WEEK`;

CREATE TABLE `WEEK` (
	`WE_KEY`	INT PRIMARY KEY	NOT NULL,
	`WE_NAME`	CHAR(3) NOT	 NULL
);

DROP TABLE IF EXISTS `PROGRAM`;

CREATE TABLE `PROGRAM` (
	`PG_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`PG_NAME`	VARCHAR(20) UNIQUE NOT NULL,
	`PG_PA_KEY`	INT	NOT NULL
);

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
	`US_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`US_ID`	VARCHAR(20) UNIQUE NOT NULL,
	`US_PW`	VARCHAR(20)	NOT NULL,
	`US_NAME`	VARCHAR(10)	NOT NULL,
	`US_AUTHORITY`	ENUM("ADMIN","USER") DEFAULT "USER" NOT	NULL
);

DROP TABLE IF EXISTS `GENRE`;

CREATE TABLE `GENRE` (
	`GR_NAME`	VARCHAR(10) PRIMARY KEY
);

DROP TABLE IF EXISTS `INDEX`;

CREATE TABLE `INDEX` (
	`IN_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`IN_US_KEY`	INT	NOT NULL,
	`IN_PG_KEY`	INT	NOT NULL
);

DROP TABLE IF EXISTS `CHANNEL_PRO`;

CREATE TABLE `CHANNEL_PRO` (
	`CP_NUM`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`CP_PG_KEY`	INT	NOT NULL,
	`CP_CH_NUM`	INT	NOT NULL
);

DROP TABLE IF EXISTS `PROGRAMGENRE`;

CREATE TABLE `PROGRAMGENRE` (
	`PR_KEY`	INT PRIMARY KEY AUTO_INCREMENT	NOT NULL,
	`PR_PG_KEY`	INT	NOT NULL,
	`PR_GR_NAME`	VARCHAR(10)	NOT NULL
);

DROP TABLE IF EXISTS `CHANNEL`;

CREATE TABLE `CHANNEL` (
	`CH_NUM`	INT PRIMARY KEY AUTO_INCREMENT,
	`CH_NAME`	VARCHAR(10)	NOT NULL
);



ALTER TABLE `VIEW` ADD CONSTRAINT `FK_WEEK_TO_VIEW_1` FOREIGN KEY (
	`VW_WE_KEY`
)
REFERENCES `WEEK` (
	`WE_KEY`
);

ALTER TABLE `VIEW` ADD CONSTRAINT `FK_CHANNEL_PRO_TO_VIEW_1` FOREIGN KEY (
	`VW_CP_NUM`
)
REFERENCES `CHANNEL_PRO` (
	`CP_NUM`
);

ALTER TABLE `BROADTIME` ADD CONSTRAINT `FK_VIEW_TO_BROADTIME_1` FOREIGN KEY (
	`BT_VW_KEY`
)
REFERENCES `VIEW` (
	`VW_KEY`
);

ALTER TABLE `PROGRAM` ADD CONSTRAINT `FK_PROGRAM_AGE_TO_PROGRAM_1` FOREIGN KEY (
	`PG_PA_KEY`
)
REFERENCES `PROGRAM_AGE` (
	`PA_KEY`
);

ALTER TABLE `INDEX` ADD CONSTRAINT `FK_USER_TO_INDEX_1` FOREIGN KEY (
	`IN_US_KEY`
)
REFERENCES `USER` (
	`US_KEY`
);

ALTER TABLE `INDEX` ADD CONSTRAINT `FK_PROGRAM_TO_INDEX_1` FOREIGN KEY (
	`IN_PG_KEY`
)
REFERENCES `PROGRAM` (
	`PG_KEY`
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
	`PR_GR_NAME`
)
REFERENCES `GENRE` (
	`GR_NAME`
);







insert into program_age(pa_age) values("All"),("12세");
insert into program(pg_name,pg_pa_key)
values("무한도전",1),("런닝맨",2);
insert into channel(ch_name)values("SBS"),("KBS");
insert into channel_pro(cp_pg_key,CP_CH_NUM) values(1,1),(2,1);
INSERT INTO WEEK(WE_KEY,WE_NAME) VALUES(1,"월요일"),
(2,"화요일"),(3,"수요일"),(4,"목요일"),(5,"금요일"),
(6,"토요일"),(7,"일요일");
INSERT INTO VIEW (VW_WE_KEY,VW_STATE,VW_CP_NUM) VALUES
(2,"생방송",1),(2,"재방송",2);
INSERT INTO BROADTIME(BT_VW_KEY,BT_DATE,BT_STARTTIME,BT_ENDTIME)VALUES
(1,NOW(),NOW(),DATE_ADD(NOW(), INTERVAL 1 HOUR))
,(2,NOW(),"09:00","10:00");
INSERT INTO GENRE (GR_NAME) VALUES ("예능");
INSERT INTO PROGRAMGENRE(PR_PG_KEY, PR_GR_NAME) VALUES(1, "예능"),(2,"예능");
#SBS 화요일을 선택하면 해당 방송사의 프로그램들이 방영시간 순으로 조회되는 쿼리
SELECT 
    CH_NAME,BT_DATE, WE_NAME ,PG_NAME, PR_GR_NAME,PA_AGE,VW_STATE,BT_STARTTIME,BT_ENDTIME 
FROM
	BROADTIME
        JOIN
    VIEW ON VW_KEY = BT_VW_KEY
		JOIN
	WEEK ON WE_KEY = VW_WE_KEY
		join
	CHANNEL_PRO ON CP_NUM = VW_CP_NUM 
		join
	CHANNEL ON CH_NUM = CP_CH_NUM
		JOIN
	PROGRAM ON PG_KEY = CP_PG_KEY
		JOIN
	PROGRAM_AGE ON PA_KEY = PG_PA_KEY
		JOIN
	PROGRAMGENRE ON PG_KEY = PR_PG_KEY
    WHERE CH_NAME="SBS" AND WE_NAME="화요일"
    ORDER BY BT_STARTTIME
	;
    select* from `index`;
    insert into channel (ch_name) value("MBC");
    insert into user(us_id,us_pw,us_name,us_authority) values ("admin","admin","admin","ADMIN");
    insert into user(us_id,us_pw,us_name) values("asd","asd","asd"),("qwer","qwer","qwer");
    insert into `index`(in_us_key,in_pg_key) values(4,1),(4,2),(4,3),(4,4);
	select pg_name from `index`
    join
		program on pg_key = in_pg_key
	where in_us_key=4;
    