package application.service.allocation;

import application.repository.allocation.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    public boolean checkIfAllocationExists(String studentUniversityId, String classUuid) {
        if (allocationRepository.findAllocationByStudentIdAndClassUuid(studentUniversityId, classUuid) == null)
            return false;
        else
            return true;
    }

}
