package com.senpure.generator.controller;

import javafx.util.StringConverter;

import java.io.File;

/**
 * Created by 罗中正 on 2017/6/12.
 */
public class FileConverter extends StringConverter<File> {

    @Override
    public String toString(File object) {
        return object.getName();
    }

    @Override
    public File fromString(String string) {
        return null;
    }
}
