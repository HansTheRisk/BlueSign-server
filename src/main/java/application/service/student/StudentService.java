package application.service.student;

import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.repository.student.StudentRepository;
import application.service.IdentifiableEntityService;
import application.service.allocation.AllocationService;
import application.service.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/**
 * Service for operations on the Students.
 */
@Component
public class StudentService implements IdentifiableEntityService<Student>{

    @Autowired
    private StudentRepository repository;
    @Autowired
    private AllocationService allocationService;
    @Autowired
    private ModuleService moduleService;

    public List<Student> getStudentsAllocatedToAModule(String moduleCode) {
        return repository.findAllAllocatedToAModule(moduleCode);
    }
    public List<Student> getStudentsAllocatedToAClass(String uuid) {
        return repository.findAllAllocatedToAClass(uuid);
    }

    public List<Student> getStudentsAllocatedToAModuleButNotToItsClasses(String moduleCode) {
        return repository.findAllAllocatedToAModuleButNotToItsClasses(moduleCode);
    }

    public List<Student> getStudentsAllocatedToAModuleGroup(String moduleCode, String group) {
        return repository.findStudentsAllocatedToAModuleGroup(moduleCode, group);
    }

    public List<Student> getStudentsAllocatedOnlyToNoneGroup(String moduleCode) {
        return repository.findStudentsOnlyAllocatedToNoneGroup(moduleCode);
    }

    /**
     * Gets all students who attended a given completed class.
     * @param classUuid
     * @param timestamp
     * @return List of StudentAttendanceCorrelation
     */
    public List<StudentAttendanceCorrelation> getAllWhoAttendedAClass(String classUuid, long timestamp) {
        return repository.findAllWhoAttendedAClass(classUuid, timestamp);
    }

    /**
     * Gets all students who were late for a completed class.
     * @param classUuid
     * @param timestamp
     * @return List of StudentAttendanceCorrelation
     */
    public List<StudentAttendanceCorrelation> getAllWhoWereLateForClass(String classUuid, long timestamp) {
        return repository.findAllWhoWereLateForAClass(classUuid, timestamp);
    }

    /**
     * Gets all students who did not attend a completed class.
     * @param classUuid
     * @param timestamp
     * @return  List of Students
     */
    public List<Student> getAllWhoDidNotAttendAClass(String classUuid, long timestamp) {
        return repository.findAllWhoDidNotAttendAClass(classUuid, timestamp);
    }

    /**
     * Checks is a student with the given id and pin combination exists.
     * @param id
     * @param pin
     * @return boolean
     */
    public boolean universityIdAndPinCombinationExist(String id, String pin) {
        boolean response = false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Student student = repository.findByUniversityId(id);
        if (student != null)
            response = encoder.matches(pin, student.getPin());
        return response;
    }

    /**
     * Finds a student by the given university id.
     * @param universityId
     * @return Student
     */
    public Student getStudentByUniversityId(String universityId) {
        return repository.findByUniversityId(universityId);
    }

    /**
     * Returns StudentModuleAttendanceCorrelations for the given module and module group.
     * Specific module group + NONE group.
     * @param moduleCode
     * @param groupName
     * @return List of StudentModuleAttendanceCorrelations
     */
    public List<StudentModuleAttendanceCorrelation> getStudentAttendanceCorrelationForModuleGroup(String moduleCode, String groupName) {
        return repository.findStudentAttendanceCorrelationForModuleGroup(moduleCode, groupName);
    }

    /**
     * Returns StudentModuleAttendanceCorrelations for the given module and NONE module group.
     * @param moduleCode
     * @return
     */
    public List<StudentModuleAttendanceCorrelation> getStudentAttendanceCorrelationForStudentsWithNoGroup(String moduleCode) {
        return repository.findStudentAttendanceCorrelationForStudentsWithNoGroup(moduleCode);
    }

    /**
     * Returns all the students.
     * @return List of Students
     */
    public List<Student> findAll() {
        return repository.findAll();
    }

    public List<Student> getStudentAvailableToAllocateToModule(String moduleCode) {
        return repository.findStudentsAvailableToAllocateToModule(moduleCode);
    }

    /**
     * Updates a students's details (name, surname and email)
     * in the database.
     * @param object
     * @return User
     */
    public Student updateStudentDetails(Student object) {
        return repository.updateStudentDetails(object);
    }

    public String resetStudentPin(String id) {
        Random random = new Random();
        return repository.resetStudentPin(id, String.valueOf(random.nextInt(((9999 - 1000))+1)+1000));
    }

    public boolean removeStudent(String universityId) {
        boolean value = true;
        value = value && allocationService.cancelStudentsAllocationsToClasses(universityId);
        value = value && moduleService.removeStudentsAllocationsToModules(universityId);
        value = value && repository.deactivateStudent(universityId);
        return value;
    }

    /**
     * Returns a student by id.
     * @param id
     * @return Student
     */
    @Override
    public Student findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Stores a student in the DB.
     * @param object
     * @return Student
     */
    @Override
    public Student save(Student object) {
        Random random = new Random();
        object.setPin(String.valueOf(random.nextInt(((9999 - 1000))+1)+1000));
        return repository.saveStudent(object);
    }
}
