package com.cs2c.project.module.serviceM.domain;

public class ViewServiceM {
    private Integer id;
    private String service_name;
    private String current_status;
    private String service_description;
    private String service_type;

    public ViewServiceM() {}

    private String enabled;

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "ViewServiceM{" +
                "id=" + id +
                ", service_name='" + service_name + '\'' +
                ", current_status='" + current_status + '\'' +
                ", service_description='" + service_description + '\'' +
                ", service_type='" + service_type + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }

    public ViewServiceM(Integer id, String service_name, String current_status, String service_description, String service_type, String enabled) {
        this.id = id;
        this.service_name = service_name;
        this.current_status = current_status;
        this.service_description = service_description;
        this.service_type = service_type;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getCurrent_status() {
        return current_status;
    }

    public void setCurrent_status(String current_status) {
        this.current_status = current_status;
    }

    public String getService_description() {
        return service_description;
    }

    public void setService_description(String service_description) {
        this.service_description = service_description;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }
}
