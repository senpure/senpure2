--[[
Model:${model}
<#list beans as bean>
<#if bean_index==0>

</#if>
Bean    :${luaNamespace!""}${rightPad(bean.name,nameMaxLen)}  ${bean.explain}
</#list>
<#list messages as bean>
    <#if bean_index==0>

    </#if>
Message :${luaNamespace!""}${rightPad(bean.name,nameMaxLen)}   ${rightPad(bean.id?c,7)}  ${bean.explain}
</#list>
<#list messages as bean>
    <#if bean_index==0>

    </#if>
<#if bean.type="SC">
GameNet :impl_receive_${model}.${bean.name}
</#if>
</#list>

author senpure-generator
version ${.now?datetime}
--]]
<#--
<#if luaNamespace??>
    ${luaNamespace?substring(0,luaNamespace?length-1)} =  ${luaNamespace?substring(0,luaNamespace?length-1)} or {}
</#if>
-->
function rightPad(_str, _pad)
    local _str_len = #_str
    for i = 1, _pad do
        if i > _str_len then
            _str = _str .. " "
        end
    end
    return _str
end
<#list beans as bean>
<#include "field.ftl">
</#list>

<#list messages as bean>
    <#include "field.ftl">
</#list>