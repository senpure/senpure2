package com.senpure.config.metadata;

/**
 * Created by 罗中正 on 2018/4/19 0019.
 */
public class Value {

    private String value;
    private String inJavaCodeValue;
    private Field field;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInJavaCodeValue() {
        return inJavaCodeValue;
    }

    public void setInJavaCodeValue(String inJavaCodeValue) {
        this.inJavaCodeValue = inJavaCodeValue;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
