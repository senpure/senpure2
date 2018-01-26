package com.senpure.generator.message;

import com.senpure.base.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by 罗中正 on 2017/6/2.
 */
public class XmlReader {

    private static String[] baseFields = {"int", "boolean", "byte", "short", "String", "long", "float", "double"};
    private static Logger logger = LoggerFactory.getLogger(XmlReader.class);

    public static XmlMessage readXml(File file) {

        SAXReader reader = new SAXReader();

        Document document = null;
        try {
            document = reader.read(new FileInputStream(file));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        XmlMessage xmlMessage = new XmlMessage();

        Element root = document.getRootElement();
        String pack = root.attributeValue("package");
        xmlMessage.setPack(pack);
        String id = root.attributeValue("id");
        xmlMessage.setId(id);
        List<Element> beanElemets = root.elements("bean");

        for (Element element : beanElemets) {
            Bean bean = new Bean();
            bean.setPack(pack);
            String name=element.attributeValue("name");
            name=StringUtil.toUpperFirstLetter(name);
            bean.setName(name);
            bean.setExplain(element.attributeValue("explain"));
            attr(element, bean);
            xmlMessage.getBeans().add(bean);
        }
        List<Element> messageElemets = root.elements("message");

        for (Element element : messageElemets) {
            Message message = new Message();
            message.setPack(pack);
            String name=element.attributeValue("name");
            name=StringUtil.toUpperFirstLetter(name);
            message.setName(name);
            message.setExplain(element.attributeValue("explain"));
            String type=element.attributeValue("type");
            type=type.toUpperCase();
            message.setType(type);
            attr(element, message);
            String mid = element.attributeValue("id");
            message.setId(Integer.parseInt(id + mid));
            xmlMessage.getMessages().add(message);
        }

        //logger.debug(xmlMessage.toString());
        return xmlMessage;
    }

    private static void attr(Element element, Bean bean) {

        List<Element> elements = element.elements();
        for (Element e : elements) {
            Field field = new Field();
            field.setName(e.attributeValue("name"));
            String type = e.attributeValue("type");
            field.setOriginalClassType(type);
            bean.getSingleField().put(type, field);
            field.setBaseField(baseField(type));
            int index = StringUtil.indexOf(type, ".", 1, true);
            if (index > -1) {
                type = type.substring(index + 1);
                field.setOtherPart(true);
            }
            field.setClassType(type);
            field.setList("list".equals(e.getName()));
            field.setExplain(e.attributeValue("explain"));

            if (!field.isBaseField()) {
                bean.setHasBean(true);
            }
            int fieldLen=field.getName().length();

            if(fieldLen>bean.getFieldMaxLen())
            {
                bean.setFieldMaxLen(fieldLen);
            }
            bean.getFields().add(field);
        }
    }

    private static boolean baseField(String type) {
        for (String str : baseFields) {
            if (str.equals(type)) {
                return true;
            }
        }
        return false;
    }
}
