package com.senpure.generator.message.demo.bean;

import com.senpure.io.protocol.Bean;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
* 学生
* 
* @author senpure-generator
* @version 2018-4-15 10:31:50
*/
public class Student extends  Bean {
    //学生名
    private String name;
    //学生年龄
    private int age;
    //学号
    private int num;
    //幸运数字
    private List<Integer> luckNums = new ArrayList(16);
    //格言
    private List<String> provide = new ArrayList(16);
    //正在读的书
    private Book readBook;
    //喜欢的书
    private List<Book> likeBooks = new ArrayList(16);
    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        getSerializedSize();
        //学生名
        if (name != null){
            writeString(buf,11,name);
        }
        //学生年龄
        writeVar32(buf,19,age);
        //学号
        writeVar32(buf,27,num);
        //幸运数字
        if (luckNums.size() > 0){
            writeVar32(buf,35);
            writeVar32(buf,luckNumsSerializedSize);
            for (int i= 0;i< luckNums.size();i++){
                writeVar32(buf,luckNums.get(i));
            }
        }
        //格言
        for (int i= 0;i< provide.size();i++){
            writeString(buf,43,provide.get(i));
        }
        //正在读的书
        if (readBook!= null){
            writeBean(buf,51,readBook);
        }
        //喜欢的书
        for (int i= 0;i< likeBooks.size();i++){
            writeBean(buf,59,likeBooks.get(i));
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
                //学生名
                case 11:// 1 << 3 | 3
                        name = readString(buf);
                    break;
                //学生年龄
                case 19:// 2 << 3 | 3
                        age = readVar32(buf);
                    break;
                //学号
                case 27:// 3 << 3 | 3
                        num = readVar32(buf);
                    break;
                //幸运数字
                case 35:// 4 << 3 | 3
                    int luckNumsDataSize = readVar32(buf);
                    int receiveLuckNumsDataSize = 0;
                    do {
                        int tempLuckNums = readVar32(buf);
                        receiveLuckNumsDataSize += computeVar32SizeNoTag(tempLuckNums);
                        luckNums.add(tempLuckNums);
                    }
                    while(receiveLuckNumsDataSize < luckNumsDataSize );
                    break;
                //格言
                case 43:// 5 << 3 | 3
                        provide.add(readString(buf));
                    break;
                //正在读的书
                case 51:// 6 << 3 | 3
                readBook = new Book();
                        readBean(buf,readBook);
                    break;
                //喜欢的书
                case 59:// 7 << 3 | 3
                        Book tempLikeBooksBean = new Book();
                        readBean(buf,tempLikeBooksBean);
                        likeBooks.add(tempLikeBooksBean);
                    break;
                default://skip
                    skip(buf, tag);
                    break;
            }
        }
    }

    private int serializedSize = -1;
    private int luckNumsSerializedSize = 0;

    @Override
    public int getSerializedSize(){
        int size = serializedSize ;
        if (size != -1 ){
            return size;
        }
        size = 0 ;
        //学生名
        if (name != null){
            size += computeStringSize(11,name);
        }
        //学生年龄
        size += computeVar32Size(19,age);
        //学号
        size += computeVar32Size(27,num);
        //幸运数字
            int luckNumsDataSize = 0;
        for(int i=0;i< luckNums.size();i++){
            luckNumsDataSize += computeVar32SizeNoTag(luckNums.get(i));
        }
        luckNumsSerializedSize = luckNumsDataSize;
        if(luckNumsDataSize > 0 ){
            //tag size 35
            size += 1;
            size += luckNumsSerializedSize;
            size += computeVar32SizeNoTag(luckNumsSerializedSize);
        }
        //格言
        for(int i=0;i< provide.size();i++){
            size += computeStringSize(43,provide.get(i));
        }
        //正在读的书
        if (readBook != null){
            size += computeBeanSize(51,readBook);
        }
        //喜欢的书
        for(int i=0;i< likeBooks.size();i++){
            size += computeBeanSize(59,likeBooks.get(i));
        }
        serializedSize = size ;
        return size ;
    }

    /**
     * get 学生名
     * @return
     */
    public  String getName() {
        return name;
    }

    /**
     * set 学生名
     */
    public Student setName(String name) {
        this.name=name;
        return this;
    }
    /**
     * get 学生年龄
     * @return
     */
    public  int getAge() {
        return age;
    }

    /**
     * set 学生年龄
     */
    public Student setAge(int age) {
        this.age=age;
        return this;
    }
    public  int getNum() {
        return num;
    }

    public Student setNum(int num) {
        this.num=num;
        return this;
    }
     /**
      * get 幸运数字
      * @return
      */
    public List<Integer> getLuckNums(){
        return luckNums;
    }
     /**
      * set 幸运数字
      */
    public Student setLuckNums (List<Integer> luckNums){
        this.luckNums=luckNums;
        return this;
    }

    public List<String> getProvide(){
        return provide;
    }
    public Student setProvide (List<String> provide){
        this.provide=provide;
        return this;
    }

    /**
     * get 正在读的书
     * @return
     */
    public  Book getReadBook() {
        return readBook;
    }

    /**
     * set 正在读的书
     */
    public Student setReadBook(Book readBook) {
        this.readBook=readBook;
        return this;
    }
     /**
      * get 喜欢的书
      * @return
      */
    public List<Book> getLikeBooks(){
        return likeBooks;
    }
     /**
      * set 喜欢的书
      */
    public Student setLikeBooks (List<Book> likeBooks){
        this.likeBooks=likeBooks;
        return this;
    }


    @Override
    public String toString() {
        return "Student{"
                +"name=" + name
                +",age=" + age
                +",num=" + num
                +",luckNums=" + luckNums
                +",provide=" + provide
                +",readBook=" + readBook
                +",likeBooks=" + likeBooks
                + "}";
   }

    //9 + 3 = 12 个空格
    private String nextIndent ="            ";
    //最长字段长度 9
    private int filedPad = 9;

    @Override
    public String toString(String indent) {
        indent = indent == null ? "" : indent;
        StringBuilder sb = new StringBuilder();
        sb.append("Student").append("{");
        //学生名
        sb.append("\n");
        sb.append(indent).append(rightPad("name", filedPad)).append(" = ").append(name);
        //学生年龄
        sb.append("\n");
        sb.append(indent).append(rightPad("age", filedPad)).append(" = ").append(age);
        //学号
        sb.append("\n");
        sb.append(indent).append(rightPad("num", filedPad)).append(" = ").append(num);
        //幸运数字
        sb.append("\n");
        sb.append(indent).append(rightPad("luckNums", filedPad)).append(" = ");
        int luckNumsSize = luckNums.size();
        if (luckNumsSize > 0) {
            sb.append("[");
            for (int i = 0; i<luckNumsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(luckNums.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //格言
        sb.append("\n");
        sb.append(indent).append(rightPad("provide", filedPad)).append(" = ");
        int provideSize = provide.size();
        if (provideSize > 0) {
            sb.append("[");
            for (int i = 0; i<provideSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(provide.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //正在读的书
        sb.append("\n");
        sb.append(indent).append(rightPad("readBook", filedPad)).append(" = ");
        if(readBook!=null){
            sb.append(readBook.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        //喜欢的书
        sb.append("\n");
        sb.append(indent).append(rightPad("likeBooks", filedPad)).append(" = ");
        int likeBooksSize = likeBooks.size();
        if (likeBooksSize > 0) {
            sb.append("[");
            for (int i = 0; i<likeBooksSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(likeBooks.get(i).toString(indent + nextIndent));
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