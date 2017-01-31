package application.api.controller.lecturer;

import application.api.resource.student.StudentResource;
import application.service.student.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LecturerController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "lecturer/student", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<StudentResource>> getStudents() {
        List<StudentResource> students = new ArrayList<>();
        studentService.findAll().forEach(student -> students.add(new StudentResource(student)));
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @RequestMapping("/lecturer")
    public String lecturer() {
        return "index";
    }

}
