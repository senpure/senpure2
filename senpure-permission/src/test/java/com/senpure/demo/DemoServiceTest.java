package com.senpure.demo;

import com.senpure.PermissionBoot;
import com.senpure.base.util.RandomUtil;
import com.senpure.demo.model.Student;
import com.senpure.demo.service.StudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by 罗中正 on 2017/12/28 0028.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PermissionBoot.class)
public class DemoServiceTest   {

    @Autowired
    StudentService studentService;

    @Test
    public void save() throws Exception {

        Date now = new Date();
        Student student = new Student();
        student.setAge(7);
        student.setClazzId(47348208602447872L);
        student.setCreateDate(new Date(now.getTime()));;
        student.setCreateTime(now.getTime());
        student.setName("name[" +77+ "]" );
        student.setNick("1111222");
        student.setNum(71);
        student.setPhoneNumber("1598312" + RandomUtil.random(1234, 9999));
        studentService.save(student);
    }


}
