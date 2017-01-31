package application.repository.student;

import application.domain.student.Student;
import application.repository.BaseJDBCRepository;
import application.repository.IdentifiableRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentRepository extends BaseJDBCRepository implements IdentifiableRepository<Student> {

    public Student findByUniversityIdAndPin(String universityId, String pin) {
        String sql = "SELECT * FROM student WHERE university_id = ? AND pin_salt = ?";
        return executor.queryForObject(sql,
                new Object[]{universityId, pin}, new StudentRowMapper());
    }

    public List<Student> findAll() {
        String sql = "SELECT * FROM student";
        return executor.query(sql, new StudentRowMapper());
    }

    @Override
    public Student findById(Long id) {
        String sql = "SELECT * FROM student WHERE id = ?";
        return executor.queryForObject(sql,
                new Object[]{id}, new StudentRowMapper());
    }
}
