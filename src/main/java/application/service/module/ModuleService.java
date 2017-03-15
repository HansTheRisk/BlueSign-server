package application.service.module;

import application.domain.allocation.Allocation;
import application.domain.module.Module;
import application.repository.allocation.AllocationRepository;
import application.repository.attendance.AttendanceRepository;
import application.repository.module.ModuleGroup;
import application.repository.module.ModuleRepository;
import application.repository.scheduledClass.ScheduledClassRepository;
import application.service.IdentifiableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This service wraps the ModuleRepository functionality.
 */
@Component
public class ModuleService implements IdentifiableEntityService<Module> {

    @Autowired
    private ModuleRepository repository;
    @Autowired
    private AllocationRepository allocationRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private ScheduledClassRepository scheduledClassRepository;

    /**
     * This method returns all the modules the student
     * is allocated to through allocation to their
     * classes.
     * @param universityId
     * @return List of Modules
     */
    public List<Module> getModulesForStudent(String universityId) {
        return repository.getModulesForStudent(universityId);
    }

    /**
     * Returns all modules taught by
     * a lecturer user.
     * @param uuid
     * @return List of Modules
     */
    public List<Module> getModulesForLecturer(String uuid) {
        return repository.getModulesForLecturer(uuid);
    }

    /**
     * Returns a module by its module code.
     * @param moduleCode
     * @return Module
     */
    public Module getByModuleCode(String moduleCode) {
        return repository.getByModuleCode(moduleCode);
    }

    public List<Module> getModules() {
        return repository.findAll();
    }

    public boolean removeStudentsAllocationsToModules(String studentUniversityId) {
        return repository.removeStudentsAllocationsToModules(studentUniversityId);
    }

    public boolean removeStudentsAllocationToModule(String moduleCode, String studentUniversityId) {
        return repository.removeStudentsAllocationToModule(moduleCode, studentUniversityId);
    }

    public List<ModuleGroup> getModuleGroups(String moduleCode) {
        return repository.getModuleGroups(moduleCode);
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean addAStudentToModule(String moduleCode, String studentId, String groupName) {
        boolean value = true;
        value = value && repository.insertModuleAllocations(moduleCode, Arrays.asList(studentId));
        List<Allocation> allocations = scheduledClassRepository.findClassesOfModuleGroup(moduleCode, groupName)
                .stream()
                .map(sclass -> new Allocation(studentId, sclass.getUuid())).collect(Collectors.toList());
        value = value && allocationRepository.insertAllocations(allocations);
        return value;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean removeStudentFromModule(String moduleCode, String studentId) {
        boolean value = true;
        value = value && repository.removeStudentsAllocationToModule(moduleCode, studentId);
        value = value && allocationRepository.cancelStudentsAllocationsToModulesClasses(moduleCode, studentId);
        return value;
    }

    /**
     * Finds a module by its id.
     * @param id
     * @return Module
     */
    @Override
    public Module findById(Long id) {
        return repository.findById(id);
    }

    /**
     * Saves a module into the database.
     * @param object
     * @return Module
     */
    @Override
    public Module save(Module object) {
        return repository.saveModule(object);
    }

    /**
     * Saves a module into the database
     * and assign students to it.
     * @param object
     * @return boolean
     */
    @Transactional(rollbackFor = DataAccessException.class)
    public Module saveWithStudentsAllocated(Module object, List<String> studentUuids) {
        Module module = repository.saveModule(object);
        repository.insertModuleAllocations(object.getModuleCode(), studentUuids);
        return module;
    }

    @Transactional(rollbackFor = DataAccessException.class)
    public boolean removeModule(String moduleCode) {
        boolean value = true;
        value = value && repository.removeModuleAllocations(moduleCode);
        value = value && allocationRepository.deleteAllocationsToModulesClasses(moduleCode);
        value = value && attendanceRepository.deleteAttendanceRecordsForModule(moduleCode);
        value = value && scheduledClassRepository.deleteModulesClasses(moduleCode);
        value = value && repository.removeModule(moduleCode);
        return value;
    }

    public Module updateModuleDetails(Module module) {
        return repository.updateModuleDetails(module);
    }
}
