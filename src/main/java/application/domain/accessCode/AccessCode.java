package application.domain.accessCode;

/**
 * This domain class represents an access code for a class.
 */
public class AccessCode {

    private String classUuid;
    private int code;

    public AccessCode() {

    }

    public AccessCode(String classUuid, int code) {
        this.classUuid = classUuid;
        this.code = code;
    }

    public String getClassUuid() {
        return classUuid;
    }

    public void setClassUuid(String classUuid) {
        this.classUuid = classUuid;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
