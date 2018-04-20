package com.senpure.config;

import com.senpure.base.AppEvn;
import com.senpure.base.util.StringUtil;
import com.senpure.config.read.ExcelReader;
import com.senpure.config.generate.ConfigGenerator;
import com.senpure.config.metadata.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/17 0017.
 */
@SpringBootApplication
public class Boot  implements CommandLineRunner{

    private Logger logger = LoggerFactory.getLogger(Boot.class);

    public static void main(String[] args) {

        SpringApplication.run(Boot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.debug("---------------------------------");
        File file;
        if (args.length == 0) {
           file=new File( AppEvn.getClassRootPath()) ;
        }
        else {
            String path = args[0];
            if (path.startsWith("/")) {
                file = new File(path);
            }
            else {
                int parent = 1;
                parent += StringUtil.count(path, "../");
                File parentFile = new File( AppEvn.getClassRootPath());
                for (int i = 0; i < parent; i++) {
                    parentFile = parentFile.getParentFile();
                }
                path = path.replace("../", "");
                file = new File(parentFile, path);
            }

        }
        if (!file.exists()) {
            logger.error("文件不存在 "+file.getAbsolutePath());
            return;
        }
        logger.debug("file path {}",file.getAbsoluteFile());
         List<Bean> beans= ExcelReader.read(file);

        Config config = new Config();
        config.setJavaFolder(AppEvn.getClassRootPath());
        config.setLuaFolder(AppEvn.getClassRootPath());


        for (Bean bean : beans) {

            bean.setConfig(config);

          //  logger.debug(bean.toString());
            ConfigGenerator.generateJava(bean);
            ConfigGenerator.generateLua(bean);
        }
    }
}
