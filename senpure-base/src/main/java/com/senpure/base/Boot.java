package com.senpure.base;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Created by 罗中正 on 2017/5/26.
 */
public class Boot {
    public static void main(String[] args) throws URISyntaxException {

        Path path = Paths.get("");
        System.out.println("path:" + path.toAbsolutePath());
        System.out.println("AppEvn.getClassRootPath():" + AppEvn.getClassRootPath());
        System.out.println("AppEvn.getClassRootPath():" + AppEvn.getCallerRootPath());

        System.out.println("AppEvn.class in jar:" + AppEvn.callerInJar());

    }
}
