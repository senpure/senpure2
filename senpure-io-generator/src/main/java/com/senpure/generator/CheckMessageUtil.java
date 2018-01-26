package com.senpure.generator;


import com.senpure.base.AppEvn;
import com.senpure.generator.message.Message;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 防止失误消息id重复
 * Created by 罗中正 on 2017/8/8.
 */
public class CheckMessageUtil {

    private static Logger logger = LoggerFactory.getLogger(CheckMessageUtil.class);

    private static Map<Integer, String> map = new HashMap<>();
    private static File record;


    private static String lastCodepath = "";
    private static RandomAccessFile randomFile;

    public static  String recordPath()
    {
        return  record.getAbsolutePath();
    }
    public static void loadMessage(String codePath) {
        if (lastCodepath.equalsIgnoreCase(codePath)) {
            return;
        }
        if (randomFile != null) {
            try {
                randomFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        map.clear();
        logger.debug("codepath {}  fileSeparator {}", codePath, File.separator);
        record = new File(AppEvn.getCallerRootPath(), "record/" + codePath.replace(File.separator, "").replace(":", "") + ".txt");

        if (!record.getParentFile().exists()) {
            record.getParentFile().mkdirs();
        }
        if (record.exists()) {
            try {

                List<String> lines = FileUtils.readLines(record);
                for (String str : lines) {
                    int id = readMessageId(str);
                    if (id > 0) {
                        map.put(id, str);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            randomFile = new RandomAccessFile(record, "rw");
            long length = randomFile.length();
            randomFile.seek(length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int readMessageId(String message) {
        int start = message.indexOf(":");

        int end = message.indexOf("->");
        if (start > 0 && end > 0) {
            String temp = message.substring(start + 1, end);
            try {
                return Integer.parseInt(temp.trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }


    public static boolean checkMessage(Message message,String type) {
        String str = map.get(message.getId());
        if (str != null) {
            String nowMessage = getMessage(message);
            if (!str.equals(nowMessage)) {

                String name="消息ID重复  {} 不会生成 \n";

                logger.error(name +
                        "                                                  {} \n" +
                        "                                                  {}",nowMessage+" ["+type+"] ", str, nowMessage);
                logger.info("如果确认以前的消息不需要，请删除 {} 里的记录 ",CheckMessageUtil.recordPath());
                return false;
            }

        }
        return true;
    }

    public static String getMessage(Message message) {
        String messageName=message.getName();
        if(!messageName.endsWith("Message"))
        {
            messageName=message.getType()+messageName+"Message";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("MessageId:").append(message.getId())
                .append(" -> ").append(message.getPack()).append(".").append(messageName);
        return sb.toString();
    }

    public static void messsageSuccess(Message message) {


        String line = getMessage(message);
        if (map.get(message.getId()) == null) {
            map.put(message.getId(), line);
            try {
                randomFile.writeBytes(line+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
