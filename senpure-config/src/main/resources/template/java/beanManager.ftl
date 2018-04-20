package ${javaPack};

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ${name}Manager  {

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
        ${name?uncap_first}.set${value.field.name?cap_first}(${value.inJavaCodeValue});
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