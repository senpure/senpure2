package ${pack}.bean;

<#list singleField?values as field>
    <#if !field.baseField>
        <#if field.otherPart>
import ${field.originalClassType};
        </#if>
    </#if>
</#list >
import com.senpure.io.message.Bean;
import io.netty.buffer.ByteBuf;

<#list fields as field>
    <#if field.list>
import java.util.List;
import java.util.ArrayList;

        <#break>
    </#if>
</#list>
/**<#if hasExplain>
* ${explain}
* </#if>
* @author senpure-generator
* @version ${.now?datetime}
*/
public class ${name} extends  Bean {
<#include "field.ftl">

    @Override
    public String toString() {
        return "${name}{"
<#list fields as field>
                +"<#if field_index gt 0>,</#if>${field.name}=" + ${field.name}
</#list>
                + "}";
   }
<#include "toString.ftl">
}