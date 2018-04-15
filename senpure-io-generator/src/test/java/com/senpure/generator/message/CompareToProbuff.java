package com.senpure.generator.message;

import com.google.protobuf.InvalidProtocolBufferException;
import com.senpure.generator.message.demo.DemoMessage;
import com.senpure.generator.message.demo.bean.AllFieldBean;
import com.senpure.generator.message.demo.bean.Author;
import com.senpure.generator.message.demo.bean.Book;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 罗中正 on 2018/4/15.
 */
public class CompareToProbuff {


    public AllFieldBean getBean() {

        AllFieldBean allFieldBean = new AllFieldBean();
        allFieldBean.setIntField(12);

        allFieldBean.getIntFields().add(0);
        allFieldBean.getIntFields().add(2);
        allFieldBean.getIntFields().add(128);
        allFieldBean.getIntFields().add(16384);
        allFieldBean.getIntFields().add(2097152);
        allFieldBean.getIntFields().add(268435456);
        allFieldBean.getIntFields().add(2147483647);
        allFieldBean.getIntFields().add(-2);
        allFieldBean.getIntFields().add(-128);
        allFieldBean.getIntFields().add(-16384);
        allFieldBean.getIntFields().add(-2097152);
        allFieldBean.getIntFields().add(-268435456);
        allFieldBean.getIntFields().add(-2147483648);


        allFieldBean.setLongField(-666666666);

        allFieldBean.getLongFields().add(0L);
        allFieldBean.getLongFields().add(2L);
        allFieldBean.getLongFields().add(128L);
        allFieldBean.getLongFields().add(16384L);
        allFieldBean.getLongFields().add(2097152L);
        allFieldBean.getLongFields().add(268435456L);
        allFieldBean.getLongFields().add(34359738368L);
        allFieldBean.getLongFields().add(4398046511104L);
        allFieldBean.getLongFields().add(562949953421312L);
        allFieldBean.getLongFields().add(72057594037927936L);
        allFieldBean.getLongFields().add(9223372036854775807L);
        allFieldBean.getLongFields().add(-2L);
        allFieldBean.getLongFields().add(-128L);
        allFieldBean.getLongFields().add(-16384L);
        allFieldBean.getLongFields().add(-2097152L);
        allFieldBean.getLongFields().add(-268435456L);
        allFieldBean.getLongFields().add(-34359738368L);
        allFieldBean.getLongFields().add(-4398046511104L);
        allFieldBean.getLongFields().add(-562949953421312L);
        allFieldBean.getLongFields().add(-72057594037927936L);
        allFieldBean.getLongFields().add(-9223372036854775808L);


        allFieldBean.setSintField(-19888888);

        allFieldBean.getSintFields().add(0);
        allFieldBean.getSintFields().add(2);
        allFieldBean.getSintFields().add(128);
        allFieldBean.getSintFields().add(16384);
        allFieldBean.getSintFields().add(2097152);
        allFieldBean.getSintFields().add(268435456);
        allFieldBean.getSintFields().add(2147483647);
        allFieldBean.getSintFields().add(-2);
        allFieldBean.getSintFields().add(-128);
        allFieldBean.getSintFields().add(-16384);
        allFieldBean.getSintFields().add(-2097152);
        allFieldBean.getSintFields().add(-268435456);
        allFieldBean.getSintFields().add(-2147483648);


        allFieldBean.setSlongField(888888888);

        allFieldBean.getSlongFields().add(0L);
        allFieldBean.getSlongFields().add(2L);
        allFieldBean.getSlongFields().add(128L);
        allFieldBean.getSlongFields().add(16384L);
        allFieldBean.getSlongFields().add(2097152L);
        allFieldBean.getSlongFields().add(268435456L);
        allFieldBean.getSlongFields().add(34359738368L);
        allFieldBean.getSlongFields().add(4398046511104L);
        allFieldBean.getSlongFields().add(562949953421312L);
        allFieldBean.getSlongFields().add(72057594037927936L);
        allFieldBean.getSlongFields().add(9223372036854775807L);
        allFieldBean.getSlongFields().add(-2L);
        allFieldBean.getSlongFields().add(-128L);
        allFieldBean.getSlongFields().add(-16384L);
        allFieldBean.getSlongFields().add(-2097152L);
        allFieldBean.getSlongFields().add(-268435456L);
        allFieldBean.getSlongFields().add(-34359738368L);
        allFieldBean.getSlongFields().add(-4398046511104L);
        allFieldBean.getSlongFields().add(-562949953421312L);
        allFieldBean.getSlongFields().add(-72057594037927936L);
        allFieldBean.getSlongFields().add(-9223372036854775808L);

        allFieldBean.setBooleanField(false);

        allFieldBean.getBooleanFields().add(false);
        allFieldBean.getBooleanFields().add(true);
        allFieldBean.getBooleanFields().add(false);

        allFieldBean.setFloatField(12.22F);

        allFieldBean.getFloatFields().add(0.12F);
        allFieldBean.getFloatFields().add(2.12F);
        allFieldBean.getFloatFields().add(128.12F);
        allFieldBean.getFloatFields().add(16384.12F);
        allFieldBean.getFloatFields().add(2097152.12F);
        allFieldBean.getFloatFields().add(268435456.12F);
        allFieldBean.getFloatFields().add(2147483647.12f);
        allFieldBean.getFloatFields().add(-2.12f);
        allFieldBean.getFloatFields().add(-128.124f);
        allFieldBean.getFloatFields().add(-16384.124f);
        allFieldBean.getFloatFields().add(-2097152.124f);
        allFieldBean.getFloatFields().add(-268435456.124f);
        allFieldBean.getFloatFields().add(-2147483648.124f);

        allFieldBean.setDoubleField(-122222.22D);

        allFieldBean.getDoubleFields().add(0.12D);
        allFieldBean.getDoubleFields().add(2.12D);
        allFieldBean.getDoubleFields().add(128.12D);
        allFieldBean.getDoubleFields().add(16384.12D);
        allFieldBean.getDoubleFields().add(2097152.12D);
        allFieldBean.getDoubleFields().add(268435456.12D);
        allFieldBean.getDoubleFields().add(2147483647.12D);
        allFieldBean.getDoubleFields().add(-2.12D);
        allFieldBean.getDoubleFields().add(-128.124D);
        allFieldBean.getDoubleFields().add(-16384.124D);
        allFieldBean.getDoubleFields().add(-2097152.124D);
        allFieldBean.getDoubleFields().add(-268435456.124D);
        allFieldBean.getDoubleFields().add(-2147483648.124D);
        allFieldBean.getDoubleFields().add(Double.MAX_VALUE);
        allFieldBean.getDoubleFields().add(Double.MIN_VALUE);
        allFieldBean.getDoubleFields().add(-4398046511104.124D);
        allFieldBean.getDoubleFields().add(-562949953421312.124D);
        allFieldBean.getDoubleFields().add(-72057594037927936.124D);
        allFieldBean.getDoubleFields().add(4398046511104.124D);
        allFieldBean.getDoubleFields().add(562949953421312.124D);
        allFieldBean.getDoubleFields().add(72057594037927936.124D);

        allFieldBean.setSfixed32Field(12);

        allFieldBean.getSfixed32Fields().add(0);
        allFieldBean.getSfixed32Fields().add(2);
        allFieldBean.getSfixed32Fields().add(128);
        allFieldBean.getSfixed32Fields().add(16384);
        allFieldBean.getSfixed32Fields().add(2097152);
        allFieldBean.getSfixed32Fields().add(268435456);
        allFieldBean.getSfixed32Fields().add(2147483647);
        allFieldBean.getSfixed32Fields().add(-2);
        allFieldBean.getSfixed32Fields().add(-128);
        allFieldBean.getSfixed32Fields().add(-16384);
        allFieldBean.getSfixed32Fields().add(-2097152);
        allFieldBean.getSfixed32Fields().add(-268435456);
        allFieldBean.getSfixed32Fields().add(-2147483648);


        allFieldBean.setSfixed64Field(-666666666);

        allFieldBean.getSfixed64Fields().add(0L);
        allFieldBean.getSfixed64Fields().add(2L);
        allFieldBean.getSfixed64Fields().add(128L);
        allFieldBean.getSfixed64Fields().add(16384L);
        allFieldBean.getSfixed64Fields().add(2097152L);
        allFieldBean.getSfixed64Fields().add(268435456L);
        allFieldBean.getSfixed64Fields().add(34359738368L);
        allFieldBean.getSfixed64Fields().add(4398046511104L);
        allFieldBean.getSfixed64Fields().add(562949953421312L);
        allFieldBean.getSfixed64Fields().add(72057594037927936L);
        allFieldBean.getSfixed64Fields().add(9223372036854775807L);
        allFieldBean.getSfixed64Fields().add(-2L);
        allFieldBean.getSfixed64Fields().add(-128L);
        allFieldBean.getSfixed64Fields().add(-16384L);
        allFieldBean.getSfixed64Fields().add(-2097152L);
        allFieldBean.getSfixed64Fields().add(-268435456L);
        allFieldBean.getSfixed64Fields().add(-34359738368L);
        allFieldBean.getSfixed64Fields().add(-4398046511104L);
        allFieldBean.getSfixed64Fields().add(-562949953421312L);
        allFieldBean.getSfixed64Fields().add(-72057594037927936L);
        allFieldBean.getSfixed64Fields().add(-9223372036854775808L);


        allFieldBean.setStringField("this is a str. 大家好 。");

        allFieldBean.getStringFields().add("this is a str");
        allFieldBean.getStringFields().add("");
        allFieldBean.getStringFields().add("this is a str 大家好");
        allFieldBean.getStringFields().add("大家好");
        allFieldBean.getStringFields().add("");
        allFieldBean.getStringFields().add("hello world ! 向世界问好 !");

        Author author = new Author();
        author.setPhone(12);
        author.setName("author name");
        allFieldBean.setAuthor(author);

        for (int i = 0; i < 5; i++) {

            Book book = new Book();
            book.setName("book Name " + i);
            book.setPrice(998);
            Author a = new Author();
            a.setName("book author" + i);
            a.setPhone(6666 + i);
            book.setAuthor(a);
            allFieldBean.getBooks().add(book);
        }
        return allFieldBean;
    }

    public DemoMessage.AllFieldBean getGoogleBean() {
        DemoMessage.AllFieldBean.Builder builder = DemoMessage.AllFieldBean.newBuilder();

        AllFieldBean allFieldBean = getBean();


        builder.setIntField(allFieldBean.getIntField());
        builder.addAllIntFields(allFieldBean.getIntFields());

        builder.setSintField(allFieldBean.getSintField());
        builder.addAllSintFields(allFieldBean.getSintFields());

        builder.setLongField(allFieldBean.getLongField());
        builder.addAllLongFields(allFieldBean.getLongFields());

        builder.setSlongField(allFieldBean.getSlongField());
        builder.addAllSlongFields(allFieldBean.getSlongFields());

        builder.setFloatField(allFieldBean.getFloatField());
        builder.addAllFloatFields(allFieldBean.getFloatFields());


        builder.setDoubleField(allFieldBean.getDoubleField());
        builder.addAllDoubleFields(allFieldBean.getDoubleFields());

        builder.setBooleanField(allFieldBean.isBooleanField());
        builder.addAllBooleanFields(allFieldBean.getBooleanFields());

        builder.setStringField(allFieldBean.getStringField());
        builder.addAllStringFields(allFieldBean.getStringFields());


        builder.setSfixed32Field(allFieldBean.getSfixed32Field());
        builder.addAllSfixed32Fields(allFieldBean.getSfixed32Fields());
        builder.setSfixed64Field(allFieldBean.getSfixed64Field());
        builder.addAllSfixed64Fields(allFieldBean.getSfixed64Fields());

        DemoMessage.Author.Builder author = DemoMessage.Author.newBuilder();
        author.setName("author name");
        author.setPhone(12);
        builder.setAuthor(author.build());
        List<DemoMessage.Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DemoMessage.Book.Builder book = DemoMessage.Book.newBuilder();
            book.setName("book Name " + i);
            book.setPrice(998);
            DemoMessage.Author.Builder a = DemoMessage.Author.newBuilder();
            a.setName("book author" + i);
            a.setPhone(6666 + i);
            book.setAuthor(a.build());
            books.add(book.build());
        }

        builder.addAllBooks(books);
        return builder.build();
    }

    int count = 100000;

    @Test
    public void google() {

        System.out.println("Google probufer 3");
        DemoMessage.AllFieldBean googleBean = getGoogleBean();
        String str = googleBean.toString();
        byte[] bytes = googleBean.toByteArray();
        System.out.println(str);
        System.out.println("序列化后大小 " + bytes.length + "b");
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            googleBean.toByteArray();
        }
        System.out.println("序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));
        try {
            DemoMessage.AllFieldBean bean = DemoMessage.AllFieldBean.parseFrom(bytes);
            System.out.println("前后toString比较 " + bean.toString().equals(str));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        try {
            time = System.currentTimeMillis();
            for (int i = 0; i < count; i++) {
                DemoMessage.AllFieldBean.parseFrom(bytes);
            }
            System.out.println("反序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void senpure() {
        AllFieldBean bean = getBean();
        ByteBuf buf = Unpooled.buffer();
        buf.ensureWritable(bean.getSerializedSize());
        System.out.println("senpure协议");
        //System.out.println("对象实体\n"+bean.toString(""));
        String str = bean.toString("");
        System.out.println(bean.toString(""));
        bean.write(buf);
        System.out.println("序列化后大小 " + buf.writerIndex() + "b");
        int count = 100000;
        long time = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            ByteBuf byteBuf = Unpooled.buffer();

            bean.write(byteBuf);
        }
        System.out.println("序列化  [" + count + "]次,用时" + (System.currentTimeMillis() - time));
        AllFieldBean receiveBean = new AllFieldBean();
        receiveBean.read(buf, buf.writerIndex());

        System.out.println("前后toString比较 " + receiveBean.toString("").equals(str));
        time = System.currentTimeMillis();
        buf.resetReaderIndex();
        for (int i = 0; i < count; i++) {
            AllFieldBean receive = new AllFieldBean();
            receive.read(buf, buf.writerIndex());
            buf.resetReaderIndex();
        }
        System.out.println("反序列化[" + count + "]次,用时" + (System.currentTimeMillis() - time));
    }


}
