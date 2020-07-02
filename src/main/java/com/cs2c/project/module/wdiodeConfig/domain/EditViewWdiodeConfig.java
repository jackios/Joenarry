package com.cs2c.project.module.wdiodeConfig.domain;

public class EditViewWdiodeConfig extends ViewWdiodeConfig{
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return super.toString() + "EditViewWdiodeConfig{" +
                "comment='" + comment + '\'' +
                '}';
    }
}
