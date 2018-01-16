package com.senpure.demo.init;

import com.senpure.base.PermissionConstant;
import com.senpure.base.model.SystemValue;
import com.senpure.base.service.SystemValueService;
import com.senpure.base.spring.SpringContextRefreshEvent;
import com.senpure.base.util.RandomUtil;
import com.senpure.base.util.TimeCalculator;
import com.senpure.demo.model.Clazz;
import com.senpure.demo.model.Student;
import com.senpure.demo.service.ClazzService;
import com.senpure.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 罗中正 on 2017/10/20.
 */
@Component
public class DemoDataGenerator extends SpringContextRefreshEvent {
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private SystemValueService systemValueService;

    private String[] nicks = {"齐天大圣", "狗蛋", "华阳乔布斯",
            "上好佳", "葫芦岛吴奇隆", "白良子", "西门粗吹嘘",
            "北京赵子龙", "三国战绩", "豹子头"
    };


    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {


        SystemValue systemValue = systemValueService.findByKey("demo.student.create");
        if (systemValue == null || !"true".equals(systemValue.getValue())) {
            int name = RandomUtil.random(1, 9);
            Date now = new Date(System.currentTimeMillis());
            TimeCalculator.toAddDay(now, -365 * 5);
            for (int i = 0; i < 5; i++) {
                TimeCalculator.toAddDay(now, RandomUtil.random(1, 5));
                Clazz clazz = new Clazz();
                clazz.setAge(i + 1);
                clazz.setNum(1);


                clazz.setCreateTime(now.getTime());
                clazz.setCreateDate(new Date(now.getTime()));
                clazzService.save(clazz);
                List<Student> students = new ArrayList<>(128);
                for (int j = 0; j < 86 - i; j++) {
                    TimeCalculator.toAddDay(now, RandomUtil.random(1, 5));
                    Student student = new Student();
                    student.setAge(RandomUtil.random(12, 16));
                    student.setClazzId(clazz.getId());
                    student.setCreateDate(new Date(now.getTime()));
                    student.setCreateTime(now.getTime());
                    student.setName("name[" + name + "]" + i + j);
                    student.setNick(nicks[RandomUtil.random(nicks.length)]);
                    student.setNum(j + 1);
                    student.setGood(j % 3 == 0);
                    student.setPhoneNumber("1598312" + RandomUtil.random(1234, 9999));
                    students.add(student);
                }
                studentService.save(students);
            }
            if (systemValue == null) {
                systemValue = new SystemValue();
                systemValue.setKey("demo.student.create");
                systemValue.setValue("true");
                systemValue.setDescription("标记是否创建了demo数据");
                systemValue.setType(PermissionConstant.VALUE_TYPE_SYSTEM);
                systemValueService.save(systemValue);
            } else {
                systemValue.setValue("true");
                systemValueService.update(systemValue);
            }


        }
    }
}
