package com.senpure.config.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/19 0019.
 */
public class Record {


    private List<Value> values=new ArrayList<>();

    public List<Value> getValues() {
        return values;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (Value value : values) {
            sb.append(value.getField().getClassType()).append(" ").append(value.getField().getName()).append(" = ").append(value.getValue());

            sb.append(" ");
        }
        return sb.toString();
    }
}
