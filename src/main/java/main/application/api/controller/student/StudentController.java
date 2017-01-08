package main.application.api.controller.student;

import main.application.api.resource.binary.BinaryResource;
import main.application.api.resource.message.MessageResource;
import main.application.api.resource.module.ModuleResource;
import main.application.api.resource.signIn.SignInResource;
import main.application.api.resource.student.StudentResource;
import main.application.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value="/student", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> test() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping(value="/student/{id}/{pin}", method= RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<BinaryResource> exists(@PathVariable String id,
                                                 @PathVariable String pin) {
        Boolean isIt = studentService.universityIdAndPinCombinationExist(id, pin);
            return new ResponseEntity<>(new BinaryResource<>(isIt), HttpStatus.OK);
    }

    public ResponseEntity<MessageResource> signIn() {
        return null;
    }

    public ResponseEntity<List<ModuleResource>> getModules() {
        //TODO: plus number of attended / total. Number of mondays between class' start date and
        return null;
    }

    public ResponseEntity<List<SignInResource>> getHistory() {
        return null;
    }

}
