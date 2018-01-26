package com.senpure.generator.model;

import com.senpure.generator.message.Bean;
import com.senpure.generator.message.XmlMessage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by 罗中正 on 2017/6/9.
 */
public class MessageData {
    private Bean message;

    private XmlMessage xmlMessage;
    private boolean messageData;
    private String pack;
    private StringProperty name;
    private BooleanProperty generate;
    private StringProperty type;

    public MessageData(Bean message, XmlMessage xmlMessage) {
        this.message = message;
        name = new SimpleStringProperty("[" + message.getType() + "]" + message.getName());
        pack = message.getPack();

        generate = new SimpleBooleanProperty(true);
        type = new SimpleStringProperty(message.getType());
        messageData = !"NA".equalsIgnoreCase(message.getType());
        this.xmlMessage = xmlMessage;
    }

    public Bean getMessage() {
        return message;
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public boolean isGenerate() {
        return generate.get();
    }

    public BooleanProperty generateProperty() {
        return generate;
    }

    public void setGenerate(boolean generate) {
        this.generate.set(generate);
        this.message.setGenerate(generate);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public boolean isMessageData() {
        return messageData;
    }

    public void setMessageData(boolean messageData) {
        this.messageData = messageData;
    }

    public String getPack() {
        return pack;
    }

    public void setPack(String pack) {
        this.pack = pack;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageData that = (MessageData) o;
        if (getPack() != null ? !getPack().equals(that.getPack()) : that.getPack() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) {
            return false;
        }
        return getType() != null ? getType().equals(that.getType()) : that.getType() == null;
    }

    @Override
    public int hashCode() {
        int result = getPack() != null ? getPack().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        return result;
    }

    public XmlMessage getXmlMessage() {
        return xmlMessage;
    }

    public void setXmlMessage(XmlMessage xmlMessage) {
        this.xmlMessage = xmlMessage;
    }
}
