package application.service.student;

import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.repository.student.StudentRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Student> getStudentsAllocatedToAModule(String moduleCode) {
        return repository.findAllAllocatedToAModule(moduleCode);
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
        Student student = ((StudentRepository)repository).findByUniversityIdAndPin(id, pin);
        if (student == null)
            return false;
        else
            return true;
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

    /**
     * Updates a students's details (name, surname and email)
     * in the database.
     * @param object
     * @return User
     */
    public Student updateStudentDetails(Student object) {
        return repository.updateStudentDetails(object);
    }

    public String resetUserPin(String id) {
        Random random = new Random();
        return repository.resetStudentPin(id, String.valueOf(random.nextInt(((9999 - 1000))+1)+1000));
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
