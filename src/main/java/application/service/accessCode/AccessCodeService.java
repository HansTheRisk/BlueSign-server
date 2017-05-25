package application.service.accessCode;

import application.domain.accessCode.AccessCode;
import application.domain.accessCode.AccessCodeForClass;
import application.repository.accessCode.AccessCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class wraps the AccessCodeRepository.
 */
@Component
public class AccessCodeService {

    @Autowired
    private AccessCodeRepository repository;

    /**
     * This method finds an access code for a class
     * running at the given time and taught by the
     * given lecturer.
     * @param lecturerUuid
     * @return AccessCodeForClass
     */
    public AccessCodeForClass getClassAccessCodeForLecturer(String lecturerUuid) {
        return repository.getClassAccessCodeForLecturer(lecturerUuid);
    }

    public AccessCodeForClass getAccessCodeForClass(String uuid) {
        return repository.getClassAccessCodeForClass(uuid);
    }

    public AccessCodeForClass getAccessCodeForModule(String moduleCode) {
        return repository.getClassAccessCodeForModule(moduleCode);
    }

    /**
     * Deletes all the access codes from the table
     */
    public void deleteCodes() {
        repository.deleteCodes();
    }

    /**
     * This method bulk inserts access codes.
     * @param codes
     */
    public void insertCodes(List<AccessCode> codes) {
        repository.insertCodes(codes);
    }

}
