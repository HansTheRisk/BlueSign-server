package application.service.allocation;

import application.domain.allocation.Allocation;
import application.repository.allocation.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public boolean saveAllocations(List<Allocation> allocations) {
        if(allocationRepository.insertAllocations(allocations).length != 0)
            return true;
        else
            return false;
    }

    public boolean saveAllocations(List<Allocation> allocations, long timestamp) {
        if(allocationRepository.insertAllocations(allocations, timestamp).length != 0)
            return true;
        else
            return false;
    }

    public List<Allocation> getModulesClassesAllocations(String moduleCode) {
        return allocationRepository.findAllModulesClassesAllocations(moduleCode);
    }

}
