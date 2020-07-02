package com.cs2c.project.module.proxy.domain;

import java.util.ArrayList;
import java.util.List;

public class Method {
    private String id;
    private String name;
    private String value;
    private String text;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    private Boolean checked;

    public static List<Method> INSTANCES = new ArrayList<>();
    static {
        INSTANCES.add(new Method("post", "method", "POST", "POST", false));
        INSTANCES.add(new Method("get", "method", "GET", "GET", false));
        INSTANCES.add(new Method("delete", "method", "DELETE", "DELETE", false));
        INSTANCES.add(new Method("put", "method", "PUT", "PUT", false));
        INSTANCES.add(new Method("head", "method", "HEAD", "HEAD", false));
       // INSTANCES.add(new Method("options", "method", "OPTIONS", "OPTIONS"));
    }

    @Override
    public String toString() {
        return "Method{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                ", checked='" + checked + '\'' +
                '}';
    }

    private Method(String id, String name, String value, String text, Boolean checked) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.text = text;
        this.checked = checked;
    }

    private Method() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
