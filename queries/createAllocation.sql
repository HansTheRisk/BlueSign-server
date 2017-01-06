use ebdb;
CREATE TABLE allocation
(
student_id int NOT NULL,
class_id int NOT NULL,
PRIMARY KEY(student_id),
PRIMARY KEY(class_id),
FOREIGN KEY (student_id) REFERENCES student(id),
FOREIGN KEY (class_id) REFERENCES class(id)
); 