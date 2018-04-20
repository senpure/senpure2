--<#list fields as field>${field.name}<#if field.hasExplain>(${field.explain})</#if> <#if field_has_next>  </#if></#list>
${name} ={}
<#list records as record>
<#list record.values as value>
    <#if value_index==0 >
${name}[${value.inJavaCodeValue}] = {</#if></#list><#list record.values as value>${value.field.name}=${value.inJavaCodeValue}<#if value_has_next>,</#if></#list>};
</#list>
