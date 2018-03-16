
<#list messages as message>
<#if message.type="SC">
${luaNamespace!""}${message.name}Handler = {
--${message.id?c}
id=${luaNamespace!""}${message.name}.id
}
function ${luaNamespace!""}${message.name}Handler:emptyMessage()
    return ${luaNamespace!""}${message.name}:new()
end
--                              ${message.id?c}
MessageParser.regMessageParser( ${message.id?c}, ${luaNamespace!""}${message.name},"${luaNamespace!""}${message.name}")
--                              ${message.id?c}
GameNet.RegisterHandler(${luaNamespace!""}${message.name}Handler.id, impl_receive_${model}.${message.name})
</#if>
</#list>
