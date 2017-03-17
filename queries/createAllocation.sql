use ebdb;
CREATE TABLE allocation
(
student_id int NOT NULL,
class_id int NOT NULL,
start datetime NOT NULL,
end datetime,
FOREIGN KEY (student_id) REFERENCES student(id),
FOREIGN KEY (class_id) REFERENCES class(id),
PRIMARY KEY(student_id, class_id, start)
); 