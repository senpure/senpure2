package ${pack}.message;

<#list singleField?values as field>
 <#if !field.baseField>
 <#if field.otherPart>
import ${field.originalClassType};
 <#else >
import ${pack}.bean.${field.classType};
 </#if>
 </#if>
</#list >
import com.lhxy.game.core.annotation.Msg;
import com.lhxy.game.message.core.<#if type="CS" >ReqMessage<#else >ResMessage</#if>;
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
@Msg(${id?c})
public class ${name} implements <#if type="CS" >ReqMessage<#else >ResMessage</#if> {
<#include "googleField.ftl">

    @Override
    public int getId() {
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
<#include "googleToString.ftl">
}