package main.application.api.resource.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.application.api.resource.IdentifiableResource;
import main.application.domain.module.Module;

public class ModuleResource extends IdentifiableResource<Module> {
    public ModuleResource(Module object) {
        super(object);
    }

    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    @JsonProperty("title")
    public String getTitle() {
        return object.getTitle();
    }

    @JsonProperty("lecturerUuid")
    public String getLecturerUuid() {
        return object.getLecturerUuid();
    }
}
