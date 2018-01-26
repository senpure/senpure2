package com.senpure.generator.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 罗中正 on 2017/6/1.
 */
public class Bean {



    private String pack;
    private String name;
    private List<Field> fields=new ArrayList<>();
    private boolean hasExplain;
    private String explain;
    //引用其他的去重
    private Map<String, Field> singleField = new HashMap<>();
    //是否包含其他bean
    private boolean hasBean=false;

    //字段的长度格式化toString使用
    private int fieldMaxLen=0;
    private boolean generate=true;

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

    @Override
    public String toString() {
        return "Bean{" +
                "pack='" + pack + '\'' +
                ", name='" + name + '\'' +
                ", fields=" + fields +
                ", hasExplain=" + hasExplain +
                ", explain='" + explain + '\'' +
                '}';
    }
}
