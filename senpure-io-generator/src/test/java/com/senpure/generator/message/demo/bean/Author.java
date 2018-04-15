package com.senpure.generator.message.demo.bean;

import com.senpure.io.protocol.Bean;
import io.netty.buffer.ByteBuf;

/**
* 作者
* 
* @author senpure-generator
* @version 2018-4-15 10:31:50
*/
public class Author extends  Bean {
    //书本名
    private String name;
    //电话
    private int phone;
    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        getSerializedSize();
        //书本名
        if (name != null){
            writeString(buf,11,name);
        }
        //电话
        writeVar32(buf,19,phone);
    }

    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf,int endIndex){
        while(true){
            int tag = readTag(buf, endIndex);
            switch (tag) {
                case 0://end
                return;
                //书本名
                case 11:// 1 << 3 | 3
                        name = readString(buf);
                    break;
                //电话
                case 19:// 2 << 3 | 3
                        phone = readVar32(buf);
                    break;
                default://skip
                    skip(buf, tag);
                    break;
            }
        }
    }

    private int serializedSize = -1;

    @Override
    public int getSerializedSize(){
        int size = serializedSize ;
        if (size != -1 ){
            return size;
        }
        size = 0 ;
        //书本名
        if (name != null){
            size += computeStringSize(11,name);
        }
        //电话
        size += computeVar32Size(19,phone);
        serializedSize = size ;
        return size ;
    }

    /**
     * get 书本名
     * @return
     */
    public  String getName() {
        return name;
    }

    /**
     * set 书本名
     */
    public Author setName(String name) {
        this.name=name;
        return this;
    }
    public  int getPhone() {
        return phone;
    }

    public Author setPhone(int phone) {
        this.phone=phone;
        return this;
    }

    @Override
    public String toString() {
        return "Author{"
                +"name=" + name
                +",phone=" + phone
                + "}";
   }

    //最长字段长度 5
    private int filedPad = 5;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Author").append("{");
        //书本名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //电话
        sb.append("\n");
        sb.append(indent).append(rightPad("phone", filedPad)).append(" = ").append(phone);
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}