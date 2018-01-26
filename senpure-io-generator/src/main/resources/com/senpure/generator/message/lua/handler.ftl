
<#list messages as message>
<#if message.type="SC">
${message.name}Handler = {
--${message.id?c}
_id=${luaNamespace!""}${message.name}._id
}
function ${message.name}Handler:emptyMessage()
    return ${luaNamespace!""}${message.name}:new()
end
--                              ${message.id?c}
MessageParser:regMessageParser(${message.name}Handler._id, ${message.name}Handler,"${luaNamespace!""}${message.name}")

</#if>
</#list>
