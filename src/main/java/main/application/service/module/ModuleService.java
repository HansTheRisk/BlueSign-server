//package main.application.service.module;
//
//import main.application.domain.module.Module;
//import main.application.repository.IdentifiableRepository;
//import main.application.repository.module.ModuleRepository;
//import main.application.service.IdentifiableEntityService;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class ModuleService extends IdentifiableEntityService<Module>{
//    public <S extends IdentifiableRepository<Module>> ModuleService(S repository) {
//        super(repository);
//    }
//
//    public List<Module> findModulesByStudentUniversityId(String universityId) {
//        return ((ModuleRepository)repository).findModules(universityId);
//    }
//}
