use ebdb;
CREATE TABLE module
(
id int NOT NULL AUTO_INCREMENT,
title varchar(36),
module_code varchar(6) UNIQUE,
lecturer_id int,
FOREIGN KEY (lecturer_id) REFERENCES user(id),
PRIMARY KEY(id)	
); 