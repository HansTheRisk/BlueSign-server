//package main.application.service.student;
//
//import main.application.domain.student.Student;
//import main.application.repository.student.StudentRepository;
//import main.application.service.IdentifiableEntityService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class StudentService extends IdentifiableEntityService<Student> {
//    @Autowired
//    public StudentService(StudentRepository repository) {
//        super(repository);
//    }
//
//    public boolean universityIdAndPinCombinationExist(String id, String pin) {
//        Student student = ((StudentRepository)repository).findByUniversityIdAndPin(id, pin);
//        if (student == null)
//            return false;
//        else
//            return true;
//    }
//
//    public List<Student> findAll() {
//        return repository.findAll();
//    }
//}
