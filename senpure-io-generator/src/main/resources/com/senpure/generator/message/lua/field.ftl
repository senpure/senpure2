<#if bean.hasExplain>
--[[
    ${bean.explain}
--]]
</#if>
${luaNamespace!""}${bean.name} = {
<#if bean.type !="NA">
    --message_id
    _id = ${bean.id?c};
    _message_name = "${luaNameStyle(bean.name)?lower_case?substring(1)}";
</#if>
<#list bean.fields as field>
    <#if field.list >
    --list:<#if field.baseField>${rightPad(field.classType,7)}<#else>${luaNamespace!""}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    <#else ><#--不是list-->
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
    --缩进${bean.fieldMaxLen} + 3 = ${bean.fieldMaxLen+3} 个空格
    _next_indent = "<#list 1..bean.fieldMaxLen+3 as i> </#list>";
</#if>
    --格式化时统一字段长度
    _filedPad = ${bean.fieldMaxLen} ;
}
--${luaNamespace!""}${bean.name}构造方法
function ${luaNamespace!""}${bean.name}:new()
    local o = {}
    self.__index = self
    setmetatable(o, self)
    return o
end

<#if bean.type =="NA"|| bean.type?ends_with("S")>
--${bean.name}写入字节缓存
function ${luaNamespace!""}${bean.name}:write(buf)
    <#list bean.fields as field>
        <#if field.hasExplain>
    --${field.explain}
        </#if>
    <#if field.list>
    if self.${field.name} then
        local ${field.name}_len = #self.${field.name}
        self:writeUShort(${field.name}_len)
        if ${field.name}_len > 0 then
            for i = 1, ${field.name}_len do
        <#if field.classType="boolean">
                buf:writeBool(self.${field.name}[i])
        <#elseif field.classType="byte">
                buf:writeRawByte(self.${field.name}[i])
        <#elseif field.classType="short">
                buf:writeShort(self.${field.name}[i])
        <#elseif field.classType="int">
                buf:writeInt(self.${field.name}[i])
        <#elseif field.classType="float">
                buf:writeFloat(self.${field.name}[i])
        <#elseif field.classType="double">
                buf:writeDouble(self.${field.name}[i])
        <#elseif field.classType="long">
                buf:writeLong(buf,self.${field.name}[i])
        <#elseif field.classType="String">
                local _str = string.pack(buf:_getLC("A"), self.${field.name}[i])
                buf:writeUInt(#_str)
                buf:writeBuf(_str)
        <#else >
                self.${field.name}[i]:write(buf)
        </#if>
            end
        end
    end
    <#else ><#-- 不是list -->
        <#if field.classType="boolean">
    buf:writeBool(self.${field.name})
        <#elseif field.classType="byte">
    buf:writeRawByte(self.${field.name})
        <#elseif field.classType="short">
    buf:writeShort(self.${field.name})
        <#elseif field.classType="int">
    buf:writeInt(self.${field.name})
        <#elseif field.classType="float">
    buf:writeFloat(self.${field.name})
        <#elseif field.classType="double">
    buf:writeDouble(self.${field.name})
        <#elseif field.classType="long">
    buf:writeLong(buf,self.${field.name})
        <#elseif field.classType="String">
    local ${field.name} = string.pack(buf:_getLC("A"), self.${field.name})
    buf:writeUInt(#${field.name})
    buf:writeBuf(${field.name})
        <#else >
    if self.${field.name} then
        buf:writeByte(1)
        self.${field.name}:write(buf)
    else
        buf:writeByte(0)
    end
        </#if>
    </#if>
    </#list>
end
</#if>

<#if bean.type =="NA"|| bean.type?ends_with("C")>
--${luaNamespace!""}${bean.name}读取字节缓存
function ${luaNamespace!""}${bean.name}:read(buf)
    <#list bean.fields as field>
        <#if field.hasExplain>
    --${field.explain}
        </#if>
        <#if field.list>
    local ${field.name}_len  =  buf:readUShort()
    if ${field.name}_len > 0 then
        local ${field.name}_list={}
        for i=1,${field.name}_len do
            <#if field.classType="boolean">
            ${field.name}_list[i] = buf:readBool()
            <#elseif field.classType="byte">
            ${field.name}_list[i] = buf:readRawByte()
            <#elseif field.classType="short">
            ${field.name}_list[i] = buf:readShort()
            <#elseif field.classType="int">
            ${field.name}_list[i] = buf:readInt()
            <#elseif field.classType="float">
            ${field.name}_list[i] = buf:readFloat()
            <#elseif field.classType="double">
            ${field.name}_list[i] = buf:readDouble()
            <#elseif field.classType="long">
            ${field.name}_list[i] = buf:readLong()
            <#elseif field.classType="String">
            local ${field.name}_str_len = buf:readUInt()
            if ${field.name}_str_len  > 0 then
                ${field.name}_list[i] = buf:readStringBytes(${field.name}_str_len)
            else
                ${field.name}_list[i]  = ""
            end
            <#else>
            local _${field.classType?uncap_first} =${luaNamespace!""}${field.classType}:new()
            _${field.classType?uncap_first}:read(buf)
            ${field.name}_list[i] = _${field.classType?uncap_first}
            </#if>
        end
        self.${field.name} = ${field.name}_list
    end
        <#else ><#--不是list-->
            <#if field.classType="boolean">
    self.${field.name} = buf:readBool()
            <#elseif field.classType="byte">
    self.${field.name} = buf:readRawByte()
            <#elseif field.classType="short">
    self.${field.name} = buf:readShort()
            <#elseif field.classType="int">
    self.${field.name} = buf:readInt()
            <#elseif field.classType="float">
    self.${field.name} = buf:readFloat()
            <#elseif field.classType="double">
    self.${field.name} = buf:readDouble()
            <#elseif field.classType="long">
    self.${field.name} = buf:readLong()
            <#elseif field.classType="String">
    local ${field.name}_len = buf:readUInt()
    if ${field.name}_len > 0 then
        self.${field.name}= buf:readStringBytes(${field.name}_len)
    else
        self.${field.name}= ""
    end
            <#else>
    local _have${field.name}= buf:readByte()
    if _have${field.name} ==1 then
        local ${field.name} = ${luaNamespace!""}${field.classType}:new()
        ${field.name}:read(buf)
        self.${field.name}=${field.name}
    end
            </#if>
        </#if>
    </#list>
end
</#if>

--${luaNamespace!""}${bean.name} 格式化字符串
function ${luaNamespace!""}${bean.name}:toString(_indent)
    _indent = _indent or ""
    local _str = ""
    _str = _str.."${bean.name}" .. "{"
<#list bean.fields as field>
    <#if field.hasExplain>
    --${field.explain}
    </#if>
    _str = _str.."\n"
    <#if field.list>
    _str = _str.._indent..rightPad("${field.name}", self._filedPad).. " = "
    if self.${field.name} then
        local ${field.name}_len = #self.${field.name}
        if ${field.name}_len > 0 then
            _str = _str.."["
            for i = 1,${field.name}_len do
                _str = _str.."\n"
        <#if field.baseField>
                _str = _str .. self._next_indent
            <#if field.classType="boolean">
                _str = _str.._indent..tostring(self.${field.name}[i])
                <#else >
                _str = _str.._indent..self.${field.name}[i]
            </#if>
        <#else>
                _str = _str..self._next_indent
                _str = _str.._indent..self.${field.name}[i]:toString(_indent .. self._next_indent)
        </#if>
            end
            _str = _str.."\n"
            _str = _str..self._next_indent
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
    _str = _str.._indent..rightPad("${field.name}", self._filedPad).. " = "..tostring(self.${field.name})

            <#else >
    _str = _str.._indent..rightPad("${field.name}", self._filedPad).. " = "..self.${field.name}
            </#if>
         <#else>
    if self.${field.name} then
        _str = _str.._indent..rightPad("${field.name}", self._filedPad).. " = "..self.${field.name}:toString(_indent .. self._next_indent)
    else
        _str = _str.._indent..rightPad("${field.name}", self._filedPad).. " = ".."nil"
    end
        </#if>
    </#if>
</#list>
    _str =_str .."\n"
    _str = _str.._indent.."}"
    return _str
end

