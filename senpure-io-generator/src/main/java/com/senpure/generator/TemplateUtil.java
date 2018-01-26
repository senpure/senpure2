package com.senpure.generator;


import com.senpure.base.AppEvn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Created by 罗中正 on 2017/6/12.
 */
public class TemplateUtil {
    private static Logger logger
            = LoggerFactory.getLogger(TemplateUtil.class);
    private static File templateDir;

    public static File templateDir() {
        if (templateDir != null) {
            return templateDir;
        }
        String root = AppEvn.getCallerRootPath();

        if (AppEvn.callerInJar()) {

            templateDir = new File(root, "template");
        } else {
            templateDir = new File(root, "com/senpure/generator/message");
        }
        logger.debug("模板文件路径{}",templateDir.getAbsolutePath());
        return templateDir;
    }

    public static void main(String[] args) {


    }
}
