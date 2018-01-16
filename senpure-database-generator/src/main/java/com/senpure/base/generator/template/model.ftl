package ${modelPackage};

import java.io.Serializable;
<#if hasDate>
import java.util.Date;
</#if>

/**<#if hasExplain>
 * ${explain}
 * </#if>
 * @author senpure-generator
 * @version ${.now?datetime}
 */
public class ${name} implements Serializable {
    private static final long serialVersionUID = ${serial(modelFieldMap)}L;

<#if id.hasExplain>
    //${id.explain}
</#if>
    ${id.accessType} ${id.clazzType} ${id.name};
<#if version??>
    <#if id.hasExplain>
    //${version.explain}
    </#if>
    ${version.accessType} ${version.clazzType} ${version.name};
</#if>
<#list modelFieldMap?values as field>
<#if field.hasExplain>
    //${field.explain}
</#if>
    ${field.accessType} ${field.clazzType} ${field.name};
</#list>

<#assign field = id />
<#include "getset.ftl">

<#list modelFieldMap?values as field>
    <#include "getset.ftl">
    <#if field_has_next >

    </#if>
</#list>

<#if version??>
    <#assign field = version />
    <#include "getset.ftl">
</#if>

    @Override
    public String toString() {
        return "${name}{"
                + "${id.name}=" + ${id.name}
<#if version??>
                + ",${version.name}=" + ${version.name}
</#if>
<#list modelFieldMap?values as field>
                + ",${field.name}=" + ${field.name}
</#list>
                + "}";
    }

}