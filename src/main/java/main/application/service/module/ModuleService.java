package main.application.service.module;

import main.application.domain.module.Module;
import main.application.repository.module.ModuleRepository;
import main.application.service.IdentifiableEntityService;
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

    @Override
    public Module findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Module save(Module object) {
        return null;
    }
}
