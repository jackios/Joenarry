package com.cs2c.project.module.wdiodeumtConfig.domain;

import java.util.Date;

public class ViewWdiodeumtConfig {
    private String w_key;
    private String w_value;
    private String description;
    private Date w_time;

    public ViewWdiodeumtConfig(){}

    public ViewWdiodeumtConfig(String w_key, String w_value, String description, Date w_time) {
        this.w_key = w_key;
        this.w_value = w_value;
        this.description = description;
        this.w_time = w_time;
    }

    public String getW_key() {
        return w_key;
    }

    public void setW_key(String w_key) {
        this.w_key = w_key;
    }

    public String getW_value() {
        return w_value;
    }

    public void setW_value(String w_value) {
        this.w_value = w_value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getW_time() {
        return w_time;
    }

    public void setW_time(Date w_time) {
        this.w_time = w_time;
    }

    @Override
    public String toString() {
        return "ViewWdiodeumtConfig{" +
                "w_key='" + w_key + '\'' +
                ", w_value='" + w_value + '\'' +
                ", description='" + description + '\'' +
                ", w_time=" + w_time +
                '}';
    }
}
