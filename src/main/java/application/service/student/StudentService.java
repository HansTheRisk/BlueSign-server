package application.service.student;

import application.domain.student.Student;
import application.repository.student.StudentRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService implements IdentifiableEntityService<Student>{

    @Autowired
    private StudentRepository repository;

    public List<Student> getAllWhoAttendedAClass(String classUuid, long timestamp) {
        return repository.findAllWhoAttendedAClass(classUuid, timestamp);
    }

    public List<Student> getAllWhoDidNotAttendAClass(String classUuid, long timestamp) {
        return repository.findAllWhoDidNotAttendAClass(classUuid, timestamp);
    }

    public boolean universityIdAndPinCombinationExist(String id, String pin) {
        Student student = ((StudentRepository)repository).findByUniversityIdAndPin(id, pin);
        if (student == null)
            return false;
        else
            return true;
    }

    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Student save(Student object) {
        return null;
    }
}
