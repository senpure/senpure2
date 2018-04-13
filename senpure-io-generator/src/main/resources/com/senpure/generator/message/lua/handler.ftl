
<#list messages as message>
<#if message.type="SC">
${message.luaNamespace}${message.name}Handler = {
--${message.id?c}
id=${message.luaNamespace}${message.name}.id
}
function ${message.luaNamespace}${message.name}Handler:emptyMessage()
    return ${message.luaNamespace}${message.name}:new()
end
--                              ${message.id?c}
MessageParser.regMessageParser( ${message.id?c}, ${message.luaNamespace}${message.name},"${message.luaNamespace}${message.name}")
--                              ${message.id?c}
GameNet.RegisterHandler(${message.luaNamespace}${message.name}Handler.id, impl_receive_${model}.${luaImplPrefix}${message.name})
</#if>
</#list>
