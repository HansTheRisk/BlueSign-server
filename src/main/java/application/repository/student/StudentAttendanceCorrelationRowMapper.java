package application.repository.student;

import application.domain.attendance.Attendance;
import application.domain.student.Student;
import application.domain.student.StudentAttendanceCorrelation;
import application.repository.attendance.AttendanceRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This row mapper maps results of StudentAttendanceCorrelation queries to the
 * StudentAttendanceCorrelation objects.
 */
public class StudentAttendanceCorrelationRowMapper implements RowMapper<StudentAttendanceCorrelation> {
    @Override
    public StudentAttendanceCorrelation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new StudentRowMapper().mapRow(rs, rowNum);
        Attendance attendance = new AttendanceRowMapper().mapRow(rs, rowNum);
        return new StudentAttendanceCorrelation(student, attendance);
    }
}
