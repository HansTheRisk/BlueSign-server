use ebdb;
CREATE TABLE IF NOT EXISTS student
(
id int NOT NULL AUTO_INCREMENT,
university_id varchar(30) UNIQUE,
name character(25),
surname character(25),
pin_salt character(25) NOT NULL,
email character(50) NOT NULL,
expired int NOT NULL,
PRIMARY KEY(id)
); 