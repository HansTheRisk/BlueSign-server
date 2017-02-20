package application.service.module;

import application.domain.module.Module;
import application.repository.module.ModuleRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This service wraps the ModuleRepository functionality.
 */
@Component
public class ModuleService implements IdentifiableEntityService<Module> {

    @Autowired
    private ModuleRepository repository;

    /**
     * This method returns all the modules the student
     * is allocated to through allocation to their
     * classes.
     * @param universityId
     * @return List of Modules
     */
    public List<Module> getModulesForStudent(String universityId) {
        return repository.getModulesForStudent(universityId);
    }

    /**
     * Returns all modules taught by
     * a lecturer user.
     * @param uuid
     * @return List of Modules
     */
    public List<Module> getModulesForLecturer(String uuid) {
        return repository.getModulesForLecturer(uuid);
    }

    /**
     * Returns a module by its module code.
     * @param moduleCode
     * @return Module
     */
    public Module getByModuleCode(String moduleCode) {
        return repository.getByModuleCode(moduleCode);
    }

    /**
     * Finds a module by its id.
     * @param id
     * @return Module
     */
    @Override
    public Module findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Saves a module into the database.
     * @param object
     * @return Module
     */
    @Override
    public Module save(Module object) {
        return null;
    }
}
