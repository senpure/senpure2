package ${javaPack};

<#list singleField?values as field>
    <#if !field.baseField>
        <#if field.bean.javaPack!=javaPack>
import ${field.bean.javaPack}.${field.classType};
        </#if>
    </#if>
</#list >
import com.senpure.io.message.Message;
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
<#assign name>${type}${name}Message</#assign>
public class ${name} extends  Message {
<#include "field.ftl">

    @Override
    public int getMessageId() {
        return ${id?c};
    }

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