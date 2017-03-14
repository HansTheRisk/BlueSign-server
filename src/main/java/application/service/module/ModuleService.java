package application.service.module;

import application.domain.module.Module;
import application.repository.allocation.AllocationRepository;
import application.repository.attendance.AttendanceRepository;
import application.repository.module.ModuleRepository;
import application.repository.scheduledClass.ScheduledClassRepository;
import application.service.IdentifiableEntityService;
import application.service.allocation.AllocationService;
import application.service.attendance.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
