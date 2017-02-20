package application.service.student;

import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.domain.student.StudentModuleAttendanceCorrelation;
import application.repository.student.StudentRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Service for operations on the Students.
 */
@Component
public class StudentService implements IdentifiableEntityService<Student>{

    @Autowired
    private StudentRepository repository;

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
        return null;
    }
}
