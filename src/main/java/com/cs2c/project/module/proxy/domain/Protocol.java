package com.cs2c.project.module.proxy.domain;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;

public class Protocol {
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

    public static List<Protocol> INSTANCES = new ArrayList<>();
    static {
        INSTANCES.add(new Protocol("http1.0", "protocol", "HTTP/1.0", "HTTP/1.0", false));
        INSTANCES.add(new Protocol("http1.1", "protocol", "HTTP/1.1", "HTTP/1.1", false));
        INSTANCES.add(new Protocol("http2.0", "protocol", "HTTP/2.0", "HTTP/2.0", false));
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

        private Protocol(String id, String name, String value, String text, Boolean checked) {
            this.checked = checked;
            this.id = id;
            this.name = name;
            this.value = value;
            this.text = text;
        }

        private Protocol() {
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

