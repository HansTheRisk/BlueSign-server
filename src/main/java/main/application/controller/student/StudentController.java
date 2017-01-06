package main.application.controller.student;

import main.application.domain.student.Student;
import main.application.repository.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@EnableAutoConfiguration
public class StudentController {

    @Autowired
    private StudentRepository studentService;

    @RequestMapping(value="/student", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<Student>> test() {
        return new ResponseEntity<List<Student>>(studentService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/{pin}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Boolean> exists(@PathVariable String id,
                                          @PathVariable String pin) {
        Student student = studentService.findByUniversityIdAndPin(id, pin);
        if (student != null)
            return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
        else
            return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
    }

}
