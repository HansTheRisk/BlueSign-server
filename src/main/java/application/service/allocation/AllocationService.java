package application.service.allocation;

import application.repository.allocation.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This service is an interface for the Allocation Repository
 */
@Component
public class AllocationService {

    @Autowired
    private AllocationRepository allocationRepository;

    /**
     * This method checks if an allocation
     * between a student and a class exists.
     * @param studentUniversityId
     * @param classUuid
     * @return boolean
     */
    public boolean checkIfAllocationExists(String studentUniversityId, String classUuid) {
        if (allocationRepository.findAllocationByStudentIdAndClassUuid(studentUniversityId, classUuid) == null)
            return false;
        else
            return true;
    }

}
