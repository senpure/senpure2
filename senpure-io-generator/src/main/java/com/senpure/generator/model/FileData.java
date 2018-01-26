package com.senpure.generator.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.File;

/**
 * Created by 罗中正 on 2017/6/9.
 */
public class FileData {

    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileData(File file) {
        this.file = file;
        name = new SimpleStringProperty(file.getName());
        path = new SimpleStringProperty(file.getAbsolutePath());
    }

    private StringProperty name;

    private StringProperty path;

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPath() {
        return path.get();
    }

    public StringProperty pathProperty() {
        return path;
    }

    public void setPath(String path) {
        this.path.set(path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileData fileData = (FileData) o;

        return getPath() != null ? getPath().equals(fileData.getPath()) : fileData.getPath() == null;
    }

    @Override
    public int hashCode() {
        return getPath() != null ? getPath().hashCode() : 0;
    }
}
