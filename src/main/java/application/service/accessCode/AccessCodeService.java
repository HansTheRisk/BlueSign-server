package application.service.accessCode;

import application.domain.accessCode.AccessCode;
import application.repository.accessCode.AccessCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccessCodeService {

    @Autowired
    private AccessCodeRepository repository;

    public void deleteCodes() {
        repository.deleteCodes();
    }

    public void insertCodes(List<AccessCode> codes) {
        repository.insertCodes(codes);
    }

}
