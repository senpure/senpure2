package com.senpure.io.message;

import com.senpure.io.message.Message;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
 * 服务器注册消息处理器到网关
 * 
 * @author senpure-generator
 * @version 2018-3-2 16:54:07
 */
public class SCRegistrationMessage extends  Message {
    //服务器名
    private String serverName;
    //可以处理的消息ID
    private List<Integer> messageIds=new ArrayList();
    //消息类名
    private List<String> messageClasses=new ArrayList();

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //服务器名
        writeStr(buf,serverName);
        //可以处理的消息ID
        int messageIdsSize=messageIds.size();
        writeShort(buf,messageIdsSize);
        for(int i=0;i< messageIdsSize;i++){
            writeInt(buf,messageIds.get(i));
           }
        //消息类名
        int messageClassesSize=messageClasses.size();
        writeShort(buf,messageClassesSize);
        for(int i=0;i< messageClassesSize;i++){
            writeStr(buf,messageClasses.get(i));
           }
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //服务器名
        this.serverName= readStr(buf);
        //可以处理的消息ID
        int messageIdsSize=readShort(buf);
        for(int i=0;i<messageIdsSize;i++){
            this.messageIds.add(readInt(buf));
         }
        //消息类名
        int messageClassesSize=readShort(buf);
        for(int i=0;i<messageClassesSize;i++){
            this.messageClasses.add(readStr(buf));
         }
    }

    /**
     * get 服务器名
     * @return
     */
    public  String getServerName() {
        return serverName;
    }

    /**
     * set 服务器名
     */
    public SCRegistrationMessage setServerName(String serverName) {
        this.serverName=serverName;
        return this;
    }
     /**
      * get 可以处理的消息ID
      * @return
      */
    public List<Integer> getMessageIds(){
        return messageIds;
    }
     /**
      * set 可以处理的消息ID
      */
    public SCRegistrationMessage setMessageIds (List<Integer> messageIds){
        this.messageIds=messageIds;
        return this;
    }

     /**
      * get 消息类名
      * @return
      */
    public List<String> getMessageClasses(){
        return messageClasses;
    }
     /**
      * set 消息类名
      */
    public SCRegistrationMessage setMessageClasses (List<String> messageClasses){
        this.messageClasses=messageClasses;
        return this;
    }


    @Override
    public int getMessageId() {
    return 100102;
    }

    @Override
    public String toString() {
        return "SCRegistrationMessage{"
                +"serverName=" + serverName
                +",messageIds=" + messageIds
                +",messageClasses=" + messageClasses
                + "}";
   }

    //14 + 3 = 17 个空格
    private String nextIndent ="                 ";
    //最长字段长度 14
    private int filedPad = 14;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("SCRegistrationMessage").append("{");
        //服务器名
        sb.append("\n");
        sb.append(indent).append(rightPad("serverName", filedPad)).append(" = ").append(serverName);
        //可以处理的消息ID
        sb.append("\n");
        sb.append(indent).append(rightPad("messageIds", filedPad)).append(" = ");
        int messageIdsSize = messageIds.size();
        if (messageIdsSize > 0) {
            sb.append("[");
            for (int i = 0; i<messageIdsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(messageIds.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //消息类名
        sb.append("\n");
        sb.append(indent).append(rightPad("messageClasses", filedPad)).append(" = ");
        int messageClassesSize = messageClasses.size();
        if (messageClassesSize > 0) {
            sb.append("[");
            for (int i = 0; i<messageClassesSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(messageClasses.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}