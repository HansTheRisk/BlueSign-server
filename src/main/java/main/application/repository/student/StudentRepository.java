package main.application.repository.student;

import main.application.domain.student.Student;
import main.application.repository.IdentifiableRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "student")
public interface StudentRepository extends IdentifiableRepository<Student> {

    //TODO:  @Query(value = "SELECT * FROM USERS WHERE EMAIL_ADDRESS = ?0", nativeQuery = true)

    @Query("SELECT s FROM Student s WHERE s.universityId = ?1 AND s.pin = ?2")
    Student findByUniversityIdAndPin(@Param("university_Id") String universityId, @Param("pin_salt") String pin);
}
