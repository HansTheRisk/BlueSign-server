package application;

import application.service.provisioning.ProvisioningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class Initialiser implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    ProvisioningService provisioningService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        provisioningService.initialise();
    }
}
