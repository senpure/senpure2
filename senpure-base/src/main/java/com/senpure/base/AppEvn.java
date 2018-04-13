package com.senpure.base;

import com.senpure.base.util.StringUtil;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 应用环境
 * Created by 罗中正 on 2017/5/26.
 */
public class AppEvn {

    private static String classRootPath;

    public static boolean isWindowsOS() {

        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows");
    }

    public static boolean isLinuxOS() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("linux");
    }
    /**
     * 获取class\jar的根路径 如<br>
     * E:\projects\com.senpure.base\target\classes\com\senpure\AppEvn.class ->
     * E:\projects\com.senpure.base\target\classes<br>
     * E:\projects\com.senpure.base\target\<b><i>jar.jar</i></b>\com\senpure\AppEvn.class ->
     * E:\projects\com.senpure.base\target
     *
     * @return
     */
    public static String getClassRootPath(Class clazz) {
        String classRootPath = null;
        try {

            URL url = clazz.getResource("");
            if (url == null) {
                return getClassRootPath();
            }
            URI uri = url.toURI();

            classRootPath = uri.getPath();
            boolean cutPackage = true;
            if (classRootPath == null) {
                // Assert.error("jar can not call this method");
                cutPackage = false;
                classRootPath = getJarRootPath(uri);
                uri = new URI(classRootPath);
                classRootPath = uri.getPath();
            }
            if (isWindowsOS()) {
                int index = classRootPath.indexOf("/");
                if (index == 0) {
                    classRootPath = classRootPath.substring(1);
                }
            }
            classRootPath = classRootPath.replace("/", File.separator);
            if (cutPackage) {
                if (clazz.getPackage() != null) {
                    //System.out.println(clazz.getProtectionDomain().getCodeSource().getLocation().getPath());
                    String packpath = clazz.getPackage().getName();
                    packpath = packpath.replace(".", File.separator);
                    classRootPath = classRootPath.replace(packpath, "");
                }
            }
            while (classRootPath.charAt(classRootPath.length() - 1) == File.separatorChar) {
                classRootPath = classRootPath.substring(0, classRootPath.length() - 1);
            }

        } catch (URISyntaxException e) {

            e.printStackTrace();
        }
        return classRootPath;
    }

    /**
     * 存在多个classpath 时该值不准确,推荐使用带参数的getClassRootPath(Class clazz)
     * @return
     */
    public static String getClassRootPath() {
        if (classRootPath != null) {
            return classRootPath;
        }
        StackTraceElement[] statcks = Thread.currentThread()
                .getStackTrace();
        int count = 1;
        Class clazz = null;
        try {
            do {
                count++;
                StackTraceElement statck = statcks[count];
                   clazz = Class.forName(statck.getClassName());
               // System.out.println("count = "+count+ "---"+statck.getClassName()+"----"+getClassRootPath(clazz));
            }
            while (classInJar(clazz) && count <statcks.length-1 );


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        classRootPath = getClassRootPath(clazz);
        return classRootPath;
    }

    public static String getCallerRootPath() {
        return getClassRootPath(getCallerClass());
    }

    private static Class getCallerClass() {
        StackTraceElement[] statcks = Thread.currentThread()
                .getStackTrace();
        StackTraceElement statck = statcks[3];
        Class clazz = null;
        try {
            clazz = Class.forName(statck.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    /**
     * @param clazz 不能是AppEvn.class
     * @return
     */
    public static boolean classInJar(Class clazz) {

        URL url = clazz.getResource("");
        if (url == null) {
            return true;
        }
        return url.toString().contains("jar:file:");
    }


    public static boolean callerInJar() {
        return classInJar(getCallerClass());
    }

    private static String getJarRootPath(URI uri) {
        String location = uri.toString();
        //System.out.println("location :" + location);
        int index = location.indexOf(".jar!");
        if (index == -1) {
            index = StringUtil.indexOf(location, ".", 1, true);
        }
        location = location.substring(0, index);
        //System.out.println("location :" + location);
        index = StringUtil.indexOf(location, "/", 1, true);
        location = location.substring(0, index);
        //System.out.println("location :" + location);
        location = location.replace("jar:file:", "");
        // System.out.println("location :" + location);
        return location;
    }

    private static String getJarPath(String location) {
        int index = location.indexOf(".jar!") + 4;
        if (index == 3) {
            index = StringUtil.indexOf(location, "!", 1, true);
        }
        location = location.substring(0, index);
        // System.out.println("location :" + location);
        location = location.replace("jar:file:", "");
        //System.out.println("location :" + location);
        return location;

    }

    public static String getClassPath(Class clazz) {
        URL url = clazz.getResource("");
        try {
            URI uri = url.toURI();
            String location = uri.getPath();
            // System.out.println(location);
            if (location == null) {
                location = getJarPath(uri.toString());
                location = new URI(location).getPath();
            } else {
                location += clazz.getSimpleName() + ".class";
            }

            // System.out.println(location);
            if (isWindowsOS()) {
                int index = location.indexOf("/");
                if (index == 0) {
                    location = location.substring(1);
                }
            }
            location = location.replace("/", File.separator);

            while (location.charAt(location.length() - 1) == File.separatorChar) {
                location = location.substring(0, location.length() - 1);
            }
            return location;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getCallerPath() {

        return getClassPath(getCallerClass());
    }

    public static String getPid() {
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            return jvmName.split("@")[0];
        } catch (Throwable ex) {
            return null;
        }
    }
}
