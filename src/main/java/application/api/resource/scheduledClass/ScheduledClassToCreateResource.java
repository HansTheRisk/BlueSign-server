package application.api.resource.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ScheduledClassToCreateResource extends ScheduledClassResource{

    List<String> studentUuids = new ArrayList<>();

    public ScheduledClassToCreateResource() {
        super(new ScheduledClass());
    }

    @JsonProperty("students")
    public List<String> getStudentsToAllocate() {
        return studentUuids;
    }

    @JsonProperty("students")
    public void setStudentsToAllocate(List<String> studentUuids) {
        this.studentUuids = studentUuids;
    }
}
