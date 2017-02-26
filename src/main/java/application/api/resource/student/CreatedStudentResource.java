package application.api.resource.student;

import application.domain.student.Student;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatedStudentResource extends StudentResource{
    public CreatedStudentResource(Student student) {
        super(student);
    }

    /**
     * This method returns the pin of the student.
     * @return String
     */
    @JsonProperty("pin")
    public String getPin() {
        return object.getPin();
    }
}
