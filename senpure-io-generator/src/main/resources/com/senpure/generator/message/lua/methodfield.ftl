<#if bean.hasExplain>
--[[
    ${bean.explain}
--]]
</#if>
${luaNamespace!""}${bean.name} = {
<#if bean.type !="NA">
    --[Comment]
    --message_id
    id = ${bean.id?c};
  <#--
    _message_name = "${luaNameStyle(bean.name)?lower_case?substring(1)}";
    -->
</#if>
<#list bean.fields as field>
    <#if field.list >
    --[Comment]
    --list:<#if field.baseField>${rightPad(field.classType,7)}<#else>${luaNamespace!""}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    <#else ><#--不是list-->
    --[Comment]
    --类型:<#if field.baseField>${rightPad(field.classType,7)}<#else>${luaNamespace!""}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    </#if>
    <#if field.list >
    <#assign hasNextIndent= true>
    ${field.name} = nil;
    <#else ><#--不是list-->
        <#if field.baseField>
            <#if field.classType == "String">
    ${field.name} = "";
            <#elseif field.classType == "boolean">
    ${field.name} = false;
            <#else >
    ${field.name} = 0;
            </#if>
        <#else>
    ${field.name}= nil ;<#--bean 引用-->
        </#if>
    </#if>
</#list>
<#if bean.hasBean||hasNextIndent!false>
    --[Comment]
    --缩进${bean.fieldMaxLen} + 3 = ${bean.fieldMaxLen+3} 个空格
    _next_indent = "<#list 1..bean.fieldMaxLen+3 as i> </#list>";
</#if>
    --[Comment]
    --格式化时统一字段长度
    _filedPad = ${bean.fieldMaxLen} ;
}

<#if bean.type =="NA"|| bean.type?ends_with("S")>
--${bean.name}写入字节缓存
function ${luaNamespace!""}${bean.name}.write(self,buf)
     <#if bean.type?ends_with("S")>
    --消息协议id
    buf:WriteInt(${bean.id?c})
     </#if>
    <#list bean.fields as field>
        <#if field.hasExplain>
    --${field.explain}
        </#if>
    <#if field.list>
    if self.${field.name} then
        local ${field.name}_len = #self.${field.name}
        buf:WriteShort(${field.name}_len)
        if ${field.name}_len > 0 then
            for i = 1, ${field.name}_len do
        <#if field.classType="boolean">
                buf:WriteBool(self.${field.name}[i])
        <#elseif field.classType="byte">
                buf:WriteRawByte(self.${field.name}[i])
        <#elseif field.classType="short">
                buf:WriteShort(self.${field.name}[i])
        <#elseif field.classType="int">
                buf:WriteInt(self.${field.name}[i])
        <#elseif field.classType="float">
                buf:WriteFloat(self.${field.name}[i])
        <#elseif field.classType="double">
                buf:WriteDouble(self.${field.name}[i])
        <#elseif field.classType="long">
                buf:WriteLong(buf,self.${field.name}[i])
        <#elseif field.classType="String">
                buf:WriteString(buf,self.${field.name}[i])
        <#else >
                ${luaNamespace!""}${field.classType}.write(buf,self)
        </#if>
            end
        end
    end
    <#else ><#-- 不是list -->
        <#if field.classType="boolean">
    if self.${field.name} == nil then
        self.${field.name} = false;
    end
    buf:WriteBool(self.${field.name})
        <#elseif field.classType="byte">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteRawByte(self.${field.name})
        <#elseif field.classType="short">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteShort(self.${field.name})
        <#elseif field.classType="int">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteInt(self.${field.name})
        <#elseif field.classType="float">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteFloat(self.${field.name})
        <#elseif field.classType="double">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteDouble(self.${field.name})
        <#elseif field.classType="long">
    if self.${field.name} == nil then
        self.${field.name} = 0;
    end
    buf:WriteLong(buf,self.${field.name})
        <#elseif field.classType="String">
    if self.${field.name} == nil then
        self.${field.name} = "";
    end
    buf:WriteString(self.${field.name})
        <#else >
    if self.${field.name} then
        buf:WriteByte(1)
        ${luaNamespace!""}${field.classType}.write(buf,self)
    else
        buf:WriteByte(0)
    end
        </#if>
    </#if>
    </#list>
end
</#if>

<#if bean.type =="NA"|| bean.type?ends_with("C")>
--${luaNamespace!""}${bean.name}读取字节缓存
function ${luaNamespace!""}${bean.name}.read(buf)
    local self = {}
    <#if bean.type?ends_with("C")>
    self.id = ${bean.id?c}
    </#if>
    <#list bean.fields as field>
        <#if field.hasExplain>
    --${field.explain}
        </#if>
        <#if field.list>
    local ${field.name}_len  =  buf:ReadShort()
    if ${field.name}_len > 0 then
        local ${field.name}_list = {}
        for i=1,${field.name}_len do
            <#if field.classType="boolean">
            ${field.name}_list[i] = buf:ReadBool()
            <#elseif field.classType="byte">
            ${field.name}_list[i] = buf:ReadRawByte()
            <#elseif field.classType="short">
            ${field.name}_list[i] = buf:ReadShort()
            <#elseif field.classType="int">
            ${field.name}_list[i] = buf:ReadInt()
            <#elseif field.classType="float">
            ${field.name}_list[i] = buf:ReadFloat()
            <#elseif field.classType="double">
            ${field.name}_list[i] = buf:ReadDouble()
            <#elseif field.classType="long">
            ${field.name}_list[i] = buf:ReadLong()
            <#elseif field.classType="String">
            ${field.name}_list[i] = buf:ReadString()
            <#else>
            local _${field.classType?uncap_first} =${luaNamespace!""}${field.classType}.read( ${field.name}_list[i] ,buf);
            ${field.name}_list[i] = _${field.classType?uncap_first}
            </#if>
        end
        self.${field.name} = ${field.name}_list
    end
        <#else ><#--不是list-->
            <#if field.classType="boolean">
    self.${field.name} = buf:ReadBool()
            <#elseif field.classType="byte">
    self.${field.name} = buf:ReadRawByte()
            <#elseif field.classType="short">
    self.${field.name} = buf:ReadShort()
            <#elseif field.classType="int">
    self.${field.name} = buf:ReadInt()
            <#elseif field.classType="float">
    self.${field.name} = buf:ReadFloat()
            <#elseif field.classType="double">
    self.${field.name} = buf:ReadDouble()
            <#elseif field.classType="long">
    self.${field.name} = buf:ReadLong()
            <#elseif field.classType="String">
    self.${field.name}= buf:ReadString()
            <#else>
    local _have${field.name}= buf:ReadByte()
    if _have${field.name} ==1 then
        local ${field.name} = ${luaNamespace!""}${field.classType}.read(buf);
        self.${field.name}=${field.name}
    end
            </#if>
        </#if>
    </#list>
    return self
end
</#if>

--${luaNamespace!""}${bean.name} 格式化字符串
function ${luaNamespace!""}${bean.name}.toString(self,_indent)
    _indent = _indent or ""
    local _str = ""
    _str = _str.."${bean.name}" .. "{"
<#list bean.fields as field>
    <#if field.hasExplain>
    --${field.explain}
    </#if>
    _str = _str.."\n"
    <#if field.list>
    _str = _str.._indent..rightPad("${field.name}", ${bean.fieldMaxLen}).. " = "
    if self.${field.name} then
        local ${field.name}_len = #self.${field.name}
        if ${field.name}_len > 0 then
            _str = _str.."["
            for i = 1,${field.name}_len do
                _str = _str.."\n"
        <#if field.baseField>
                _str = _str .. "<#list 1..bean.fieldMaxLen+3 as i> </#list>"
            <#if field.classType="boolean">
                _str = _str.._indent..tostring(self.${field.name}[i])
                <#else >
                _str = _str.._indent..self.${field.name}[i]
            </#if>
        <#else>
                _str = _str.."<#list 1..bean.fieldMaxLen+3 as i> </#list>"
                _str = _str.._indent..${luaNamespace!""}${field.classType}.toString(self.${field.name}[i],_indent .. "<#list 1..bean.fieldMaxLen+3 as i> </#list>")
        </#if>
            end
            _str = _str.."\n"
            _str = _str.."<#list 1..bean.fieldMaxLen+3 as i> </#list>"
            _str = _str.._indent.."]"
        else<#-- len = 0-->
            _str = _str.."nil "
        end
    else <#-- nil-->
        _str = _str.."nil "
    end
    <#else ><#-- 不是list-->
        <#if field.baseField>
            <#if field.classType="boolean">
    _str = _str.._indent..rightPad("${field.name}", ${bean.fieldMaxLen}).. " = "..tostring(self.${field.name})

            <#else >
    _str = _str.._indent..rightPad("${field.name}", ${bean.fieldMaxLen}).. " = "..self.${field.name}
            </#if>
         <#else>
    if self.${field.name} then
        _str = _str.._indent..rightPad("${field.name}", ${bean.fieldMaxLen}).. " = "..${luaNamespace!""}${field.classType}.toString(self.${field.name},_indent .. "<#list 1..bean.fieldMaxLen+3 as i> </#list>")
    else
        _str = _str.._indent..rightPad("${field.name}", ${bean.fieldMaxLen}).. " = ".."nil"
    end
        </#if>
    </#if>
</#list>
    _str =_str .."\n"
    _str = _str.._indent.."}"
    return _str
end

