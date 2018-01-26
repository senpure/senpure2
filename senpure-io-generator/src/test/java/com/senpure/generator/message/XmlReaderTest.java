package com.senpure.generator.message;


import com.senpure.base.AppEvn;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by 罗中正 on 2017/6/2.
 */


public class XmlReaderTest {

    @Test
    public void readhandler() throws IOException {
        String root= AppEvn.getCallerRootPath();
        File file=new File(root, "com/senpure/generator/message/xml/bookMessage.xml");

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        XmlMessage xmlMessage= XmlReader.readXml(file);
        cfg.setClassForTemplateLoading(Generator.class, "/com/senpure/generator/");
        Template t = cfg.getTemplate("message/java/handler.ftl");//指定模板

        for(Message message:xmlMessage.getMessages()) {
            if(message.getType().endsWith("S"))
            {
                File save = new File("E:\\projects\\senpure-parent\\senpure-generator\\src\\main\\java\\com\\senpure\\something\\handler","Message"+message.getName()+"Handler.java");
                Generator.generate(message, t, save);
            }

        }
    }
    @Test
    public  void readBean() throws IOException {

        String root= AppEvn.getCallerRootPath();
        File file=new File(root, "com/senpure/generator/message/xml/bookMessage.xml");

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);

        XmlMessage xmlMessage= XmlReader.readXml(file);
        cfg.setClassForTemplateLoading(Generator.class, "/com/senpure/generator/");
        Template t = cfg.getTemplate("message/java/bean.ftl");//指定模板
        System.out.println(xmlMessage.getBeans().size());
        for(Bean bean:xmlMessage.getBeans()) {
            File save = new File("E:\\projects\\senpure-parent\\senpure-generator\\src\\main\\java\\com\\senpure\\something\\bean",bean.getName()+".java");
            Generator.generate(bean, t, save);
        }
    }

    @Test
    public void readMessage() throws Exception {

      // String path="E:\\projects\\senpure-parent\\senpure-generator\\target\\classes\\com\\senpure\\generator\\message\\xml\\somthing.xml";

      String root= AppEvn.getCallerRootPath();
        File file=new File(root, "com/senpure/generator/message/xml/bookMessage.xml");

       XmlMessage xmlMessage= XmlReader.readXml(file);
        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);


        cfg.setClassForTemplateLoading(Generator.class, "/com/senpure/generator/");
        Template t = cfg.getTemplate("message/java/message.ftl");//指定模板
        System.out.println(xmlMessage.getMessages().size());
       for(Message message:xmlMessage.getMessages()) {
           File save = new File("E:\\projects\\senpure-parent\\senpure-generator\\src\\main\\java\\com\\senpure\\something\\message",message.getType()+message.getName()+"Message.java");
           Generator.generate(message, t, save);
       }
    }
    @Test
    public void readLua() throws IOException {
        String root= AppEvn.getCallerRootPath();
        File file=new File(root, "com/senpure/generator/message/xml/testMessage.xml");

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setSharedVariable("rightPad",new RightPad());
        cfg.setSharedVariable("luaNameStyle",new LuaNameStyle());
        XmlMessage xmlMessage= XmlReader.readXml(file);
        xmlMessage= MessageUtil.convert2Lua(xmlMessage);
        System.out.println(xmlMessage.getMessages());
        cfg.setClassForTemplateLoading(Generator.class, "/com/senpure/generator/");
        Template t = cfg.getTemplate("message/lua/message.ftl");//指定模板

        File save = new File("E:\\xml.lua");
       // Generator.generate(xmlMessage, t, save);
        File handler = new File("E:\\handler.lua");
        Template h = cfg.getTemplate("message/lua/handler.ftl");//指定模板
        Generator.generate(xmlMessage, h, handler);


    }

}