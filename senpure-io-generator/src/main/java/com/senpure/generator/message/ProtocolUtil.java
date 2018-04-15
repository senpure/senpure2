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
    public static Map<String, String> languageType = new HashMap<>();

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


        languageType.put("int", "int");
        languageType.put("long", "long");
        languageType.put("sint", "int");
        languageType.put("slong", "long");
        languageType.put("byte", "int");
        languageType.put("sort", "int");

        languageType.put("sfixed32", "int");
        languageType.put("sfixed64", "long");


        languageType.put("boolean", "boolean");
        languageType.put("String", "String");

        languageType.put("float", "float");
        languageType.put("double", "double");

    }


    public static  int getWriteType(String type)
    {

        return writeType.get(type);
    }
    public static  String getLanguageType(String type)
    {

        return languageType.get(type);
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
