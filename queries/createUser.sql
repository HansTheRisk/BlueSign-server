USE ebdb;
CREATE TABLE user 
(
id int NOT NULL AUTO_INCREMENT,
uuid character(36) NOT NULL UNIQUE,
username character(25),
name character(25),
surname character(25),
psswd_salt character(25),
type varchar(10),
PRIMARY KEY(id)	
);