package application.api.resource.student;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.IdentifiableResource;
import application.domain.student.Student;

/**
 * This class represents the Student as a JSON object.
 */
public class StudentResource extends IdentifiableResource<Student> {

    public StudentResource(Student object) {
        super(object);
    }

    /**
     * This method returns the university id of the student.
     * @return String
     */
    @JsonProperty("universityId")
    public String getUniversityId() {
        return object.getUniversityId();
    }

    /**
     * This method returns the name of the student.
     * @return String
     */
    @JsonProperty("name")
    public String getName() {
        return object.getName();
    }

    /**
     * This method returns the surname of the student.
     * @return String
     */
    @JsonProperty("surname")
    public String getSurname() {
        return object.getSurname();
    }

}
