package application.repository.provisioning;

import application.domain.user.User;
import application.repository.BaseJDBCRepository;
import application.repository.user.UserRowMapper;
import org.springframework.stereotype.Component;

@Component
public class ProvisioningRepository extends BaseJDBCRepository {
    public void createStudentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS student " +
                "(" +
                "id int NOT NULL AUTO_INCREMENT, " +
                "university_id varchar(30) UNIQUE, " +
                "name character(25), " +
                "surname character(25), " +
                "pin_salt character(100) NOT NULL, " +
                "email character(50) NOT NULL, " +
                "expired int NOT NULL, "+
                "PRIMARY KEY(id) " +
                ")";
        executor.execute(sql);
    }

    public void createUserTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user " +
                "(" +
                "id int NOT NULL AUTO_INCREMENT, " +
                "uuid character(36) NOT NULL UNIQUE, " +
                "username character(25) NOT NULL UNIQUE, " +
                "name character(25), " +
                "surname character(25), " +
                "email varchar(50), " +
                "psswd_salt character(100) NOT NULL, " +
                "type varchar(15) NOT NULL, " +
                "PRIMARY KEY(id)" +
                ")";
        executor.execute(sql);
    }

    public void crateModuleTable() {
        String sql = "CREATE TABLE IF NOT EXISTS module " +
                "(" +
                "id int NOT NULL AUTO_INCREMENT, " +
                "title varchar(36), " +
                "module_code varchar(6) UNIQUE, " +
                "lecturer_id int, " +
                "FOREIGN KEY (lecturer_id) REFERENCES user(id), " +
                "PRIMARY KEY(id)" +
                ")";
        executor.execute(sql);
    }

    public void createClassTable() {
        String sql = "CREATE TABLE IF NOT EXISTS class(" +
                "id int NOT NULL AUTO_INCREMENT, " +
                "uuid character(36) NOT NULL UNIQUE, " +
                "module_id int NOT NULL, " +
                "start_date DATETIME NOT NULL, " +
                "end_date DATETIME NOT NULL, " +
                "room character(6) NOT NULL, " +
                "group_name varchar(4), " +
                "FOREIGN KEY (module_id) REFERENCES module(id), " +
                "PRIMARY KEY(id)" +
                ")";
        executor.execute(sql);
    }

    public void createAllocationTable() {
        String sql = "CREATE TABLE IF NOT EXISTS allocation " +
                "(" +
                "student_id int NOT NULL, " +
                "class_id int NOT NULL, " +
                "start datetime NOT NULL, " +
                "end datetime, " +
                "FOREIGN KEY (student_id) REFERENCES student(id), " +
                "FOREIGN KEY (class_id) REFERENCES class(id), " +
                "PRIMARY KEY(student_id, class_id, start) " +
                ")";
        executor.execute(sql);
    }

    public void createAttendanceTable() {
        String sql = "CREATE TABLE IF NOT EXISTS attendance " +
                "(" +
                "student_id int NOT NULL, " +
                "class_id int NOT NULL, " +
                "date datetime NOT NULL, " +
                "FOREIGN KEY (student_id) REFERENCES student(id), " +
                "FOREIGN KEY (class_id) REFERENCES class(id), " +
                "PRIMARY KEY(student_id, class_id, date)" +
                ")";
        executor.execute(sql);
    }

    public void createAccessCodeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS access_code " +
                "(" +
                "class_id int UNIQUE NOT NULL, " +
                "code int UNIQUE NOT NULL, " +
                "FOREIGN KEY (class_id) REFERENCES class(id) " +
                ")";
        executor.execute(sql);
    }

    public void createIpRangeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS ip_range " +
                "(" +
                "id int UNIQUE NOT NULL AUTO_INCREMENT, " +
                "uuid character(36) NOT NULL UNIQUE, " +
                "start VARCHAR(15) UNIQUE NOT NULL, " +
                "end VARCHAR(15) UNIQUE NOT NULL " +
                ")";
        executor.execute(sql);
    }

    public void createModuleStudentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS module_student (" +
                "module_id int NOT NULL, " +
                "student_id int NOT NULL, " +
                "FOREIGN KEY (module_id) REFERENCES module(id), " +
                "FOREIGN KEY (student_id) REFERENCES student(id), " +
                "PRIMARY KEY(module_id, student_id)" +
                ")";
        executor.execute(sql);
    }

    public User getNoneUser() {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type, email " +
                "FROM user " +
                "WHERE username = 'NONE'";
        return executor.queryForObject(sql, new Object[]{}, new UserRowMapper());
    }

    public User getAdminnoUser() {
        String sql = "SELECT id, uuid, username, name, surname, psswd_salt, type, email " +
                "FROM user " +
                "WHERE username = 'adminno'";
        return executor.queryForObject(sql, new Object[]{}, new UserRowMapper());
    }
}
