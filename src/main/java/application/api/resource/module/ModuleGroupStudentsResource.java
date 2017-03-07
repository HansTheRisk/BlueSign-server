package application.api.resource.module;

import application.api.resource.BaseResource;
import application.api.resource.student.StudentResource;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ModuleGroupStudentsResource extends BaseResource<List<StudentResource>> {

    private boolean locked = false;

    /**
     * The constructor for the class
     *
     * @param object
     */
    public ModuleGroupStudentsResource(List<StudentResource> object, boolean locked) {
        super(object);
        this.locked = locked;
    }

    @JsonProperty("students")
    public List<StudentResource> getStudents() {
        return object;
    }

    @JsonProperty("locked")
    public boolean getLocked() {
        return locked;
    }
}
