<#global "int"="Integer"/>
<#list fields as field>
<#if field.hasExplain>
    //${field.explain}
</#if>
<#if field.list >
    private List<${.globals[field.classType]!field.classType?cap_first}> ${field.name}=new ArrayList(<#if field.capacity gt 0>${field.capacity}</#if>);
<#else>
    private ${field.classType} ${field.name};
</#if>
</#list>

    /**
     * 写入字节缓存
     */
    @Override
    public void write(ByteBuf buf){
      <#list fields as field>
          <#if field.hasExplain>
        //${field.explain}
          </#if>
         <#if field.list>
        int ${field.name}Size=${field.name}.size();
        writeShort(buf,${field.name}Size);
        for(int i=0;i< ${field.name}Size;i++){
             <#if field.classType="boolean">
            writeBoolean(buf,${field.name}.get(i));
             <#elseif field.classType="byte">
            writeByte(buf,${field.name}.get(i));
             <#elseif field.classType="short">
            writeShort(buf,${field.name}.get(i));
             <#elseif field.classType="int">
            writeInt(buf,${field.name}.get(i));
             <#elseif field.classType="float">
            writeFloat(buf,${field.name}.get(i));
             <#elseif field.classType="double">
            writeDouble(buf,${field.name}.get(i));
             <#elseif field.classType="long">
            writeLong(buf,${field.name}.get(i));
             <#elseif field.classType="String">
            writeStr(buf,${field.name}.get(i));
             <#else>
            writeBean(buf,${field.name}.get(i),false);
             </#if>
           }
             <#else>
         <#if field.classType="boolean">
        writeBoolean(buf,${field.name});
         <#elseif field.classType="byte">
        writeByte(buf,${field.name});
         <#elseif field.classType="short">
        writeShort(buf,${field.name});
         <#elseif field.classType="int">
        writeInt(buf,${field.name});
         <#elseif field.classType="float">
        writeFloat(buf,${field.name});
         <#elseif field.classType="double">
        writeDouble(buf,${field.name});
         <#elseif field.classType="long">
        writeLong(buf,${field.name});
         <#elseif field.classType="String">
        writeStr(buf,${field.name});
         <#else>
        writeBean(buf,${field.name},true);
         </#if>
         </#if>
       </#list>
    }


    /**
     * 读取字节缓存
     */
    @Override
    public void read(ByteBuf buf){
<#list fields as field>
    <#if field.hasExplain>
        //${field.explain}
    </#if>
    <#if field.list>
        int ${field.name}Size=readShort(buf);
        for(int i=0;i<${field.name}Size;i++){
        <#if field.classType="boolean">
            this.${field.name}.add(readBoolean(buf));
        <#elseif field.classType="byte">
            this.${field.name}.add(readByte(buf));
        <#elseif field.classType="short">
            this.${field.name}.add(readShort(buf));
        <#elseif field.classType="int">
            this.${field.name}.add(readInt(buf));
        <#elseif field.classType="float">
            this.${field.name}.add(readFloat(buf));
        <#elseif field.classType="double">
            this.${field.name}.add(readDouble(buf));
        <#elseif field.classType="long">
            this.${field.name}.add(readLong(buf));
        <#elseif field.classType="String">
            this.${field.name}.add(readStr(buf));
        <#else>
            this.${field.name}.add((${field.classType})readBean(buf,${field.classType}.class,false));
        </#if>
         }
    <#else>
        <#if field.classType="boolean">
        this.${field.name} = readBoolean(buf);
        <#elseif field.classType="byte">
        this.${field.name} = readByte(buf);
        <#elseif field.classType="short">
        this.${field.name} =readShort(buf);
        <#elseif field.classType="int">
        this.${field.name} = readInt(buf);
        <#elseif field.classType="float">
        this.${field.name} = readFloat(buf);
        <#elseif field.classType="double">
        this.${field.name} = readDouble(buf);
        <#elseif field.classType="long">
        this.${field.name} = readLong(buf);
        <#elseif field.classType="String">
        this.${field.name }= readStr(buf);
        <#else>
        this.${field.name} = (${field.classType})readBean(buf,${field.classType}.class,true);
        </#if>
    </#if>
</#list>
    }

<#list fields as field>
    <#if field.list>
        <#if field.hasExplain&&field.explain?length gt 2>
     /**
      * get ${field.explain}
      * @return
      */
        </#if>
    public List<${.globals[field.classType]!field.classType?cap_first}> get${field.name?cap_first}(){
        return ${field.name};
    }
        <#if field.hasExplain&&field.explain?length gt 2>
     /**
      * set ${field.explain}
      */
        </#if>
    public ${name} set${field.name?cap_first} (List<${.globals[field.classType]!field.classType?cap_first}> ${field.name}){
        this.${field.name}=${field.name};
        return this;
    }

    <#else>
        <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * <#if field.classType="boolean"> is<#else>get</#if> ${field.explain}
     * @return
     */
        </#if>
    public  ${field.classType} <#if field.classType="boolean"> is<#else>get</#if>${field.name?cap_first}() {
        return ${field.name};
    }

        <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * set ${field.explain}
     */
        </#if>
    public ${name} set${field.name?cap_first}(${field.classType} ${field.name}) {
        this.${field.name}=${field.name};
        return this;
    }
    </#if>
</#list>