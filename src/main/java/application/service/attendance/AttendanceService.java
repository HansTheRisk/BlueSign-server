package application.service.attendance;

import application.domain.attendance.Attendance;
import application.repository.attendance.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Service for manipulating the AttendanceRepository.
 */
@Component
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    /**
     * This method returns all attendance records for a student.
     * @param universityId
     * @return List of Attendance.
     */
    public List<Attendance> getAttendanceRecordsForStudent(String universityId) {
        return attendanceRepository.getAttendanceRecordsForStudent(universityId);
    }

    /**
     * Returns all Attendance records for given module.
     * @param moduleCode
     * @return List of Attendance
     */
    public List<Attendance> getAttendanceRecordsForModule(String moduleCode) {
        return attendanceRepository.getAttendanceRecordsForModule(moduleCode);
    }

    /**
     * Checks if an attendance record already exists.
     * @param studentId
     * @param classUuid
     * @param date
     * @return boolean
     */
    public boolean checkIfAttendanceExists(String studentId, String classUuid, Date date) {
        if (attendanceRepository.checkIfAttendanceExists(studentId, classUuid, new java.sql.Date(date.getTime())) == null)
            return false;
        else
            return true;
    }

    /**
     * This method inserts a new attendance record
     * into the database.
     * @param attendance
     * @return boolean
     */
    public boolean insertAttendance(Attendance attendance) {
        if (attendanceRepository.insertAttendance(attendance) == 0)
            return false;
        else
            return true;
    }

}
