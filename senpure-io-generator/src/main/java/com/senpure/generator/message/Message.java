package com.senpure.generator.message;

/**
 * Created by 罗中正 on 2017/5/24.
 */
public class Message extends  Bean {
    private int id;
    private String type;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
