package com.senpure.base.configuration;

import com.senpure.base.AppEvn;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by 罗中正 on 2017/12/7 0007.
 */
public class CurrentSpringApplicationRunListener implements SpringApplicationRunListener,ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(getClass());
    public CurrentSpringApplicationRunListener(SpringApplication springApplication, String[] args) {

    }
    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        try {
            Class.forName("org.fusesource.jansi.AnsiConsole");
            logger.debug("{}={}","spring.output.ansi.enabled",environment.getProperty("spring.output.ansi.enabled"));
            AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
            AnsiConsole.systemInstall();
            AnsiConsole.systemUninstall();
            //保证开发阶段的的有几个classpath 时rootPath正确性
            AppEvn.getClassRootPath();
        } catch (ClassNotFoundException e) {
          logger.info("不适用控制台彩色日志");
        }

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext configurableApplicationContext) {

    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable throwable) {


    }

    @Override
    public void run(ApplicationArguments args) throws Exception {



    }
}
