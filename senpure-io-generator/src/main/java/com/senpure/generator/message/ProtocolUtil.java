package com.senpure.generator.message;

import java.util.HashMap;
import java.util.Map;



/**
 * Created by 罗中正 on 2018/4/14.
 */
public class ProtocolUtil {
    public static final int WIRETYPE_VARINT = 0;
    public static final int WIRETYPE_FIXED32 = 1;
    public static final int WIRETYPE_FIXED64 = 2;
    public static final int WIRETYPE_LENGTH_DELIMITED = 3;
    public static String[] baseFields ={"int", "long", "sint", "slong", "sfixed32", "sfixed64", "float", "double","boolean","String","byte", "short"};


    public static Map<String, Integer> writeType = new HashMap<>();
    public static Map<String, String> javaType = new HashMap<>();

    static {
        writeType.put("int",WIRETYPE_VARINT);
        writeType.put("long",WIRETYPE_VARINT);
        writeType.put("byte",WIRETYPE_VARINT);
        writeType.put("short",WIRETYPE_VARINT);
        writeType.put("boolean",WIRETYPE_VARINT);
        writeType.put("sint",WIRETYPE_VARINT);
        writeType.put("slong",WIRETYPE_VARINT);

        writeType.put("float",WIRETYPE_FIXED32);
        writeType.put("sfixed32",WIRETYPE_FIXED32);


        writeType.put("double",WIRETYPE_FIXED64);
        writeType.put("sfixed64",WIRETYPE_FIXED64);

        writeType.put("String",WIRETYPE_LENGTH_DELIMITED);


        javaType.put("int", "int");
        javaType.put("long", "long");
        javaType.put("sint", "int");
        javaType.put("slong", "long");
        javaType.put("byte", "int");
        javaType.put("sort", "int");

        javaType.put("sfixed32", "int");
        javaType.put("sfixed64", "long");


        javaType.put("boolean", "boolean");
        javaType.put("String", "String");

        javaType.put("float", "float");
        javaType.put("double", "double");

    }


    public static  int getWriteType(String type)
    {

        return writeType.get(type);
    }
    public static  String getJavaType(String type)
    {

        return javaType.get(type);
    }
    public static boolean isBaseField(String type) {
        for (String str : baseFields) {
            if (str.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("".getBytes().length);
    }
}
