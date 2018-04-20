package com.senpure.config.generate;

import com.senpure.config.Config;
import com.senpure.config.metadata.Bean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by 罗中正 on 2018/4/19 0019.
 */
public class ConfigGenerator {

    private static Logger logger = LoggerFactory.getLogger(ConfigGenerator.class);

    public static void generateJava(Bean bean) throws IOException {
        Config config= bean.getConfig();
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);


            cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "java"));

            Template template = cfg.getTemplate("bean.ftl");
            File file = new File(config.getJavaFolder(), bean.getJavaPack().replace(".", File.separator)
                    //  + File.separator + "bean"
                    + File.separator + bean.getName() + ".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            logger.debug("file path {}", file.getAbsoluteFile());
            Generator.generate(bean, template, file);
            template = cfg.getTemplate("beanManager.ftl");

            file = new File(config.getJavaFolder(), bean.getJavaPack().replace(".", File.separator)
                    //  + File.separator + "bean"
                    + File.separator + bean.getName() + config.getJavaManagerSuffix() + ".java");
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Generator.generate(bean, template, file);

        // Generator.generate(bean);
    }

    public static void generateLua(Bean bean) throws IOException {
        Config config=bean.getConfig();
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setDirectoryForTemplateLoading(new File(TemplateUtil.templateDir(), "lua"));
        Template template = cfg.getTemplate("bean.ftl");
        File file = new File(config.getLuaFolder(),  File.separator + bean.getName() + ".lua");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Generator.generate(bean, template, file);
    }
}
