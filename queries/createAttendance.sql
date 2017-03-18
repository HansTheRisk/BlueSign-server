use ebdb;
CREATE TABLE IF NOT EXISTS attendance
(
student_id int NOT NULL,
class_id int NOT NULL,
date datetime NOT NULL,
FOREIGN KEY (student_id) REFERENCES student(id),
FOREIGN KEY (class_id) REFERENCES class(id),
PRIMARY KEY(student_id, class_id, date)	
); 