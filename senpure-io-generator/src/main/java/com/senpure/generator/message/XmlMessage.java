package com.senpure.generator.message;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2017/6/2.
 */
public class XmlMessage {

  //  private Set<String> imports=new HashSet<>();
    private File file;
    private String pack;
    private String id;
    private  String model;
    private List<Bean> beans=new ArrayList<>();
    private int beanNameMaxLen;
    private int messageNameMaxLen;
    private int nameMaxLen;

    private List<Message> messages = new ArrayList<>();

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }

    public List<Bean> getBeans() {
        return beans;
    }

    public void setBeans(List<Bean> beans) {
        this.beans = beans;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBeanNameMaxLen() {
        return beanNameMaxLen;
    }

    public void setBeanNameMaxLen(int beanNameMaxLen) {
        this.beanNameMaxLen = beanNameMaxLen;
    }

    public int getMessageNameMaxLen() {
        return messageNameMaxLen;
    }

    public void setMessageNameMaxLen(int messageNameMaxLen) {
        this.messageNameMaxLen = messageNameMaxLen;
    }

    public int getNameMaxLen() {
        return nameMaxLen;
    }

    public void setNameMaxLen(int nameMaxLen) {
        this.nameMaxLen = nameMaxLen;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
