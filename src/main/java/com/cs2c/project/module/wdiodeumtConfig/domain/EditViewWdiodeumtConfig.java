package com.cs2c.project.module.wdiodeumtConfig.domain;

public class EditViewWdiodeumtConfig extends ViewWdiodeumtConfig {
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "EditViewWdiodeumtConfig{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
