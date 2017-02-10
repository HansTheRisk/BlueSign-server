package application.service.attendance;

import application.domain.attendance.Attendance;
import application.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public List<Attendance> getAttendanceRecordsForStudent(String universityId) {
        return attendanceRepository.getAttendanceRecordsForStudent(universityId);
    }

    public List<Attendance> getAttendanceRecordsForModule(String moduleCode) {
        return attendanceRepository.getAttendanceRecordsForModule(moduleCode);
    }

    public boolean checkIfAttendanceExists(String studentId, String classUuid, Date date) {
        if (attendanceRepository.checkIfAttendanceExists(studentId, classUuid, new java.sql.Date(date.getTime())) == null)
            return false;
        else
            return true;
    }

    public boolean insertAttendance(Attendance attendance) {
        if (attendanceRepository.insertAttendance(attendance) == 0)
            return false;
        else
            return true;
    }

}
