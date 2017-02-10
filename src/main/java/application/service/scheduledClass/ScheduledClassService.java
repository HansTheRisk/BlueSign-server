package application.service.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import application.repository.scheduledClass.ScheduledClassRepository;
import application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledClassService implements NaturallyIdentifiableService<ScheduledClass> {

    @Autowired
    private ScheduledClassRepository repository;

    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleCode(String universityId, String moduleCode) {
        return repository.findClassesByStudentUniveristyIdAndModuleCode(universityId, moduleCode);
    }

    public List<ScheduledClass> findClassesByModuleCode(String moduleCode) {
        return repository.findClassesByModuleCode(moduleCode);
    }

    public List<ScheduledClass> findClassesByStudentUniversityId(String universityId) {
        return repository.findClassesByStudentUniveristyId(universityId);
    }

    public ScheduledClass findClassByAuthenticationCode(int code) {
        return repository.findClassByAuthenticationCode(code);
    }

    public List<ScheduledClass> findCurrentlyRunningClasses() {
        return repository.findCurrentlyRunningClasses();
    }

    public CompletedClassAttendance getClassAttendance(String classUuid, long timestamp) {
        return repository.getCompletedClassAttendance(classUuid, timestamp);
    }

    @Override
    public ScheduledClass findByUUID(String uuid) {
        return repository.findByUuid(uuid);
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
