
<#list messages as message>
<#if message.type="SC">
${message.name}Handler = {
--${message.id?c}
id=${luaNamespace!""}${message.name}.id
}
function ${message.name}Handler:emptyMessage()
    return ${luaNamespace!""}${message.name}:new()
end
--                              ${message.id?c}
MessageParser:regMessageParser(${message.name}Handler.id, ${message.name}Handler,"${luaNamespace!""}${message.name}")
--                              ${message.id?c}
GameNet.RegisterHandler(${message.name}Handler.id, impl_receive_${model}.${message.name})
</#if>
</#list>
