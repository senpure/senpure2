xml 消息定义格式
<messages>
    package="com.senpure.server.test"  java包名    必须
    id="200"                           消息ID前缀   必须
    model="abc"                        消息模块名，同时为lua代码的消息命名空间 默认为MSG
</messages>

<bean> 实习对象节点
    name="Author"                       实体对象名 必须
    explain="作者"                      注释
</bean>

<message  > 消息节点
    id="101"                            消息id与父节点messages>的id 拼接为全局唯一id 必须
    type="CS"                           消息类型CS client to server 客服端到服务器 SC服务器到客服端 必须
    name="Student"                      消息对象名 必须
    explain="请求学生信息"              注释
</message>

<field > 字段节点
    type="String"                        字段类型Java 基础数据类型或定义的bean节点 必须
     name="name"                         字段名 必须
     explain="书本名"                    注释
</field>

 <list > 同<field>节点表示复数
 </list>