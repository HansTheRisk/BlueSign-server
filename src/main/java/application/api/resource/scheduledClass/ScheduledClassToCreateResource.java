package application.api.resource.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ScheduledClassToCreateResource extends ScheduledClassResource{

    private List<String> studentUuids = new ArrayList<>();

    public ScheduledClassToCreateResource() {
        super(new ScheduledClass());
    }

    @JsonProperty("studentIds")
    public List<String> getStudentsToAllocate() {
        return studentUuids;
    }

    @JsonProperty("studentIds")
    public void setStudentsToAllocate(List<String> studentUuids) {
        this.studentUuids = studentUuids;
    }
}
