use ebdb;
CREATE TABLE class_allocation_snapshot (
	class_uuid character(36) NOT NULL,
    class_date DATE NOT NULL,
    allocated INT NOT NULL,
    FOREIGN KEY (class_uuid) REFERENCES class(uuid),
	PRIMARY KEY(class_uuid, class_date)	
)