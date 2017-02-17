package application.repository.student;

import application.domain.student.Student;
import application.repository.IdentifiableRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements IdentifiableRowMapper<Student>{
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student(rs.getString("university_id"),
                                      rs.getString("name"),
                                      rs.getString("surname"));
        student.setId(rs.getLong("studentId"));
        student.setPin(rs.getString("pin_salt"));
        return student;
    }
}
