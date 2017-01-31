package main.application.scheduled;

import main.application.domain.accessCode.AccessCode;
import main.application.domain.scheduledClass.ScheduledClass;
import main.application.service.accessCode.AccessCodeService;
import main.application.service.scheduledClass.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

//@Component
//public class ScheduledTask {
//
//    @Autowired
//    private ScheduledClassService classService;
//
//    @Autowired
//    private AccessCodeService accessCodeService;
//
//    @Transactional
//    @Scheduled(fixedDelay = 240000)
//    public void regenerateCodes() {
//        List<ScheduledClass> classes = classService.findCurrentlyRunningClasses();
//        List<Integer> codes = generateCodes(classes.size()).stream().collect(Collectors.toList());
//        List<AccessCode> accessCodes = new ArrayList<>();
//
//        classes.forEach(scheduledClass -> {
//            accessCodes.add(new AccessCode(scheduledClass.getUuid(), codes.get(classes.indexOf(scheduledClass))));
//        });
//        accessCodeService.deleteCodes();
//        accessCodeService.insertCodes(accessCodes);
//    }
//
//    private Set<Integer> generateCodes(int numOfCodes) {
//        Random random = new Random();
//        Set<Integer> codes = new HashSet<>();
//        while (codes.size() < numOfCodes) {
//            codes.add(random.nextInt(((9999 - 1000))+1)+1000);
//        }
//        return codes;
//    }
//
//}
