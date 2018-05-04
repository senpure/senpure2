package ${javaPack};

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ${name}${config.javaManagerSuffix}  {

    private static ConcurrentHashMap<${id.javaListType},${name}> configMap = new ConcurrentHashMap();
    private static List<${name}> configList = new ArrayList(16);


    static{
        refresh();
    }

    public static void  refresh(){
        configMap.clear();
        configList.clear();
        ${name} ${name?uncap_first} ;
    <#list records as record>
        ${name?uncap_first} = new ${name}();
        <#list record.values as value>
        <#if value.field.list>
            <#list value.inJavaCodeValues as v>
        ${name?uncap_first}.get${value.field.name?cap_first}().add(${v});
            </#list>
            <#else >
        ${name?uncap_first}.set${value.field.name?cap_first}(${value.inJavaCodeValues[0]});
        </#if>
        </#list>
        configMap.put(${name?uncap_first}.get${id.name?cap_first}(),${name?uncap_first});
        configList.add(${name?uncap_first});
    <#if record_has_next>

    </#if>
    </#list>
    }

    public static ${name}  get${name}(${id.javaListType} ${id.name}){
        return  configMap.get(${id.name});
    }

    public static List<${name}> get${name}List(){
        return configList;
    }

}