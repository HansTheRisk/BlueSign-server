USE ebdb;
CREATE TABLE user 
(
id int NOT NULL AUTO_INCREMENT,
uuid character(36) NOT NULL UNIQUE,
username character(25) NOT NULL UNIQUE,
name character(25),
surname character(25),
psswd_salt character(25) NOT NULL,
type varchar(10) NOT NULL,
PRIMARY KEY(id)	
);