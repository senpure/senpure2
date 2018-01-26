package com.senpure.generator;

import com.alibaba.fastjson.JSON;
import com.senpure.base.AppEvn;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Created by 罗中正 on 2017/6/12.
 */
public class HabitUtil {

    private  static Logger logger = LoggerFactory.getLogger(HabitUtil.class);
    public static Habit loadHabit()
    {
        File save=new File(AppEvn.getCallerRootPath(),"config/habit.json");

        logger.debug("行为配置文件路径{}",save.getAbsolutePath());
        if(!save.exists())
        {
            Habit habit=new Habit();
            return habit;
        }
        try {
            String config= FileUtils.readFileToString(save);
            return JSON.parseObject(config, Habit.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }

    public static  void saveHabit(Habit habit)
    {
        String json = JSON.toJSONString(habit);
        File save=new File(AppEvn.getCallerRootPath(),"config/habit.json");
        if(!save.getParentFile().exists())
        {
            save.getParentFile().mkdirs();
        }
        try {
            FileUtils.writeStringToFile(save,json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Habit habit=new Habit();
        habit.setJavaBeanTemplate("123456789");
        saveHabit(habit);
        habit=loadHabit();
        System.out.println(habit);
    }
}
