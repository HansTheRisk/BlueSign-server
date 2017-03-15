package application.scheduled;

import application.domain.accessCode.AccessCode;
import application.domain.scheduledClass.ScheduledClass;
import application.service.accessCode.AccessCodeService;
import application.service.scheduledClass.ScheduledClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a scheduled task that generates class access codes every four minutes.
 */
@Component
public class ScheduledTask {

    @Autowired
    private ScheduledClassService classService;

    @Autowired
    private AccessCodeService accessCodeService;

    /**
     * This method regenerates codes every four minutes.
     */
    //    @Transactional
//    @Scheduled(fixedDelay = 240000)
    public void regenerateCodes() {
        List<ScheduledClass> classes = classService.getCurrentlyRunningClasses();
        List<Integer> codes = generateCodes(classes.size()).stream().collect(Collectors.toList());
        List<AccessCode> accessCodes = new ArrayList<>();

        classes.forEach(scheduledClass -> {
            accessCodes.add(new AccessCode(scheduledClass.getUuid(), codes.get(classes.indexOf(scheduledClass))));
        });
        accessCodeService.deleteCodes();
        accessCodeService.insertCodes(accessCodes);
    }

    /**
     * This method generates a number of unique codes
     * for the given int amount.
     * @param numOfCodes
     * @return Set of Integers
     */
    private Set<Integer> generateCodes(int numOfCodes) {
        Random random = new Random();
        Set<Integer> codes = new HashSet<>();
        while (codes.size() < numOfCodes) {
            codes.add(random.nextInt(((9999 - 1000))+1)+1000);
        }
        return codes;
    }

}
