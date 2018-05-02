package com.senpure.config;

import com.senpure.base.AppEvn;
import com.senpure.config.config.ConfigProperties;
import com.senpure.config.generate.ConfigGenerator;
import com.senpure.config.metadata.Bean;
import com.senpure.config.read.ExcelReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/17 0017.
 */
@SpringBootApplication
public class Boot implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(Boot.class);

    @Autowired
    private ConfigProperties config;

    private ConfigProperties defaultConfig = new ConfigProperties();

    public static void main(String[] args) {

       // AppEvn.getClassRootPath();
        SpringApplication.run(Boot.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        logger.debug(config.toString());
        logger.debug(defaultConfig.toString());
        if (config.getExcelPath() == null || config.getExcelPath().trim().length() == 0) {
            config.setExcelPath(defaultConfig.getExcelPath());
        }
        if (config.getJavaFolder() == null || config.getJavaFolder().trim().length() == 0) {
            config.setJavaFolder(defaultConfig.getJavaFolder());
        }
        if (config.getJavaManagerSuffix() == null || config.getJavaManagerSuffix().trim().length() == 0) {
            config.setJavaManagerSuffix(config.getJavaManagerSuffix());
        }
        if (config.getLuaFolder() == null || config.getJavaFolder().trim().length() == 0) {
            config.setLuaFolder(defaultConfig.getLuaFolder());
        }

        File file = new File(config.getExcelPath());
        logger.debug("excel path {}", file.getAbsoluteFile());
        if (!file.exists()) {
            logger.error("文件不存在{}_ ", file.getAbsolutePath());
            return;
        }

        List<Bean> beans = ExcelReader.read(file);

        if (beans.size() == 0) {
            logger.info("没有找到excel文件");
        }
        for (Bean bean : beans) {
            bean.setConfig(config);
            //  logger.debug(bean.toString());
            ConfigGenerator.generateJava(bean);
            ConfigGenerator.generateLua(bean);
        }
    }
    public  static  void markClass()
    {
        AppEvn.markClassRootPath();
    }
}
