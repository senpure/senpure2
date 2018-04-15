package com.senpure.generator.message.demo.bean;

import com.senpure.io.protocol.Bean;
import io.netty.buffer.ByteBuf;

/**
* 书本
* 
* @author senpure-generator
* @version 2018-4-15 10:31:50
*/
public class Book extends  Bean {
    //书本名
    private String name;
    //书本单价
    private int price;
    //作者
    private Author author;
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
        //书本单价
        writeVar32(buf,19,price);
        //作者
        if (author!= null){
            writeBean(buf,27,author);
        }
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
                //书本单价
                case 19:// 2 << 3 | 3
                        price = readVar32(buf);
                    break;
                //作者
                case 27:// 3 << 3 | 3
                author = new Author();
                        readBean(buf,author);
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
        //书本单价
        size += computeVar32Size(19,price);
        //作者
        if (author != null){
            size += computeBeanSize(27,author);
        }
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
    public Book setName(String name) {
        this.name=name;
        return this;
    }
    /**
     * get 书本单价
     * @return
     */
    public  int getPrice() {
        return price;
    }

    /**
     * set 书本单价
     */
    public Book setPrice(int price) {
        this.price=price;
        return this;
    }
    public  Author getAuthor() {
        return author;
    }

    public Book setAuthor(Author author) {
        this.author=author;
        return this;
    }

    @Override
    public String toString() {
        return "Book{"
                +"name=" + name
                +",price=" + price
                +",author=" + author
                + "}";
   }

    //6 + 3 = 9 个空格
    private String nextIndent ="         ";
    //最长字段长度 6
    private int filedPad = 6;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Book").append("{");
        //书本名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //书本单价
        sb.append("\n");
        sb.append(indent).append(rightPad("price", filedPad)).append(" = ").append(price);
        //作者
        sb.append("\n");
        sb.append(indent).append(rightPad("author", filedPad)).append(" = ");
        if(author!=null){
            sb.append(author.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        sb.append("\n");
        sb.append(indent).append("}");
        return sb.toString();
    }

}