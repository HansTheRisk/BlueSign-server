package application.service.module;

import application.domain.module.Module;
import application.repository.module.ModuleRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ModuleService implements IdentifiableEntityService<Module> {

    @Autowired
    private ModuleRepository repository;

    public List<Module> getModulesForStudent(String universityId) {
        return repository.getModulesForStudent(universityId);
    }

    public List<Module> getModulesForLecturer(String uuid) {
        return repository.getModulesForLecturer(uuid);
    }

    @Override
    public Module findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Module save(Module object) {
        return null;
    }
}
