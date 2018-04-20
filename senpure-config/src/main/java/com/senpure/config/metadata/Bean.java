package com.senpure.config.metadata;

import com.senpure.config.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/19 0019.
 */
public class Bean {

    private String name;
    private String javaPack;
    private List<Field> fields = new ArrayList<>();
    private String explain;

    private Field id;
    private List<Record> records = new ArrayList<>();

    private Config config;

    public boolean isHasExplain() {
        return explain != null && explain.length() > 0;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJavaPack() {
        return javaPack;
    }

    public void setJavaPack(String javaPack) {
        this.javaPack = javaPack;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public Field getId() {
        return id;
    }

    public void setId(Field id) {
        this.id = id;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
