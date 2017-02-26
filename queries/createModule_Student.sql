use ebdb;
CREATE TABLE module_student (
	module_id int NOT NULL,
    student_id int NOT NULL,
    FOREIGN KEY (module_id) REFERENCES module(id),
    FOREIGN KEY (student_id) REFERENCES student(id),
	PRIMARY KEY(module_id, student_id)	
)