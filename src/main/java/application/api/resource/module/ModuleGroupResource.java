package application.api.resource.module;

import application.api.resource.BaseResource;
import application.repository.module.ModuleGroup;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ModuleGroupResource extends BaseResource<ModuleGroup>{
    /**
     * The constructor for the class
     *
     * @param object
     */
    public ModuleGroupResource(ModuleGroup object) {
        super(object);
    }

    @JsonProperty("moduleGroup")
    public String getModuleGroup() {
        return object.getGroup();
    }
}
