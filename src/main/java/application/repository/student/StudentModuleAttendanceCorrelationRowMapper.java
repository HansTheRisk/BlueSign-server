package application.repository.student;

import application.domain.module.attendance.IndividualCumulativeModuleAttendance;
import application.domain.student.Student;
import application.domain.student.StudentModuleAttendanceCorrelation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This row mapper maps results of StudentModuleAttendanceCorrelation queries to the
 * StudentModuleAttendanceCorrelation objects.
 */
public class StudentModuleAttendanceCorrelationRowMapper implements RowMapper<StudentModuleAttendanceCorrelation>{
    @Override
    public StudentModuleAttendanceCorrelation mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new StudentRowMapper().mapRow(rs, rowNum);
        IndividualCumulativeModuleAttendance attendance = new IndividualCumulativeModuleAttendance();
        attendance.setTotalAttended(rs.getLong("count"));
        return new StudentModuleAttendanceCorrelation(student, attendance);
    }
}
