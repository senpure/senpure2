package com.senpure.generator.message;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2017/5/24.
 */
public class Generator {

    public static  void generate(Object object,   Template template ,File file)
    {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            template.process(object, new OutputStreamWriter(fos, "utf-8"));
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException, TemplateException {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);


        cfg.setClassForTemplateLoading(Generator.class, "/com/senpure/generator/");
        //指定模板
        Template t = cfg.getTemplate("message/java/message.ftl");
        File file = new File("e:/UserMessage.java");

        FileOutputStream fos = new FileOutputStream(file);
        Message message = new Message();
        message.setPack("com.senpue.message");
        message.setName("User");
        message.setExplain("这是一个消息");
        message.setId(123456789);
        List<Field> fields = new ArrayList();
        Field field = new Field();
        field.setClassType("int");
        field.setExplain("年龄");
        field.setName("age");
        fields.add(field);
        field = new Field();
        field.setClassType("String");
        field.setExplain("姓名");
        field.setName("name");
        fields.add(field);

        field = new Field();
        field.setClassType("BeanMessage");
        field.setExplain("bean");
        field.setName("bean");

        fields.add(field);

        field = new Field();
        field.setClassType("BeanMessage");
        field.setExplain("beans");
        field.setName("beans");
        field.setList(true);
        fields.add(field);
        message.setFields(fields);

        t.process(message, new OutputStreamWriter(fos, "utf-8"));
    }
}
