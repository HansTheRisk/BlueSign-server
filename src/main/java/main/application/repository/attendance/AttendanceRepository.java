package main.application.repository.attendance;

import main.application.domain.attendance.Attendance;
import main.application.repository.BaseJDBCRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceRepository extends BaseJDBCRepository {

    public List<Attendance> getAttendanceForStudent(String universityId) {
        String sql = "SELECT student.university_id, class.uuid AS class_uuid, attendance.date, module.module_code " +
                "FROM attendance INNER JOIN student " +
                "   ON attendance.student_id = student.id " +
                "INNER JOIN class " +
                "   ON attendance.class_id = class.id " +
                "INNER JOIN module ON " +
                "   class.module_id = module.id " +
                "WHERE university_id = ?";
        return executor.query(sql, new Object[]{universityId}, new AttendanceRowMapper());
    }

}
