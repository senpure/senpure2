package com.senpure.generator.message;

/**
 * Created by 罗中正 on 2017/5/24.
 */
public class Field {

    private boolean hasExplain;
    private String explain;
    private String originalClassType;
    private String classType;

    private String name;
    private boolean baseField;
    private boolean list;
    private int capacity;
    private boolean otherPart;
    //lua 中的实体对象名
    private String luaClassType;


    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
        if (explain != null && explain.trim().length() > 0) {
            hasExplain = true;
        }
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBaseField() {
        return baseField;
    }

    public void setBaseField(boolean baseField) {
        this.baseField = baseField;
    }

    public boolean isHasExplain() {
        return hasExplain;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getOriginalClassType() {
        return originalClassType;
    }

    public void setOriginalClassType(String originalClassType) {
        this.originalClassType = originalClassType;
    }

    public boolean isOtherPart() {
        return otherPart;
    }

    public void setOtherPart(boolean otherPart) {
        this.otherPart = otherPart;
    }

    public String getLuaClassType() {
        return luaClassType;
    }

    public void setLuaClassType(String luaClassType) {
        this.luaClassType = luaClassType;
    }

    @Override
    public String toString() {
        return "Field{" +
                "hasExplain=" + hasExplain +
                ", explain='" + explain + '\'' +
                ", originalClassType='" + originalClassType + '\'' +
                ", classType='" + classType + '\'' +
                ", name='" + name + '\'' +
                ", baseField=" + baseField +
                ", list=" + list +
                ", capacity=" + capacity +
                '}';
    }
}
