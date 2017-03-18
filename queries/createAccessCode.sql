use ebdb;
CREATE TABLE IF NOT EXISTS access_code
(
class_id int UNIQUE NOT NULL,
code int UNIQUE NOT NULL,
FOREIGN KEY (class_id) REFERENCES class(id)
); 