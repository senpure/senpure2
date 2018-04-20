package com.senpure.config;

/**
 * Created by 罗中正 on 2018/4/19 0019.
 */
public class Config {

    private String javaFolder ="";
    private String javaManagerSuffix="Manager";
    private String luaFolder ="";

    public String getJavaFolder() {
        return javaFolder;
    }

    public void setJavaFolder(String javaFolder) {
        this.javaFolder = javaFolder;
    }

    public String getLuaFolder() {
        return luaFolder;
    }

    public void setLuaFolder(String luaFolder) {
        this.luaFolder = luaFolder;
    }

    public String getJavaManagerSuffix() {
        return javaManagerSuffix;
    }

    public void setJavaManagerSuffix(String javaManagerSuffix) {
        this.javaManagerSuffix = javaManagerSuffix;
    }
}
