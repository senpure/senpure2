package com.senpure.base.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Created by 罗中正 on 2017/12/7 0007.
 */
public class CurrentSpringApplicationRunListener2 implements SpringApplicationRunListener,ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(getClass());
    public CurrentSpringApplicationRunListener2(SpringApplication springApplication, String[] args) {

    }
    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {


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
