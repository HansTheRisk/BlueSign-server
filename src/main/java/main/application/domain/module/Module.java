package main.application.domain.module;

import main.application.domain.entity.IdentifiableEntity;

public class Module implements IdentifiableEntity {

    private Long id;

    private String title;

    private String moduleCode;

    private String lecturerUuid;

    public Module() {

    }

    public Module(String title, String moduleCode, String lecturerUuid) {
        this.title = title;
        this.moduleCode = moduleCode;
        this.lecturerUuid = lecturerUuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getLecturerUuid() {
        return lecturerUuid;
    }

    public void setLecturerUuid(String lecturerUuid) {
        this.lecturerUuid = lecturerUuid;
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
