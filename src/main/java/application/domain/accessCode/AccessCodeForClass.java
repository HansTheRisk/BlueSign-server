package application.domain.accessCode;

import application.domain.scheduledClass.ScheduledClass;

/**
 * This domain class represents a correlation between an access code
 * for a class and the class itself.
 * It provides more details than the AccessCode class.
 */
public class AccessCodeForClass {

    private AccessCode accessCode;
    private ScheduledClass scheduledClass;

    public AccessCodeForClass(AccessCode accessCode, ScheduledClass scheduledClass) {
        this.accessCode = accessCode;
        this.scheduledClass = scheduledClass;
    }

    public AccessCode getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(AccessCode accessCode) {
        this.accessCode = accessCode;
    }

    public ScheduledClass getScheduledClass() {
        return scheduledClass;
    }

    public void setScheduledClass(ScheduledClass scheduledClass) {
        this.scheduledClass = scheduledClass;
    }
}
