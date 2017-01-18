package main.application.service.scheduledClass;

import main.application.domain.scheduledClass.ScheduledClass;
import main.application.repository.scheduledClass.ScheduledClassRepository;
import main.application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledClassService implements NaturallyIdentifiableService<ScheduledClass> {

    @Autowired
    private ScheduledClassRepository repository;

    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleUuid(String universityId, String moduleCode) {
        return repository.findClassesByStudentUniveristyIdAndModuleUuid(universityId, moduleCode);
    }

    public List<ScheduledClass> findClassesByStudentUniversityId(String universityId) {
        return repository.findClassesByStudentUniveristyId(universityId);
    }

    @Override
    public ScheduledClass findByUUID(String uuid) {
        return null;
    }

    @Override
    public ScheduledClass findById(Long id) {
        return null;
    }

    @Override
    public ScheduledClass save(ScheduledClass object) {
        return null;
    }
}
