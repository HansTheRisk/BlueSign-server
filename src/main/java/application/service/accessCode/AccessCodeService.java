package application.service.accessCode;

import application.domain.accessCode.AccessCode;
import application.domain.accessCode.AccessCodeForClass;
import application.repository.accessCode.AccessCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessCodeService {

    @Autowired
    private AccessCodeRepository repository;

    public AccessCodeForClass getClassAccessCodeForLecturer(String lecturerUuid) {
        return repository.getClassAccessCodeForLecturer(lecturerUuid);
    }

    public void deleteCodes() {
        repository.deleteCodes();
    }

    public void insertCodes(List<AccessCode> codes) {
        repository.insertCodes(codes);
    }

}
