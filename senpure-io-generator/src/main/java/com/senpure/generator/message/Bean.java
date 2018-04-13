package com.senpure.generator.message;

import java.util.*;

/**
 * Created by 罗中正 on 2017/6/1.
 */
public class Bean {


    private String name;
    private String pack;
    private String model;
    private String javaPack;


    private String luaNamespace = "";
    private List<Field> fields = new ArrayList<>();
    private boolean hasExplain;
    private String explain;
    //引用其他的去重
    private Map<String, Field> singleField = new HashMap<>();
    //是否包含其他bean
    private boolean hasBean = false;

    //字段的长度格式化toString使用
    private int fieldMaxLen = 0;
    private boolean generate = true;

    public String getType() {
        return "NA";
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }


    public boolean isHasExplain() {
        return hasExplain;
    }


    public String getExplain() {
        return explain;
    }

    public Map<String, Field> getSingleField() {
        return singleField;
    }

    public void setSingleField(Map<String, Field> singleField) {
        this.singleField = singleField;
    }

    public void setExplain(String explain) {
        this.explain = explain;
        if (explain != null && explain.trim().length() > 0) {
            hasExplain = true;
        }
    }

    public boolean isHasBean() {
        return hasBean;
    }

    public void setHasBean(boolean hasBean) {
        this.hasBean = hasBean;
    }

    public int getFieldMaxLen() {
        return fieldMaxLen;
    }

    public void setFieldMaxLen(int fieldMaxLen) {
        this.fieldMaxLen = fieldMaxLen;
    }

    public boolean isGenerate() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate = generate;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getJavaPack() {
        return javaPack;
    }

    public void setJavaPack(String javaPack) {
        this.javaPack = javaPack;
    }


    public String getLuaNamespace() {
        return luaNamespace;
    }

    public void setLuaNamespace(String luaNamespace) {
        this.luaNamespace = luaNamespace;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Bean bean = (Bean) o;
        return Objects.equals(getName(), bean.getName()) &&
                Objects.equals(getType(), bean.getType()) &&
                Objects.equals(getPack(), bean.getPack()) &&
                Objects.equals(getModel(), bean.getModel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getPack(), getModel());
    }

    @Override
    public String toString() {
        return "Bean{" +
                "type='" + getType() + '\'' +
                ",name='" + name + '\'' +
                ", pack='" + pack + '\'' +
                '}';
    }
}
