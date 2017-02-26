use ebdb;
CREATE TABLE class
(
id int NOT NULL AUTO_INCREMENT,
uuid character(36) NOT NULL UNIQUE,
module_id int NOT NULL,
start_date DATETIME NOT NULL,
end_date DATETIME NOT NULL,
room character(6) NOT NULL,
group_name varchar(4),
FOREIGN KEY (module_id) REFERENCES module(id),
PRIMARY KEY(id)	
); 