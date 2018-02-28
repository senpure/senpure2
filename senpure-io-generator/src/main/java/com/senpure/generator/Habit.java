package com.senpure.generator;


import com.senpure.base.AppEvn;

import java.io.File;
import java.io.Serializable;

/**
 * Created by 罗中正 on 2017/6/12.
 */
public class Habit implements Serializable {
    private String javaServerCodeRootPath = AppEvn.getCallerRootPath();
    private String javaServerCodePathChooserPath=new File(javaServerCodeRootPath).getParent();
    private String javaAICodeRootPath = javaServerCodeRootPath;
    private String javaAICodePathChooserPath=javaServerCodePathChooserPath;
    private String messageChooserPath=javaServerCodePathChooserPath;
    private String messageDirectoryChooserPath=javaServerCodePathChooserPath;


    private String javaBeanTemplate = "bean.ftl";
    private String javaMessageTemplate = "message.ftl";
    private String javaHandlerTemplate = "handler.ftl";
    private boolean javaHandlerCover=false;

    private String luaCodeRootPath = javaServerCodeRootPath;
    private String luaCodePathChooserPath=javaServerCodePathChooserPath;
    private boolean luaHandlerCover=true;
    private String luaMessageTemplate = "message.ftl";
    private String luaHandlerTemplate = "handler.ftl";


    public String getJavaServerCodeRootPath() {
        return javaServerCodeRootPath;
    }

    public void setJavaServerCodeRootPath(String javaServerCodeRootPath) {
        this.javaServerCodeRootPath = javaServerCodeRootPath;
    }

    public String getJavaServerCodePathChooserPath() {
        return javaServerCodePathChooserPath;
    }

    public void setJavaServerCodePathChooserPath(String javaServerCodePathChooserPath) {
        this.javaServerCodePathChooserPath = javaServerCodePathChooserPath;
    }

    public String getJavaAICodeRootPath() {
        return javaAICodeRootPath;
    }

    public void setJavaAICodeRootPath(String javaAICodeRootPath) {
        this.javaAICodeRootPath = javaAICodeRootPath;
    }

    public String getJavaAICodePathChooserPath() {
        return javaAICodePathChooserPath;
    }

    public void setJavaAICodePathChooserPath(String javaAICodePathChooserPath) {
        this.javaAICodePathChooserPath = javaAICodePathChooserPath;
    }

    public String getMessageChooserPath() {
        return messageChooserPath;
    }

    public void setMessageChooserPath(String messageChooserPath) {
        this.messageChooserPath = messageChooserPath;
    }

    public String getMessageDirectoryChooserPath() {
        return messageDirectoryChooserPath;
    }

    public void setMessageDirectoryChooserPath(String messageDirectoryChooserPath) {
        this.messageDirectoryChooserPath = messageDirectoryChooserPath;
    }

    public String getJavaBeanTemplate() {
        return javaBeanTemplate;
    }

    public void setJavaBeanTemplate(String javaBeanTemplate) {
        this.javaBeanTemplate = javaBeanTemplate;
    }

    public String getJavaMessageTemplate() {
        return javaMessageTemplate;
    }

    public void setJavaMessageTemplate(String javaMessageTemplate) {
        this.javaMessageTemplate = javaMessageTemplate;
    }

    public String getJavaHandlerTemplate() {
        return javaHandlerTemplate;
    }

    public void setJavaHandlerTemplate(String javaHandlerTemplate) {
        this.javaHandlerTemplate = javaHandlerTemplate;
    }

    public boolean isJavaHandlerCover() {
        return javaHandlerCover;
    }

    public void setJavaHandlerCover(boolean javaHandlerCover) {
        this.javaHandlerCover = javaHandlerCover;
    }

    public String getLuaCodeRootPath() {
        return luaCodeRootPath;
    }

    public void setLuaCodeRootPath(String luaCodeRootPath) {
        this.luaCodeRootPath = luaCodeRootPath;
    }

    public String getLuaCodePathChooserPath() {
        return luaCodePathChooserPath;
    }

    public void setLuaCodePathChooserPath(String luaCodePathChooserPath) {
        this.luaCodePathChooserPath = luaCodePathChooserPath;
    }

    public boolean isLuaHandlerCover() {
        return luaHandlerCover;
    }

    public void setLuaHandlerCover(boolean luaHandlerCover) {
        this.luaHandlerCover = luaHandlerCover;
    }

    public String getLuaMessageTemplate() {
        return luaMessageTemplate;
    }

    public void setLuaMessageTemplate(String luaMessageTemplate) {
        this.luaMessageTemplate = luaMessageTemplate;
    }

    public String getLuaHandlerTemplate() {
        return luaHandlerTemplate;
    }

    public void setLuaHandlerTemplate(String luaHandlerTemplate) {
        this.luaHandlerTemplate = luaHandlerTemplate;
    }
}
