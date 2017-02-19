package application.api.resource.module;

import com.fasterxml.jackson.annotation.JsonProperty;
import application.api.resource.IdentifiableResource;
import application.domain.module.Module;

/**
 * This class represents modules in JSON form.
 */
public class ModuleResource extends IdentifiableResource<Module> {
    public ModuleResource(Module object) {
        super(object);
    }

    /**
     * This method returns the module code.
     * @return String
     */
    @JsonProperty("moduleCode")
    public String getModuleCode() {
        return object.getModuleCode();
    }

    /**
     * This method returns the title of the module.
     * @return
     */
    @JsonProperty("title")
    public String getTitle() {
        return object.getTitle();
    }

    /**
     * This method returns the UUID of the lecturer
     * of the module.
     * @return
     */
    @JsonProperty("lecturerUuid")
    public String getLecturerUuid() {
        return object.getLecturerUuid();
    }
}
