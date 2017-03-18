package application.service.provisioning;

import application.domain.user.User;
import application.repository.provisioning.ProvisioningRepository;
import application.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProvisioningService {
    @Autowired
    private ProvisioningRepository repository;
    @Autowired
    private UserService userService;

    public void initialise() {
        repository.createUserTable();
        repository.createStudentTable();
        repository.crateModuleTable();
        repository.createClassTable();
        repository.createAllocationTable();
        repository.createAttendanceTable();
        repository.createModuleStudentTable();
        repository.createIpRangeTable();
        repository.createAccessCodeTable();

        User none = new User();
        none.setUsername("NONE");
        none.setName("NONE");
        none.setEmail("NONE");
        none.setPassword("NONE");
        none.setSurname("NONE");
        none.setRole("ROLE_LECTURER");

        User adminno = new User();
        adminno.setUsername("adminno");
        adminno.setName("admin");
        adminno.setEmail("n/a");
        adminno.setPassword("verysecretAdminPasswd123");
        adminno.setSurname("adminus");
        adminno.setRole("ROLE_ADMIN");

        if(repository.getNoneUser() == null) {
            userService.save(none);
        }

        if(repository.getAdminnoUser() == null) {
            userService.save(adminno);
        }
    }
}
