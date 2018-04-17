package com.senpure.generator.message;

import com.senpure.base.util.Assert;
import com.senpure.base.util.StringUtil;
import com.senpure.generator.MessageConfig;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by 罗中正 on 2017/6/2.
 */
public class XmlReader {

    // private static String[] baseFields = {"int", "boolean", "byte", "short", "String", "long", "float", "double"};
    private static Logger logger = LoggerFactory.getLogger(XmlReader.class);

    public static XmlMessage innerReadXml(File file, Map<String, XmlMessage> xmlMessageMap) {
        XmlMessage xmlMessage = xmlMessageMap.get(file.getAbsolutePath());
        if (xmlMessage != null) {
            return xmlMessage;
        }
        xmlMessage = new XmlMessage();
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(new FileInputStream(file));
        } catch (DocumentException e) {
            logger.error("DocumentException", e);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException", e);
        }
        xmlMessage.setFile(file);
        Element root = document.getRootElement();
        String pack = root.attributeValue("package");
        String model = root.attributeValue("model");
        if (model == null) {
            model = root.attributeValue("namespace");
            model = StringUtil.toLowerFirstLetter(model);
        }
        model = model == null ? "MSG" : model;
        xmlMessage.setModel(model);
        xmlMessage.setPack(pack);
        List<Element> imports = root.elements("import");
        int parent = 1;
        for (Element element : imports) {
            File readFile = null;
            String path = element.attributeValue("path");
            if (path == null) {
                Assert.error("path is null ");
            }
            if (path.startsWith("/")) {
                readFile = new File(path);
            } else {
                parent += StringUtil.count(path, "../");
                File parentFile = file;
                for (int i = 0; i < parent; i++) {
                    parentFile = parentFile.getParentFile();
                }
                path = path.replace("../", "");
                readFile = new File(parentFile, path);
            }
            XmlMessage importXml = innerReadXml(readFile, xmlMessageMap);
            if (importXml.getPack().equals(xmlMessage.getPack()) && importXml.getModel().equals(xmlMessage.getModel())) {
                //  MessageUtil.mergeXmlMessage(xmlMessage, importXml,false);
            }
        }

        String id = root.attributeValue("id");
        xmlMessage.setId(id);
        List<Element> beanElemets = root.elements("bean");
        for (Element element : beanElemets) {
            Bean bean = new Bean();
            bean.setPack(pack);
            if (MessageConfig.JAVA_PACK_BEAN.trim().length() > 0) {
                bean.setJavaPack(pack + "." + MessageConfig.JAVA_PACK_BEAN);
            }
            bean.setModel(model);
            bean.setLuaNamespace(MessageConfig.LUA_NAMESPACE_PREFIX + StringUtil.toUpperFirstLetter(model) +
                    MessageConfig.LUA_NAMESPACE_BEAN_SUFFIX);
            String name = element.attributeValue("name");
            name = StringUtil.toUpperFirstLetter(name);
            bean.setName(name);
            bean.setExplain(element.attributeValue("explain"));
            attr(element, bean);
            xmlMessage.getBeans().add(bean);
        }
        List<Element> messageElemets = root.elements("message");

        for (Element element : messageElemets) {
            Message message = new Message();
            message.setPack(pack);
            if (MessageConfig.JAVA_PACK_MESSAGE.trim().length() > 0) {
                message.setJavaPack(pack + "." + MessageConfig.JAVA_PACK_MESSAGE);
            }
            message.setModel(model);

            String name = element.attributeValue("name");
            if (name.startsWith("Req") || name.startsWith("Res")) {
                name = name.substring(3);
            }
            name = StringUtil.toUpperFirstLetter(name);
            message.setName(name);
            message.setExplain(element.attributeValue("explain"));
            String type = element.attributeValue("type");

            type = type.toUpperCase();
            message.setType(type);

            message.setLuaNamespace(MessageConfig.LUA_NAMESPACE_PREFIX +
                    StringUtil.toUpperFirstLetter(model) +
                    (type.equals("CS") ? MessageConfig.LUA_NAMESPACE_CS_SUFFIX : MessageConfig.LUA_NAMESPACE_SC_SUFFIX));
            attr(element, message);
            String mid = element.attributeValue("id");
            message.setId(Integer.parseInt(id + mid));
            xmlMessage.getMessages().add(message);
        }

        List<Bean> modelBeans = new ArrayList<>();
        modelBeans.addAll(xmlMessage.getBeans());
        // modelBeans.addAll(xmlMessage.getMessages());
        List<Bean> allBeans = new ArrayList<>();
        for (XmlMessage x : xmlMessageMap.values()) {
            allBeans.addAll(x.getBeans());
            //  allBeans.addAll(x.getMessages());
        }
        List<Bean> finds = new ArrayList<>();
        finds.addAll(xmlMessage.getBeans());
        finds.addAll(xmlMessage.getMessages());
        findBenAndAssignment(xmlMessage.getBeans(), modelBeans, allBeans);
        findBenAndAssignment(finds, modelBeans, allBeans);
        xmlMessageMap.put(file.getAbsolutePath(), xmlMessage);

        return xmlMessage;
    }

    public static XmlMessage readXml(File file) {
        Map<String, XmlMessage> xmlMessageMap = new HashMap(16);
        return innerReadXml(file, xmlMessageMap);
    }

    private static void attr(Element element, Bean bean) {
        int fieldIndex = 0;
        Set<Integer> indexs = new HashSet<>();
        List<Element> elements = element.elements();
        for (Element e : elements) {
            fieldIndex++;
            Field field = new Field();
            field.setName(e.attributeValue("name"));
            String tempIndex = e.attributeValue("index");
            Integer index = 0;
            if (tempIndex == null) {
                index = fieldIndex;
            } else {
                index = Integer.valueOf(tempIndex);
            }
            indexs.add(index);
            if (indexs.size() != fieldIndex) {
                Assert.error("field index 重复 " + index);
            }
            field.setIndex(index);
            String type = e.attributeValue("type");
            if (type == null) {
                type = e.attributeValue("class");
            }
            // field.setOriginalClassType(type);
            bean.getSingleField().put(type, field);
            field.setBaseField(ProtocolUtil.isBaseField(type));
            field.setClassType(type);
            field.setList("list".equals(e.getName()));
            field.setExplain(e.attributeValue("explain"));
            if (!field.isBaseField()) {
                bean.setHasBean(true);
            }
            if (field.isBaseField()) {
                field.setLanguageType(ProtocolUtil.getLanguageType(type));
                if (field.isList() && !field.getClassType().equals("String")) {
                    field.setWriteType(ProtocolUtil.WIRETYPE_LENGTH_DELIMITED);
                }
                field.setTag(index << 3 | field.getWriteType());
            } else {
                field.setLanguageType(type);
                field.setWriteType(ProtocolUtil.WIRETYPE_LENGTH_DELIMITED);
                field.setTag(index << 3 | ProtocolUtil.WIRETYPE_LENGTH_DELIMITED);
            }

            int fieldLen = field.getName().length();
            if (fieldLen > bean.getFieldMaxLen()) {
                bean.setFieldMaxLen(fieldLen);
            }
          //  String str = field.isList() ? "repeated " : "";
           // str += field.getClassType() + " " + field.getName() + " = " + field.getIndex() + " ;";
           // System.out.println(str);
            bean.getFields().add(field);
        }
    }

    //给bean 下的 bean赋值
    private static void findBenAndAssignment(List<Bean> beans, List<Bean> modelBeans, List<Bean> allBeans) {
        for (Bean bean : beans) {
            for (Field field : bean.getFields()) {
                if (!field.isBaseField()) {
                    Bean b = findBean(field.getClassType(), modelBeans);
                    if (b == null) {
                        b = findBean(field.getClassType(), allBeans);
                    } else {
                        field.setOtherPart(false);
                    }
                    if (b != null) {
                        field.setBean(b);
                    } else {
                        Assert.error(
                                (bean.getType().equalsIgnoreCase("NA") ? "" : bean.getType()) + bean.getName() + "." + field.getName()
                                        + "[" + field.getClassType() + "] Type,未定义，或未引用");
                    }

                }
            }
        }
    }

    private static Bean findBean(String type, List<Bean> beans) {
        for (Bean bean : beans) {
            if (bean.getName().equals(type)) {
                return bean;
            }
        }
        return null;
    }

    private static class ReadResult {

        XmlMessage xmlMessage;

        List<XmlMessage> xmlMessages;
    }
}
