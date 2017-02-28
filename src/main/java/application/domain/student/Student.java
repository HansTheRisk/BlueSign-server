package application.domain.student;

import application.domain.entity.IdentifiableEntity;

/**
 * This class represents Student database rows.
 */
public class Student implements IdentifiableEntity {

    private Long id;
    private String universityId;
    private String name;
    private String surname;
    private String email;
    private String pin;

    public Student() {

    }

    public Student(String universityId, String name, String surname) {
        this.universityId = universityId;
        this.name = name;
        this.surname = surname;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
