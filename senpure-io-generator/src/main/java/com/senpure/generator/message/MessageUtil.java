package com.senpure.generator.message;


import com.senpure.base.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2017/8/17.
 */
public class MessageUtil {
    public static void copyBean(Bean source, Bean target) {
        target.setName(source.getName());
        target.setHasBean(source.isHasBean());
        target.setExplain(source.getExplain());
        target.setPack(source.getPack());
        target.setFieldMaxLen(source.getFieldMaxLen());
        for (Field field : source.getFields()) {
            Field f = new Field();
            f.setOtherPart(field.isOtherPart());
            f.setOriginalClassType(field.getOriginalClassType());
            f.setBaseField(field.isBaseField());
            f.setExplain(field.getExplain());
            f.setList(field.isList());
            f.setClassType(field.getClassType());
            f.setCapacity(field.getCapacity());
            f.setName(field.getName());
            target.getFields().add(f);
        }
    }

    public static void copyMessage(Message source, Message target) {
        copyBean(source, target);
        target.setType(source.getType());
        target.setId(source.getId());

    }


    public static void convertName2Lua(Bean bean) {
        for (Field field : bean.getFields()) {
            field.setName(luaNameStyle(field.getName()));
            int len = field.getName().length();
            if (len > bean.getFieldMaxLen()) {
                bean.setFieldMaxLen(len);
            }
        }

    }


    public static XmlMessage convert2Lua(XmlMessage xmlMessage) {
        XmlMessage m = new XmlMessage();
        m.setId(xmlMessage.getId());
        m.setPack(xmlMessage.getPack());
        for (Bean bean : xmlMessage.getBeans()) {
            Bean b = new Bean();
            copyBean(bean, b);
            convertName2Lua(b);
            int len = b.getName().length();
            if (len > m.getBeanNameMaxLen()) {
                m.setBeanNameMaxLen(len);
            }
            if (len > m.getNameMaxLen()) {
                m.setNameMaxLen(len);
            }
            m.getBeans().add(b);
        }
        for (Message message : xmlMessage.getMessages()) {
            Message msg = new Message();
            copyMessage(message, msg);
            convertName2Lua(msg);
            msg.setName(msg.getType() + msg.getName() + "Message");

            int len = msg.getName().length();
            if (len > m.getMessageNameMaxLen()) {
                m.setMessageNameMaxLen(len);
            }
            if (len > m.getNameMaxLen()) {
                m.setNameMaxLen(len);
            }
            m.getMessages().add(msg);
        }
        return m;
    }

    private static class Mark {

        int index;
        boolean lower;

        @Override
        public String toString() {
            return "Mark{" +
                    "index=" + index +
                    ", lower=" + lower +
                    '}';
        }
    }

    public static String luaNameStyle(String name) {


        name = name.replaceAll("_", "");
        int len = name.length();
        List<Mark> marks = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (StringUtil.isUpperLetter(c)) {
                int cons = cons(name, i, len);
                //大写结尾
                if (i + cons == len) {
                    Mark mark = new Mark();
                    mark.index = i;
                    mark.lower = false;
                    marks.add(mark);
                    break;
                } else if (cons == 1) {
                    Mark mark = new Mark();
                    mark.index = i;
                    mark.lower = true;
                    if (i > 0) {
                        marks.add(mark);
                    }

                } else {
                    Mark mark = new Mark();
                    mark.index = i;
                    mark.lower = false;
                    if (i > 0) {
                        marks.add(mark);
                    }
                    i += cons - 2;
                }
            }
        }
        int ml = marks.size();
        for (int i = 0; i < ml; i++) {
            Mark mark = marks.get(i);
            if (mark.lower) {
                name = StringUtil.toLowerLetter(name, mark.index);
            }

        }
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        for (int i = 0; i < ml; i++) {
            Mark mark = marks.get(i);
            sb.insert(mark.index + i, "_");
        }
        if (!name.startsWith("_")) {
            sb.insert(0, "_");
        }

        return sb.toString();
    }

    private static int cons(String name, int start, int len) {
        int count = 0;
        for (int i = start; i < len; i++) {
            char c = name.charAt(i);
            if (!StringUtil.isUpperLetter(c)) {
                break;
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        names.add("readBook");
        names.add("computerIP");
        names.add("_book");
        names.add("groupIPPConfig");
        names.add("CSStudentMessage");
        for (String name : names) {

            System.out.println(name + " >> " + luaNameStyle(name));
        }

    }
}
