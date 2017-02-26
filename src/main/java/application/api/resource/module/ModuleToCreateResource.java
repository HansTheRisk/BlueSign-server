package application.api.resource.module;

import application.domain.module.Module;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ModuleToCreateResource extends ModuleResource{

    private ArrayList<String> studentIds;

    public ModuleToCreateResource() {
        super(new Module());
    }

    public ModuleToCreateResource(Module object) {
        super(object);
    }

    @JsonProperty("studentIds")
    public ArrayList<String> getStudentIds() {
        return studentIds;
    }

    @JsonProperty("studentIds")
    public void setStudentIds(ArrayList<String> studentIds) {
        this.studentIds = studentIds;
    }
}
