use ebdb;
CREATE TABLE student
(
id int NOT NULL AUTO_INCREMENT,
university_id varchar(9) UNIQUE,
name character(25),
surname character(25),
pin_salt character(25) NOT NULL,
PRIMARY KEY(id)
); 