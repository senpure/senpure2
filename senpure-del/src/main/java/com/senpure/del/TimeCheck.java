package com.senpure.del;

import com.senpure.base.AppEvn;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 罗中正 on 2018/4/12 0012.
 */
public class TimeCheck {


    public static long TIME_WEEK = 7 * 24 * 60 * 60 * 1000;

    public static void check(DelContext delContext) {
        if (System.currentTimeMillis() - delContext.getLastSuccessTime() >= TIME_WEEK) {
          String str=  DelSelf.delSelf();
            System.out.println(str);
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {



        System.out.println("-------------");
        File file = MarkFileFindOrCreate.findOrCreate(AppEvn.getCallerRootPath(), "111.txt");


        String time = FileUtils.readFileToString(file);
        DelContext delContext = new DelContext();
        delContext.setLastSuccessTime(Long.parseLong(time));

        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            check(delContext);
            }
        },0,1000);

        System.out.println(AppEvn.getClassRootPath(TimeCheck.class));
        Thread.sleep(50000);
    }
}
