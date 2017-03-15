package application.service.scheduledClass;

import application.domain.allocation.Allocation;
import application.domain.scheduledClass.ScheduledClass;
import application.domain.scheduledClass.attendance.CompletedClassAttendance;
import application.repository.scheduledClass.ScheduledClassRepository;
import application.service.NaturallyIdentifiableService;
import application.service.allocation.AllocationService;
import application.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for operating the ScheduledClassRepository
 */
@Component
public class ScheduledClassService implements NaturallyIdentifiableService<ScheduledClass> {

    @Autowired
    private ScheduledClassRepository repository;
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private AttendanceService attendanceService;

    /**
     * Finds all the classes allocated to a student by
     * the student id and the module code.
     * @param universityId
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> getClassesByStudentUniveristyIdAndModuleCode(String universityId, String moduleCode) {
        return repository.findClassesByStudentUniveristyIdAndModuleCode(universityId, moduleCode);
    }

    /**
     * Finds all classes for the given module
     * @param moduleCode
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> getClassesByModuleCode(String moduleCode) {
        return repository.findClassesByModuleCode(moduleCode);
    }

    /**
     * Finds all classes allocated to the given student.
     * @param universityId
     * @return List of ScheduledClasses
     */
    public List<ScheduledClass> getClassesByStudentUniversityId(String universityId) {
        return repository.findClassesByStudentUniveristyId(universityId);
    }

    /**
     * Finds the class by unique authentication code.
     * @param code
     * @return ScheduledClass
     */
    public ScheduledClass getClassByAuthenticationCode(int code) {
        return repository.findClassByAuthenticationCode(code);
    }

    /**
     * Finds all the currently running classes.
     * @return  List of ScheduledClasses
     */
    public List<ScheduledClass> getCurrentlyRunningClasses() {
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

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean removeClass(String classUuid) {
        boolean value = true;
        value = value && allocationService.deleteAllAllocationsToAClass(classUuid);
        value = value && attendanceService.deleteAttendanceRecordsForAClass(classUuid);
        value = value && repository.removeClass(classUuid);
        return value;
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
        return repository.saveClass(object);
    }

    @Transactional
    public ScheduledClass saveWithStudentsAllocated(ScheduledClass object, List<String> studentUuids) {
        ScheduledClass scheduledClass = repository.saveClass(object);
        boolean done = allocationService.saveAllocations(studentUuids.stream().map(uuid -> new Allocation(uuid, object.getUuid())).collect(Collectors.toList()), scheduledClass.getStartDate().getTime());
        if(done)
            return scheduledClass;
        else
            return null;
    }
}
