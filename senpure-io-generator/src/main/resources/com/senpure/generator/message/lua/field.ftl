<#if bean.hasExplain>
--[[
    ${bean.explain}
--]]
</#if>
${bean.luaNamespace}${bean.name} = {
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
    --list:<#if field.baseField>${rightPad(field.classType,7)}<#else>${field.bean.luaNamespace}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    <#else ><#--不是list-->
    --[Comment]
    --类型:<#if field.baseField>${rightPad(field.classType,7)}<#else>${field.bean.luaNamespace}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
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

--${bean.luaNamespace}${bean.name}构造方法
function ${bean.luaNamespace}${bean.name}:new()
    local ${bean.luaNamespace}${bean.name} = setmetatable({}, {__index=self}) ;
<#list bean.fields as field>
    <#if field.list >
    --[Comment]
    --list:<#if field.baseField>${rightPad(field.classType,7)}<#else>${field.bean.luaNamespace}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    <#else ><#--不是list-->
    --[Comment]
    --类型:<#if field.baseField>${rightPad(field.classType,7)}<#else>${field.bean.luaNamespace}${field.classType}</#if><#if field.hasExplain>${field.explain}</#if>
    </#if>
    <#if field.list >
    ${bean.luaNamespace}${bean.name}.${field.name} = nil;
    <#else ><#--不是list-->
        <#if field.baseField>
            <#if field.classType == "String">
    ${bean.luaNamespace}${bean.name}.${field.name} = "";
            <#elseif field.classType == "boolean">
    ${bean.luaNamespace}${bean.name}.${field.name} = false;
            <#else >
    ${bean.luaNamespace}${bean.name}.${field.name} = 0;
            </#if>
        <#else>
    ${bean.luaNamespace}${bean.name}.${field.name}= nil ;<#--bean 引用-->
        </#if>
    </#if>
</#list>
    return ${bean.luaNamespace}${bean.name}
end

<#if bean.type =="NA"|| bean.type?ends_with("S")>
--${bean.name}写入字节缓存
function ${bean.luaNamespace}${bean.name}:write(buf)
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
                buf:WriteByte(self.${field.name}[i])
        <#elseif field.classType="short">
                buf:WriteShort(self.${field.name}[i])
        <#elseif field.classType="int">
                buf:WriteInt(self.${field.name}[i])
        <#elseif field.classType="float">
                buf:WriteFloat(self.${field.name}[i])
        <#elseif field.classType="double">
                buf:WriteDouble(self.${field.name}[i])
        <#elseif field.classType="long">
                buf:WriteLong(self.${field.name}[i])
        <#elseif field.classType="String">
                buf:WriteString(self.${field.name}[i])
        <#else >
                self.${field.name}[i]:write(buf)
        </#if>
            end
        end
    end
    <#else ><#-- 不是list -->
        <#if field.classType="boolean">
    buf:WriteBool(self.${field.name})
        <#elseif field.classType="byte">
    buf:WriteByte(self.${field.name})
        <#elseif field.classType="short">
    buf:WriteShort(self.${field.name})
        <#elseif field.classType="int">
    buf:WriteInt(self.${field.name})
        <#elseif field.classType="float">
    buf:WriteFloat(self.${field.name})
        <#elseif field.classType="double">
    buf:WriteDouble(self.${field.name})
        <#elseif field.classType="long">
    buf:WriteLong(buf,self.${field.name})
        <#elseif field.classType="String">
    buf:WriteString(self.${field.name})
        <#else >
    if self.${field.name} then
        buf:WriteByte(1)
        self.${field.name}:write(buf)
    else
        buf:WriteByte(0)
    end
        </#if>
    </#if>
    </#list>
end
</#if>

<#if bean.type =="NA"|| bean.type?ends_with("C")>
--${bean.luaNamespace}${bean.name}读取字节缓存
function ${bean.luaNamespace}${bean.name}:read(buf)
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
            ${field.name}_list[i] = buf:ReadByte()
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
            local _${field.classType?uncap_first} =${field.bean.luaNamespace}${field.classType}:new()
            _${field.classType?uncap_first}:read(buf)
            ${field.name}_list[i] = _${field.classType?uncap_first}
            </#if>
        end
        self.${field.name} = ${field.name}_list
    end
        <#else ><#--不是list-->
            <#if field.classType="boolean">
    self.${field.name} = buf:ReadBool()
            <#elseif field.classType="byte">
    self.${field.name} = buf:ReadByte()
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
        local ${field.name} = ${field.bean.luaNamespace}${field.classType}:new()
        ${field.name}:read(buf)
        self.${field.name}=${field.name}
    end
            </#if>
        </#if>
    </#list>
end
</#if>

--${bean.luaNamespace}${bean.name} 格式化字符串
function ${bean.luaNamespace}${bean.name}:toString(_indent)
    _indent = _indent or ""
    local _str = ""
    _str = _str.."${bean.luaNamespace}${bean.name}" .. "{"
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
    _str = _str.._indent..rightPad("${field.name}",self._filedPad).. " = "..self.${field.name}
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

