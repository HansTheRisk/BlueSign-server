use ebdb;
CREATE TABLE access_code
(
class_id int UNIQUE NOT NULL,
code int UNIQUE NOT NULL,
FOREIGN KEY (class_id) REFERENCES class(id)
); 