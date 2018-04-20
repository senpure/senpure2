package com.senpure.generator.message;

/**
 * Created by 罗中正 on 2017/5/24.
 */
public class Field {

    private boolean hasExplain;
    private String explain;
   // private String originalClassType;
    private String classType;

    private String name;
    private boolean baseField;
    private boolean list;
    private int capacity=16;
    private boolean otherPart;

    //lua 中的实体对象名
    private String luaClassType;
    //自定义对象时的信息
    private Bean bean;

    private String javaType;
    private int index;
    private int writeType;
    private int tag;

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

//    public String getOriginalClassType() {
//        return originalClassType;
//    }
//
//    public void setOriginalClassType(String originalClassType) {
//        this.originalClassType = originalClassType;
//    }

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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Bean getBean() {
        return bean;
    }

    public void setBean(Bean bean) {
        this.bean = bean;
    }


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public int getWriteType() {
        return writeType;
    }

    public void setWriteType(int writeType) {
        this.writeType = writeType;
    }

    @Override
    public String toString() {
        return "Field{" +
                "hasExplain=" + hasExplain +
                ", explain='" + explain + '\'' +
                ", classType='" + classType + '\'' +
                ", name='" + name + '\'' +
                ", baseField=" + baseField +
                ", list=" + list +
                ", capacity=" + capacity +
                '}';
    }
}
