insert into week (we_key,we_name) values
(1,"월요일"), (2,"화요일"), (3,"수요일"),
(4,"목요일"), (5,"금요일"), (6,"토요일"),
(7,"일요일");
insert into user (us_id,us_pw,us_name,us_authority) values("admin","admin","admin","ADMIN");
insert into genre values
("예능"), ("드라마"), ("연예"),
("퀴즈"), ("시사"), ("스포츠"),
("뉴스"), ("교양"), ("쇼");

insert into channel (ch_name) values
("SBS"),("KBS"),("MBC");

insert into program_age (pa_age) values
("모든 연령 시청가"), ("7세 이상 시청가"), ("12세 이상 시청가"),
("15세 이상 시청가"), ("19세 이상 시청가");

insert into program (pg_name, pg_pa_key) values
("런닝맨", 1), ("무한도전", 1),("생생정보통", 1);
insert into program (pg_name, pg_show,pg_pa_key) values
("웃찾사","N",1),("동물농장","Y",1);
insert into programgenre (pr_pg_key, pr_gr_name) values
(1,"예능"),(2,"예능"),(3,"예능");

insert into channel_pro (cp_pg_key, cp_ch_num) values
(1,1),(2,1),(3,1);

insert into view (vw_we_key, vw_state, vw_cp_num) values
(2,"생방송",1), (2,"생방송",2), (7,"생방송",3);

insert into broadtime (bt_vw_key, bt_date, bt_starttime, bt_endtime) values
(1,"2025-03-13","14:00:00","16:00:00"), (2,"2025-03-13","09:00:00","10:00:00"),
(3,"2025-03-13","17:00:00","19:00:00");
