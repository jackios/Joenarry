package com.cs2c.project.module.proxy.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

public class Element_Type {
    private String id;
    private String name;
    private String value;
    private String text;

    private Boolean checked;

    public static List<Element_Type> INSTANCES = new ArrayList<>();
    static {
        INSTANCES.add(new Element_Type("json", "element_type", "json", "json", false));
        INSTANCES.add(new Element_Type("xml", "element_type", "xml", "xml", false));
    }

    @Override
    public String toString() {
        return "Method{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    private Element_Type(String id, String name, String value, String text, Boolean checked) {
        this.checked = checked;
        this.id = id;
        this.name = name;
        this.value = value;
        this.text = text;
    }

    private Element_Type() {
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


    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

