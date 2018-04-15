package com.senpure.generator.message.demo.bean;

import com.senpure.io.protocol.Bean;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.ArrayList;

/**
* 全部的字段的bean
* 
* @author senpure-generator
* @version 2018-4-15 10:31:50
*/
public class AllFieldBean extends  Bean {
    //int
    private int intField;
    //ints
    private List<Integer> intFields = new ArrayList(16);
    //long
    private long longField;
    //longs
    private List<Long> longFields = new ArrayList(16);
    //sint
    private int sintField;
    //sints
    private List<Integer> sintFields = new ArrayList(16);
    //slong
    private long slongField;
    //slongs
    private List<Long> slongFields = new ArrayList(16);
    //sfixed32
    private int sfixed32Field;
    //sfixed32s
    private List<Integer> sfixed32Fields = new ArrayList(16);
    //sfixed64
    private long sfixed64Field;
    //sfixed64s
    private List<Long> sfixed64Fields = new ArrayList(16);
    //float
    private float floatField;
    //floats
    private List<Float> floatFields = new ArrayList(16);
    //double
    private double doubleField;
    //doubles
    private List<Double> doubleFields = new ArrayList(16);
    //boolean
    private boolean booleanField;
    //booleans
    private List<Boolean> booleanFields = new ArrayList(16);
    //string
    private String stringField;
    //strings
    private List<String> stringFields = new ArrayList(16);
    //author
    private Author author;
    //books
    private List<Book> books = new ArrayList(16);
    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
        getSerializedSize();
        //int
        writeVar32(buf,11,intField);
        //ints
        if (intFields.size() > 0){
            writeVar32(buf,19);
            writeVar32(buf,intFieldsSerializedSize);
            for (int i= 0;i< intFields.size();i++){
                writeVar32(buf,intFields.get(i));
            }
        }
        //long
        writeVar64(buf,27,longField);
        //longs
        if (longFields.size() > 0){
            writeVar32(buf,35);
            writeVar32(buf,longFieldsSerializedSize);
            for (int i= 0;i< longFields.size();i++){
                writeVar64(buf,longFields.get(i));
            }
        }
        //sint
        writeSInt(buf,43,sintField);
        //sints
        if (sintFields.size() > 0){
            writeVar32(buf,51);
            writeVar32(buf,sintFieldsSerializedSize);
            for (int i= 0;i< sintFields.size();i++){
                writeSInt(buf,sintFields.get(i));
            }
        }
        //slong
        writeSLong(buf,59,slongField);
        //slongs
        if (slongFields.size() > 0){
            writeVar32(buf,67);
            writeVar32(buf,slongFieldsSerializedSize);
            for (int i= 0;i< slongFields.size();i++){
                writeSLong(buf,slongFields.get(i));
            }
        }
        //sfixed32
        writeSFixed32(buf,75,sfixed32Field);
        //sfixed32s
        if (sfixed32Fields.size() > 0){
            writeVar32(buf,83);
            writeVar32(buf,sfixed32FieldsSerializedSize);
            for (int i= 0;i< sfixed32Fields.size();i++){
                writeSFixed32(buf,sfixed32Fields.get(i));
            }
        }
        //sfixed64
        writeSFixed64(buf,91,sfixed64Field);
        //sfixed64s
        if (sfixed64Fields.size() > 0){
            writeVar32(buf,99);
            writeVar32(buf,sfixed64FieldsSerializedSize);
            for (int i= 0;i< sfixed64Fields.size();i++){
                writeSFixed64(buf,sfixed64Fields.get(i));
            }
        }
        //float
        writeFloat(buf,107,floatField);
        //floats
        if (floatFields.size() > 0){
            writeVar32(buf,115);
            writeVar32(buf,floatFieldsSerializedSize);
            for (int i= 0;i< floatFields.size();i++){
                writeFloat(buf,floatFields.get(i));
            }
        }
        //double
        writeDouble(buf,123,doubleField);
        //doubles
        if (doubleFields.size() > 0){
            writeVar32(buf,131);
            writeVar32(buf,doubleFieldsSerializedSize);
            for (int i= 0;i< doubleFields.size();i++){
                writeDouble(buf,doubleFields.get(i));
            }
        }
        //boolean
        writeBoolean(buf,139,booleanField);
        //booleans
        if (booleanFields.size() > 0){
            writeVar32(buf,147);
            writeVar32(buf,booleanFieldsSerializedSize);
            for (int i= 0;i< booleanFields.size();i++){
                writeBoolean(buf,booleanFields.get(i));
            }
        }
        //string
        if (stringField != null){
            writeString(buf,155,stringField);
        }
        //strings
        for (int i= 0;i< stringFields.size();i++){
            writeString(buf,163,stringFields.get(i));
        }
        //author
        if (author!= null){
            writeBean(buf,171,author);
        }
        //books
        for (int i= 0;i< books.size();i++){
            writeBean(buf,179,books.get(i));
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
                //int
                case 11:// 1 << 3 | 3
                        intField = readVar32(buf);
                    break;
                //ints
                case 19:// 2 << 3 | 3
                    int intFieldsDataSize = readVar32(buf);
                    int receiveIntFieldsDataSize = 0;
                    do {
                        int tempIntFields = readVar32(buf);
                        receiveIntFieldsDataSize += computeVar32SizeNoTag(tempIntFields);
                        intFields.add(tempIntFields);
                    }
                    while(receiveIntFieldsDataSize < intFieldsDataSize );
                    break;
                //long
                case 27:// 3 << 3 | 3
                        longField = readVar64(buf);
                    break;
                //longs
                case 35:// 4 << 3 | 3
                    int longFieldsDataSize = readVar32(buf);
                    int receiveLongFieldsDataSize = 0;
                    do {
                        long tempLongFields = readVar64(buf);
                        receiveLongFieldsDataSize += computeVar64SizeNoTag(tempLongFields);
                        longFields.add(tempLongFields);
                    }
                    while(receiveLongFieldsDataSize < longFieldsDataSize );
                    break;
                //sint
                case 43:// 5 << 3 | 3
                        sintField = readSInt(buf);
                    break;
                //sints
                case 51:// 6 << 3 | 3
                    int sintFieldsDataSize = readVar32(buf);
                    int receiveSintFieldsDataSize = 0;
                    do {
                        int tempSintFields = readSInt(buf);
                        receiveSintFieldsDataSize += computeSIntSizeNoTag(tempSintFields);
                        sintFields.add(tempSintFields);
                    }
                    while(receiveSintFieldsDataSize < sintFieldsDataSize );
                    break;
                //slong
                case 59:// 7 << 3 | 3
                        slongField = readSLong(buf);
                    break;
                //slongs
                case 67:// 8 << 3 | 3
                    int slongFieldsDataSize = readVar32(buf);
                    int receiveSlongFieldsDataSize = 0;
                    do {
                        long tempSlongFields = readSLong(buf);
                        receiveSlongFieldsDataSize += computeSLongSizeNoTag(tempSlongFields);
                        slongFields.add(tempSlongFields);
                    }
                    while(receiveSlongFieldsDataSize < slongFieldsDataSize );
                    break;
                //sfixed32
                case 75:// 9 << 3 | 3
                        sfixed32Field = readSFixed32(buf);
                    break;
                //sfixed32s
                case 83:// 10 << 3 | 3
                    int sfixed32FieldsDataSize = readVar32(buf);
                    int receiveSfixed32FieldsDataSize = 0;
                    do {
                        int tempSfixed32Fields = readSFixed32(buf);
                        receiveSfixed32FieldsDataSize += computeSFixed32SizeNoTag(tempSfixed32Fields);
                        sfixed32Fields.add(tempSfixed32Fields);
                    }
                    while(receiveSfixed32FieldsDataSize < sfixed32FieldsDataSize );
                    break;
                //sfixed64
                case 91:// 11 << 3 | 3
                        sfixed64Field = readSFixed64(buf);
                    break;
                //sfixed64s
                case 99:// 12 << 3 | 3
                    int sfixed64FieldsDataSize = readVar32(buf);
                    int receiveSfixed64FieldsDataSize = 0;
                    do {
                        long tempSfixed64Fields = readSFixed64(buf);
                        receiveSfixed64FieldsDataSize += computeSFixed64SizeNoTag(tempSfixed64Fields);
                        sfixed64Fields.add(tempSfixed64Fields);
                    }
                    while(receiveSfixed64FieldsDataSize < sfixed64FieldsDataSize );
                    break;
                //float
                case 107:// 13 << 3 | 3
                        floatField = readFloat(buf);
                    break;
                //floats
                case 115:// 14 << 3 | 3
                    int floatFieldsDataSize = readVar32(buf);
                    int receiveFloatFieldsDataSize = 0;
                    do {
                        float tempFloatFields = readFloat(buf);
                        receiveFloatFieldsDataSize += computeFloatSizeNoTag(tempFloatFields);
                        floatFields.add(tempFloatFields);
                    }
                    while(receiveFloatFieldsDataSize < floatFieldsDataSize );
                    break;
                //double
                case 123:// 15 << 3 | 3
                        doubleField = readDouble(buf);
                    break;
                //doubles
                case 131:// 16 << 3 | 3
                    int doubleFieldsDataSize = readVar32(buf);
                    int receiveDoubleFieldsDataSize = 0;
                    do {
                        double tempDoubleFields = readDouble(buf);
                        receiveDoubleFieldsDataSize += computeDoubleSizeNoTag(tempDoubleFields);
                        doubleFields.add(tempDoubleFields);
                    }
                    while(receiveDoubleFieldsDataSize < doubleFieldsDataSize );
                    break;
                //boolean
                case 139:// 17 << 3 | 3
                        booleanField = readBoolean(buf);
                    break;
                //booleans
                case 147:// 18 << 3 | 3
                    int booleanFieldsDataSize = readVar32(buf);
                    int receiveBooleanFieldsDataSize = 0;
                    do {
                        boolean tempBooleanFields = readBoolean(buf);
                        receiveBooleanFieldsDataSize += computeBooleanSizeNoTag(tempBooleanFields);
                        booleanFields.add(tempBooleanFields);
                    }
                    while(receiveBooleanFieldsDataSize < booleanFieldsDataSize );
                    break;
                //string
                case 155:// 19 << 3 | 3
                        stringField = readString(buf);
                    break;
                //strings
                case 163:// 20 << 3 | 3
                        stringFields.add(readString(buf));
                    break;
                //author
                case 171:// 21 << 3 | 3
                author = new Author();
                        readBean(buf,author);
                    break;
                //books
                case 179:// 22 << 3 | 3
                        Book tempBooksBean = new Book();
                        readBean(buf,tempBooksBean);
                        books.add(tempBooksBean);
                    break;
                default://skip
                    skip(buf, tag);
                    break;
            }
        }
    }

    private int serializedSize = -1;
    private int intFieldsSerializedSize = 0;
    private int longFieldsSerializedSize = 0;
    private int sintFieldsSerializedSize = 0;
    private int slongFieldsSerializedSize = 0;
    private int sfixed32FieldsSerializedSize = 0;
    private int sfixed64FieldsSerializedSize = 0;
    private int floatFieldsSerializedSize = 0;
    private int doubleFieldsSerializedSize = 0;
    private int booleanFieldsSerializedSize = 0;

    @Override
    public int getSerializedSize(){
        int size = serializedSize ;
        if (size != -1 ){
            return size;
        }
        size = 0 ;
        //int
        size += computeVar32Size(11,intField);
        //ints
            int intFieldsDataSize = 0;
        for(int i=0;i< intFields.size();i++){
            intFieldsDataSize += computeVar32SizeNoTag(intFields.get(i));
        }
        intFieldsSerializedSize = intFieldsDataSize;
        if(intFieldsDataSize > 0 ){
            //tag size 19
            size += 1;
            size += intFieldsSerializedSize;
            size += computeVar32SizeNoTag(intFieldsSerializedSize);
        }
        //long
        size += computeVar64Size(27,longField);
        //longs
            int longFieldsDataSize = 0;
        for(int i=0;i< longFields.size();i++){
            longFieldsDataSize += computeVar64SizeNoTag(longFields.get(i));
        }
        longFieldsSerializedSize = longFieldsDataSize;
        if(longFieldsDataSize > 0 ){
            //tag size 35
            size += 1;
            size += longFieldsSerializedSize;
            size += computeVar32SizeNoTag(longFieldsSerializedSize);
        }
        //sint
        size += computeSIntSize(43,sintField);
        //sints
            int sintFieldsDataSize = 0;
        for(int i=0;i< sintFields.size();i++){
            sintFieldsDataSize += computeSIntSizeNoTag(sintFields.get(i));
        }
        sintFieldsSerializedSize = sintFieldsDataSize;
        if(sintFieldsDataSize > 0 ){
            //tag size 51
            size += 1;
            size += sintFieldsSerializedSize;
            size += computeVar32SizeNoTag(sintFieldsSerializedSize);
        }
        //slong
        size += computeSLongSize(59,slongField);
        //slongs
            int slongFieldsDataSize = 0;
        for(int i=0;i< slongFields.size();i++){
            slongFieldsDataSize += computeSLongSizeNoTag(slongFields.get(i));
        }
        slongFieldsSerializedSize = slongFieldsDataSize;
        if(slongFieldsDataSize > 0 ){
            //tag size 67
            size += 1;
            size += slongFieldsSerializedSize;
            size += computeVar32SizeNoTag(slongFieldsSerializedSize);
        }
        //sfixed32
        size += computeSFixed32Size(75,sfixed32Field);
        //sfixed32s
            int sfixed32FieldsDataSize = 0;
        for(int i=0;i< sfixed32Fields.size();i++){
            sfixed32FieldsDataSize += computeSFixed32SizeNoTag(sfixed32Fields.get(i));
        }
        sfixed32FieldsSerializedSize = sfixed32FieldsDataSize;
        if(sfixed32FieldsDataSize > 0 ){
            //tag size 83
            size += 1;
            size += sfixed32FieldsSerializedSize;
            size += computeVar32SizeNoTag(sfixed32FieldsSerializedSize);
        }
        //sfixed64
        size += computeSFixed64Size(91,sfixed64Field);
        //sfixed64s
            int sfixed64FieldsDataSize = 0;
        for(int i=0;i< sfixed64Fields.size();i++){
            sfixed64FieldsDataSize += computeSFixed64SizeNoTag(sfixed64Fields.get(i));
        }
        sfixed64FieldsSerializedSize = sfixed64FieldsDataSize;
        if(sfixed64FieldsDataSize > 0 ){
            //tag size 99
            size += 1;
            size += sfixed64FieldsSerializedSize;
            size += computeVar32SizeNoTag(sfixed64FieldsSerializedSize);
        }
        //float
        size += computeFloatSize(107,floatField);
        //floats
            int floatFieldsDataSize = 0;
        for(int i=0;i< floatFields.size();i++){
            floatFieldsDataSize += computeFloatSizeNoTag(floatFields.get(i));
        }
        floatFieldsSerializedSize = floatFieldsDataSize;
        if(floatFieldsDataSize > 0 ){
            //tag size 115
            size += 1;
            size += floatFieldsSerializedSize;
            size += computeVar32SizeNoTag(floatFieldsSerializedSize);
        }
        //double
        size += computeDoubleSize(123,doubleField);
        //doubles
            int doubleFieldsDataSize = 0;
        for(int i=0;i< doubleFields.size();i++){
            doubleFieldsDataSize += computeDoubleSizeNoTag(doubleFields.get(i));
        }
        doubleFieldsSerializedSize = doubleFieldsDataSize;
        if(doubleFieldsDataSize > 0 ){
            //tag size 131
            size += 2;
            size += doubleFieldsSerializedSize;
            size += computeVar32SizeNoTag(doubleFieldsSerializedSize);
        }
        //boolean
        size += computeBooleanSize(139,booleanField);
        //booleans
            int booleanFieldsDataSize = 0;
        for(int i=0;i< booleanFields.size();i++){
            booleanFieldsDataSize += computeBooleanSizeNoTag(booleanFields.get(i));
        }
        booleanFieldsSerializedSize = booleanFieldsDataSize;
        if(booleanFieldsDataSize > 0 ){
            //tag size 147
            size += 2;
            size += booleanFieldsSerializedSize;
            size += computeVar32SizeNoTag(booleanFieldsSerializedSize);
        }
        //string
        if (stringField != null){
            size += computeStringSize(155,stringField);
        }
        //strings
        for(int i=0;i< stringFields.size();i++){
            size += computeStringSize(163,stringFields.get(i));
        }
        //author
        if (author != null){
            size += computeBeanSize(171,author);
        }
        //books
        for(int i=0;i< books.size();i++){
            size += computeBeanSize(179,books.get(i));
        }
        serializedSize = size ;
        return size ;
    }

    /**
     * get int
     * @return
     */
    public  int getIntField() {
        return intField;
    }

    /**
     * set int
     */
    public AllFieldBean setIntField(int intField) {
        this.intField=intField;
        return this;
    }
     /**
      * get ints
      * @return
      */
    public List<Integer> getIntFields(){
        return intFields;
    }
     /**
      * set ints
      */
    public AllFieldBean setIntFields (List<Integer> intFields){
        this.intFields=intFields;
        return this;
    }

    /**
     * get long
     * @return
     */
    public  long getLongField() {
        return longField;
    }

    /**
     * set long
     */
    public AllFieldBean setLongField(long longField) {
        this.longField=longField;
        return this;
    }
     /**
      * get longs
      * @return
      */
    public List<Long> getLongFields(){
        return longFields;
    }
     /**
      * set longs
      */
    public AllFieldBean setLongFields (List<Long> longFields){
        this.longFields=longFields;
        return this;
    }

    /**
     * get sint
     * @return
     */
    public  int getSintField() {
        return sintField;
    }

    /**
     * set sint
     */
    public AllFieldBean setSintField(int sintField) {
        this.sintField=sintField;
        return this;
    }
     /**
      * get sints
      * @return
      */
    public List<Integer> getSintFields(){
        return sintFields;
    }
     /**
      * set sints
      */
    public AllFieldBean setSintFields (List<Integer> sintFields){
        this.sintFields=sintFields;
        return this;
    }

    /**
     * get slong
     * @return
     */
    public  long getSlongField() {
        return slongField;
    }

    /**
     * set slong
     */
    public AllFieldBean setSlongField(long slongField) {
        this.slongField=slongField;
        return this;
    }
     /**
      * get slongs
      * @return
      */
    public List<Long> getSlongFields(){
        return slongFields;
    }
     /**
      * set slongs
      */
    public AllFieldBean setSlongFields (List<Long> slongFields){
        this.slongFields=slongFields;
        return this;
    }

    /**
     * get sfixed32
     * @return
     */
    public  int getSfixed32Field() {
        return sfixed32Field;
    }

    /**
     * set sfixed32
     */
    public AllFieldBean setSfixed32Field(int sfixed32Field) {
        this.sfixed32Field=sfixed32Field;
        return this;
    }
     /**
      * get sfixed32s
      * @return
      */
    public List<Integer> getSfixed32Fields(){
        return sfixed32Fields;
    }
     /**
      * set sfixed32s
      */
    public AllFieldBean setSfixed32Fields (List<Integer> sfixed32Fields){
        this.sfixed32Fields=sfixed32Fields;
        return this;
    }

    /**
     * get sfixed64
     * @return
     */
    public  long getSfixed64Field() {
        return sfixed64Field;
    }

    /**
     * set sfixed64
     */
    public AllFieldBean setSfixed64Field(long sfixed64Field) {
        this.sfixed64Field=sfixed64Field;
        return this;
    }
     /**
      * get sfixed64s
      * @return
      */
    public List<Long> getSfixed64Fields(){
        return sfixed64Fields;
    }
     /**
      * set sfixed64s
      */
    public AllFieldBean setSfixed64Fields (List<Long> sfixed64Fields){
        this.sfixed64Fields=sfixed64Fields;
        return this;
    }

    /**
     * get float
     * @return
     */
    public  float getFloatField() {
        return floatField;
    }

    /**
     * set float
     */
    public AllFieldBean setFloatField(float floatField) {
        this.floatField=floatField;
        return this;
    }
     /**
      * get floats
      * @return
      */
    public List<Float> getFloatFields(){
        return floatFields;
    }
     /**
      * set floats
      */
    public AllFieldBean setFloatFields (List<Float> floatFields){
        this.floatFields=floatFields;
        return this;
    }

    /**
     * get double
     * @return
     */
    public  double getDoubleField() {
        return doubleField;
    }

    /**
     * set double
     */
    public AllFieldBean setDoubleField(double doubleField) {
        this.doubleField=doubleField;
        return this;
    }
     /**
      * get doubles
      * @return
      */
    public List<Double> getDoubleFields(){
        return doubleFields;
    }
     /**
      * set doubles
      */
    public AllFieldBean setDoubleFields (List<Double> doubleFields){
        this.doubleFields=doubleFields;
        return this;
    }

    /**
     *  is boolean
     * @return
     */
    public  boolean  isBooleanField() {
        return booleanField;
    }

    /**
     * set boolean
     */
    public AllFieldBean setBooleanField(boolean booleanField) {
        this.booleanField=booleanField;
        return this;
    }
     /**
      * get booleans
      * @return
      */
    public List<Boolean> getBooleanFields(){
        return booleanFields;
    }
     /**
      * set booleans
      */
    public AllFieldBean setBooleanFields (List<Boolean> booleanFields){
        this.booleanFields=booleanFields;
        return this;
    }

    /**
     * get string
     * @return
     */
    public  String getStringField() {
        return stringField;
    }

    /**
     * set string
     */
    public AllFieldBean setStringField(String stringField) {
        this.stringField=stringField;
        return this;
    }
     /**
      * get strings
      * @return
      */
    public List<String> getStringFields(){
        return stringFields;
    }
     /**
      * set strings
      */
    public AllFieldBean setStringFields (List<String> stringFields){
        this.stringFields=stringFields;
        return this;
    }

    /**
     * get author
     * @return
     */
    public  Author getAuthor() {
        return author;
    }

    /**
     * set author
     */
    public AllFieldBean setAuthor(Author author) {
        this.author=author;
        return this;
    }
     /**
      * get books
      * @return
      */
    public List<Book> getBooks(){
        return books;
    }
     /**
      * set books
      */
    public AllFieldBean setBooks (List<Book> books){
        this.books=books;
        return this;
    }


    @Override
    public String toString() {
        return "AllFieldBean{"
                +"intField=" + intField
                +",intFields=" + intFields
                +",longField=" + longField
                +",longFields=" + longFields
                +",sintField=" + sintField
                +",sintFields=" + sintFields
                +",slongField=" + slongField
                +",slongFields=" + slongFields
                +",sfixed32Field=" + sfixed32Field
                +",sfixed32Fields=" + sfixed32Fields
                +",sfixed64Field=" + sfixed64Field
                +",sfixed64Fields=" + sfixed64Fields
                +",floatField=" + floatField
                +",floatFields=" + floatFields
                +",doubleField=" + doubleField
                +",doubleFields=" + doubleFields
                +",booleanField=" + booleanField
                +",booleanFields=" + booleanFields
                +",stringField=" + stringField
                +",stringFields=" + stringFields
                +",author=" + author
                +",books=" + books
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
        sb.append("AllFieldBean").append("{");
        //int
        sb.append("\n");
        sb.append(indent).append(rightPad("intField", filedPad)).append(" = ").append(intField);
        //ints
        sb.append("\n");
        sb.append(indent).append(rightPad("intFields", filedPad)).append(" = ");
        int intFieldsSize = intFields.size();
        if (intFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<intFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(intFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //long
        sb.append("\n");
        sb.append(indent).append(rightPad("longField", filedPad)).append(" = ").append(longField);
        //longs
        sb.append("\n");
        sb.append(indent).append(rightPad("longFields", filedPad)).append(" = ");
        int longFieldsSize = longFields.size();
        if (longFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<longFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(longFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //sint
        sb.append("\n");
        sb.append(indent).append(rightPad("sintField", filedPad)).append(" = ").append(sintField);
        //sints
        sb.append("\n");
        sb.append(indent).append(rightPad("sintFields", filedPad)).append(" = ");
        int sintFieldsSize = sintFields.size();
        if (sintFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<sintFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(sintFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //slong
        sb.append("\n");
        sb.append(indent).append(rightPad("slongField", filedPad)).append(" = ").append(slongField);
        //slongs
        sb.append("\n");
        sb.append(indent).append(rightPad("slongFields", filedPad)).append(" = ");
        int slongFieldsSize = slongFields.size();
        if (slongFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<slongFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(slongFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //sfixed32
        sb.append("\n");
        sb.append(indent).append(rightPad("sfixed32Field", filedPad)).append(" = ").append(sfixed32Field);
        //sfixed32s
        sb.append("\n");
        sb.append(indent).append(rightPad("sfixed32Fields", filedPad)).append(" = ");
        int sfixed32FieldsSize = sfixed32Fields.size();
        if (sfixed32FieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<sfixed32FieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(sfixed32Fields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //sfixed64
        sb.append("\n");
        sb.append(indent).append(rightPad("sfixed64Field", filedPad)).append(" = ").append(sfixed64Field);
        //sfixed64s
        sb.append("\n");
        sb.append(indent).append(rightPad("sfixed64Fields", filedPad)).append(" = ");
        int sfixed64FieldsSize = sfixed64Fields.size();
        if (sfixed64FieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<sfixed64FieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(sfixed64Fields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //float
        sb.append("\n");
        sb.append(indent).append(rightPad("floatField", filedPad)).append(" = ").append(floatField);
        //floats
        sb.append("\n");
        sb.append(indent).append(rightPad("floatFields", filedPad)).append(" = ");
        int floatFieldsSize = floatFields.size();
        if (floatFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<floatFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(floatFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //double
        sb.append("\n");
        sb.append(indent).append(rightPad("doubleField", filedPad)).append(" = ").append(doubleField);
        //doubles
        sb.append("\n");
        sb.append(indent).append(rightPad("doubleFields", filedPad)).append(" = ");
        int doubleFieldsSize = doubleFields.size();
        if (doubleFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<doubleFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(doubleFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //boolean
        sb.append("\n");
        sb.append(indent).append(rightPad("booleanField", filedPad)).append(" = ").append(booleanField);
        //booleans
        sb.append("\n");
        sb.append(indent).append(rightPad("booleanFields", filedPad)).append(" = ");
        int booleanFieldsSize = booleanFields.size();
        if (booleanFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<booleanFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(booleanFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //string
        sb.append("\n");
        sb.append(indent).append(rightPad("stringField", filedPad)).append(" = ").append(stringField);
        //strings
        sb.append("\n");
        sb.append(indent).append(rightPad("stringFields", filedPad)).append(" = ");
        int stringFieldsSize = stringFields.size();
        if (stringFieldsSize > 0) {
            sb.append("[");
            for (int i = 0; i<stringFieldsSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(stringFields.get(i));
            }
            sb.append("\n");
        sb.append(nextIndent);
            sb.append(indent).append("]");
        }else {
            sb.append("null");
        }

        //author
        sb.append("\n");
        sb.append(indent).append(rightPad("author", filedPad)).append(" = ");
        if(author!=null){
            sb.append(author.toString(indent+nextIndent));
        } else {
            sb.append("null");
        }
        //books
        sb.append("\n");
        sb.append(indent).append(rightPad("books", filedPad)).append(" = ");
        int booksSize = books.size();
        if (booksSize > 0) {
            sb.append("[");
            for (int i = 0; i<booksSize;i++) {
                sb.append("\n");
                sb.append(nextIndent);
                sb.append(indent).append(books.get(i).toString(indent + nextIndent));
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