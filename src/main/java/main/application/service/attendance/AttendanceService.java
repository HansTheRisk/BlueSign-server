package main.application.service.attendance;

import main.application.domain.attendance.Attendance;
import main.application.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAttendanceForStudent(String universityId) {
        return attendanceRepository.getAttendanceForStudent(universityId);
    }

}
