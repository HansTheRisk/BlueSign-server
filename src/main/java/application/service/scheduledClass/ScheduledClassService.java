package application.service.scheduledClass;

import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import application.repository.scheduledClass.ScheduledClassRepository;
import application.service.NaturallyIdentifiableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service for operating the ScheduledClassRepository
 */
@Component
public class ScheduledClassService implements NaturallyIdentifiableService<ScheduledClass> {

    @Autowired
    private ScheduledClassRepository repository;

    /**
     * Finds all the classes allocated to a student by
     * the student id and the module code.
     * @param universityId
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByStudentUniveristyIdAndModuleCode(String universityId, String moduleCode) {
        return repository.findClassesByStudentUniveristyIdAndModuleCode(universityId, moduleCode);
    }

    /**
     * Finds all classes for the given module
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByModuleCode(String moduleCode) {
        return repository.findClassesByModuleCode(moduleCode);
    }

    /**
     * Finds all classes allocated to the given student.
     * @param universityId
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> findClassesByStudentUniversityId(String universityId) {
        return repository.findClassesByStudentUniveristyId(universityId);
    }

    /**
     * Finds the class by unique authentication code.
     * @param code
     * @return ScheduledClass
     */
    public ScheduledClass findClassByAuthenticationCode(int code) {
        return repository.findClassByAuthenticationCode(code);
    }

    /**
     * Finds all the currently running classes.
     * @return  List of ScheduledClasses
     */
    public List<ScheduledClass> findCurrentlyRunningClasses() {
        return repository.findCurrentlyRunningClasses();
    }

    /**
     * Retrieves attendance data for the completed class.
     * @param classUuid
     * @param timestamp
     * @return CompletedClassAttendance
     */
    public CompletedClassAttendance getClassAttendance(String classUuid, long timestamp) {
        return repository.getCompletedClassAttendance(classUuid, timestamp);
    }

    /**
     * Finds a scheduled class by its UUID
     * @param uuid
     * @return ScheduledClass
     */
    @Override
    public ScheduledClass findByUUID(String uuid) {
        return repository.findByUuid(uuid);
    }

    /**
     * Finds a scheduled class by its id.
     * @param id
     * @return ScheduledClass
     */
    @Override
    public ScheduledClass findById(Long id) {
        return null;
    }

    /**
     * Saves a scheduled class in the db.
     * @param object
     * @return ScheduledClass
     */
    @Override
    public ScheduledClass save(ScheduledClass object) {
        return null;
    }
}
