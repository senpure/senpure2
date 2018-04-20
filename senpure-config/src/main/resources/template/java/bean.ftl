package ${javaPack};

/**<#if hasExplain>
 * ${explain}
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
public class ${name}  {

<#list fields as field>
    <#if field.hasExplain>
    //${field.explain}
    </#if>
    private ${field.javaType} ${field.name};
</#list>

<#list fields as field>
    <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * <#if field.javaType="boolean"> is<#else>get</#if> ${field.explain}
     * @return
     */
    </#if>
    public  ${field.javaType} <#if field.javaType="boolean"> is<#else>get</#if>${field.name?cap_first}() {
        return ${field.name};
    }

    <#if field.hasExplain&&field.explain?length gt 2>
    /**
     * set ${field.explain}
     */
    </#if>
    public ${name} set${field.name?cap_first}(${field.javaType} ${field.name}) {
        this.${field.name}=${field.name};
    return this;
    }

</#list>
}