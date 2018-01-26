package com.senpure.generator.controller;

import com.senpure.generator.appender.TextAreaAppender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by 罗中正 on 2017/6/9.
 */
public class Welcome {
    private static Logger logger = LoggerFactory.getLogger(Welcome.class);
    private static  void sleep()
    {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public  static  void  welcome()
    {
        logger.debug("欢迎使用代码生成工具");
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.debug("欢迎使用代码生成工具");
                sleep();
                logger.debug("我是MC子龙");
                sleep();
                logger.debug("一首刀山火海送给大家");
                sleep();
                logger.debug("刀 怒斩雪翼雕");
                sleep();
                logger.debug("山 豪迈冲云霄");
                sleep();
                logger.debug("长 坂坡在燃烧");
                sleep();
                logger.debug("我 直播砍曹操");
                sleep();
                TextAreaAppender.celar();
                logger.debug("欢迎使用代码生成工具");
            }
        });
    }
}
