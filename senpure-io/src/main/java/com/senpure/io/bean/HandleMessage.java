package com.senpure.io.bean;

import com.senpure.io.bean.Bean;
import io.netty.buffer.ByteBuf;

/**
* @author senpure-generator
* @version 2018-3-16 17:14:31
*/
public class HandleMessage extends  Bean {
    //可以处理的消息ID
    private int handleMessageId;
    //消息类名
    private String messageClasses;
    //消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
    private int type;
    //数字类型 0int 1 long
    private int numType;
    //范围开始
    private int start;
    //范围结束
    private int end;

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        //可以处理的消息ID
        writeInt(buf,handleMessageId);
        //消息类名
        writeStr(buf,messageClasses);
        //消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
        writeInt(buf,type);
        //数字类型 0int 1 long
        writeInt(buf,numType);
        //范围开始
        writeInt(buf,start);
        //范围结束
        writeInt(buf,end);
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
        //可以处理的消息ID
        this.handleMessageId = readInt(buf);
        //消息类名
        this.messageClasses= readStr(buf);
        //消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
        this.type = readInt(buf);
        //数字类型 0int 1 long
        this.numType = readInt(buf);
        //范围开始
        this.start = readInt(buf);
        //范围结束
        this.end = readInt(buf);
    }

    /**
     * get 可以处理的消息ID
     * @return
     */
    public  int getHandleMessageId() {
        return handleMessageId;
    }

    /**
     * set 可以处理的消息ID
     */
    public HandleMessage setHandleMessageId(int handleMessageId) {
        this.handleMessageId=handleMessageId;
        return this;
    }
    /**
     * get 消息类名
     * @return
     */
    public  String getMessageClasses() {
        return messageClasses;
    }

    /**
     * set 消息类名
     */
    public HandleMessage setMessageClasses(String messageClasses) {
        this.messageClasses=messageClasses;
        return this;
    }
    /**
     * get 消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
     * @return
     */
    public  int getType() {
        return type;
    }

    /**
     * set 消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
     */
    public HandleMessage setType(int type) {
        this.type=type;
        return this;
    }
    /**
     * get 数字类型 0int 1 long
     * @return
     */
    public  int getNumType() {
        return numType;
    }

    /**
     * set 数字类型 0int 1 long
     */
    public HandleMessage setNumType(int numType) {
        this.numType=numType;
        return this;
    }
    /**
     * get 范围开始
     * @return
     */
    public  int getStart() {
        return start;
    }

    /**
     * set 范围开始
     */
    public HandleMessage setStart(int start) {
        this.start=start;
        return this;
    }
    /**
     * get 范围结束
     * @return
     */
    public  int getEnd() {
        return end;
    }

    /**
     * set 范围结束
     */
    public HandleMessage setEnd(int end) {
        this.end=end;
        return this;
    }

    @Override
    public String toString() {
        return "HandleMessage{"
                +"handleMessageId=" + handleMessageId
                +",messageClasses=" + messageClasses
                +",type=" + type
                +",numType=" + numType
                +",start=" + start
                +",end=" + end
                + "}";
   }

    //最长字段长度 15
    private int filedPad = 15;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("HandleMessage").append("{");
        //可以处理的消息ID
        sb.append("\n");
        sb.append(indent).append(rightPad("handleMessageId", filedPad)).append(" = ").append(handleMessageId);
        //消息类名
        sb.append("\n");
        sb.append(indent).append(rightPad("messageClasses", filedPad)).append(" = ").append(messageClasses);
        //消息类型 0 可以直接转发过来 1 网关向服务器请求是否可以处理 2 网关读取数字范围
        sb.append("\n");
        sb.append(indent).append(rightPad("type", filedPad)).append(" = ").append(type);
        //数字类型 0int 1 long
        sb.append("\n");
        sb.append(indent).append(rightPad("numType", filedPad)).append(" = ").append(numType);
        //范围开始
        sb.append("\n");
        sb.append(indent).append(rightPad("start", filedPad)).append(" = ").append(start);
        //范围结束
        sb.append("\n");
        sb.append(indent).append(rightPad("end", filedPad)).append(" = ").append(end);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}