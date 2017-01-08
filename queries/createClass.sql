use ebdb;
CREATE TABLE class
(
id int NOT NULL AUTO_INCREMENT,
uuid character(36) NOT NULL UNIQUE,
module_id int NOT NULL,
day character(8) NOT NULL,
start_date date NOT NULL,
end_date date NOT NULL,
start_time time NOT NULL,
end_time time NOT NULL,
FOREIGN KEY (module_id) REFERENCES module(id),
PRIMARY KEY(id)	
); 