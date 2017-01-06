package main.application.domain.student;

import main.application.domain.entity.identifiable.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="student")
public class Student implements IdentifiableEntity {

    @Id
    @Column(name="id")
    private Long id;
    @Column(name="university_id")
    private String universityId;
    @Column(name="name")
    private String name;
    @Column(name="surname")
    private String surname;
    @Column(name="pin_salt")
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
