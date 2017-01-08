package main.application.api.resource.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.IdentifiableResource;
import main.application.domain.student.Student;

public class StudentResource extends IdentifiableResource<Student> {

    public StudentResource(Student object) {
        super(object);
    }

    @JsonProperty("universityId")
    public String getUniversityId() {
        return object.getUniversityId();
    }

    @JsonProperty("name")
    public String getName() {
        return object.getName();
    }

    @JsonProperty("surname")
    public String getSurname() {
        return object.getSurname();
    }

}
