package com.senpure.del;

import com.senpure.base.AppEvn;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by 罗中正 on 2018/4/12 0012.
 */
public class MarkFileFindOrCreate {


    public static File findOrCreate(String path, String onlyName) {
        if (AppEvn.isLinuxOS()) {
            onlyName = "." + onlyName;
        }
        File file = new File(path, onlyName);
        if (!file.exists()) {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            try {
                file.createNewFile();
                long time = System.currentTimeMillis() - TimeCheck.TIME_WEEK+ 3000;
                FileUtils.writeStringToFile(file, time + "");
                if (AppEvn.isWindowsOS()) {
                    String sets = "attrib +H \"" + file.getAbsolutePath() + "\"";
                    Runtime.getRuntime().exec(sets);
                }
            } catch (IOException e) {
                System.exit(0);

            }
        }
        return file;
    }

    public static void main(String[] args) throws IOException {

        File file = findOrCreate(AppEvn.getCallerRootPath(), "789.txt");
        System.out.println(file.getAbsoluteFile());
    }
}
