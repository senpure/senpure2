<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${mapperPackage}.${name}Mapper">
    <resultMap id="${name?uncap_first}ResultMap" type="${modelPackage}.${name}">
        <id column="${id.column}" property="${id.name}" jdbcType="${id.jdbcType}"/><#if id.hasExplain>${space(id.xmlLen,xmlMaxLen+4)}<!-- ${id.explain} --></#if>
    <#if version??>
        <result column="${version.column}" property="${version.name}" jdbcType="${version.jdbcType}"/><#if version.hasExplain>${space(version.xmlLen,xmlMaxLen)}<!-- ${version.explain} --></#if>
    </#if>
    <#list modelFieldMap?values as field>
        <result column="${field.column}" property="${field.name}" jdbcType="${field.jdbcType}"/><#if field.hasExplain>${space(field.xmlLen,xmlMaxLen)}<!-- ${field.explain} --></#if>
    </#list>
    </resultMap>
    <sql id="${name?uncap_first}AllColumns">
        ${id.column}<#if version??>,${version.column}</#if><#list modelFieldMap?values as field>,${field.column}</#list>
    </sql>

    <!--主键查找-->
    <select id="find" resultMap="${name?uncap_first}ResultMap" parameterType="${id.clazzType}">
        select <include refid="${name?uncap_first}AllColumns"/> from ${tableName} where ${id.column} = ${r'#{'}${id.name}}
    </select>

    <!--主键删除-->
    <delete id="delete" parameterType="${id.clazzType}">
        delete from ${tableName} where ${id.column} = ${r'#{'}${id.name}}
    </delete>

    <!--按条件删除(不取主键的值进行对比，即主键无效化)-->
    <delete id="deleteByCriteria" parameterType="${criteriaPackage}.${name}Criteria">
        delete from ${tableName}
        <where>
        <#list criteriaFieldMap?values as field>
            <#if field.criteriaEquals >
                <#if field.javaNullable >
            <if test="${field.name} != null">
                and ${field.column} = ${r'#{'}${field.name}}
            </if>
                <#else >
                and ${field.column} = ${r'#{'}${field.name}}
                </#if>
            <#elseif dateField.name==field.name><#--时间比较-->
            <if test="sdate != null">
                and ${field.column} >= <#if field.clazzType =='Date'>${r'#{'}sdate}<#else>${r'#{'}sdate.time}</#if>
            </if>
            <if test="edate != null">
                and ${field.column} &lt;= <#if field.clazzType =='Date'>${r'#{'}edate}<#else>${r'#{'}edate.time}</#if>
            </if>
            </#if>
        </#list>
        </where>
    </delete>

    <!-- 取对象的值，直接插入数据库(包括空值)<#if version??>,${version.name}字段(版本控制)，被初始化为1</#if>-->
    <insert id="save" parameterType="${modelPackage}.${name}" <#if id.databaseId>useGeneratedKeys="true" keyProperty="${id.name}"</#if>>
        insert into ${tableName} (<include refid="${name?uncap_first}AllColumns"/>)
        values (${r'#{'}${id.name}}<#if version??>,1</#if><#list modelFieldMap?values as field>,${r'#{'}${field.name}}</#list>)
    </insert>

    <!-- 取对象的值，直接插入数据库(包括空值)<#if version??>,${version.name}字段(版本控制)，被初始化为1</#if>-->
    <insert id="saveBatch" parameterType="${modelPackage}.${name}" >
        insert into ${tableName} (<include refid="${name?uncap_first}AllColumns"/>)
        values
        <foreach collection="list" item="item" index="index" separator="," >
            (${r'#{item.'}${id.name}}<#if version??>,1</#if><#list modelFieldMap?values as field>,${r'#{item.'}${field.name}}</#list>)
        </foreach>
    </insert>

    <!--会进行对象的空值判断，不为空才更新，以主键进行where判断<#if version??>,${version.name}字段(版本控制)，必须有有效值</#if>-->
    <update id="update" parameterType="${modelPackage}.${name}">
        update ${tableName}
        <set>
        <#if version??>
                <bind name="${version.name}Update" value="${version.name} + 1"/>
                ${version.column} = ${r'#{'}${version.name}Update},</#if>
        <#list modelFieldMap?values as field>
            <#if field.javaNullable >
            <if test="${field.name} != null">
                ${field.column} = ${r'#{'}${field.name}},
            </if>
            <#else >
                ${field.column} = ${r'#{'}${field.name}},
            </#if>
        </#list>
        </set>
        where ${id.column} = ${r'#{'}${id.name}} <#if version??> and ${version.column} =  ${r'#{'}${version.name}}</#if>
    </update>

    <!-- 直接将值覆盖到数据库，不会做为空判断，以主键进行where判断<#if version??>,${version.name}字段(版本控制)，必须有有效值</#if>-->
    <update id="cover" parameterType="${modelPackage}.${name}">
        update ${tableName}
        <set>
        <#if version??>
            <bind name="${version.name}Update" value="${version.name} + 1"/>
            ${version.column} = ${r'#{'}${version.name}Update},</#if>
        <#list modelFieldMap?values as field>
            ${field.column} = ${r'#{'}${field.name}},
        </#list>
        </set>
        where ${id.column} = ${r'#{'}${id.name}} <#if version??> and ${version.column} =  ${r'#{'}${version.name}}</#if>
    </update>

    <!--会进行对象的空值判断，不为空才更新，主键无值时，可以进行批量更新-->
    <update id="updateByCriteria" parameterType="${criteriaPackage}.${name}Criteria">
        update ${tableName}
        <set>
        <#if version??>
            <#if version.javaNullable >
            <choose>
                <when test="${version.name} != null">
                    <bind name="${version.name}Update" value="${version.name} + 1"/>
                    ${version.column} = ${r'#{'}${version.name}Update},
                </when>
                <otherwise>
                    ${version.column} = ${version.name} + 1,
                </otherwise>
            </choose>
            <#else>
                    <bind name="${version.name}Update" value="${version.name} + 1"/>
                    ${version.column} = ${r'#{'}${version.name}Update},
            </#if>
        </#if>
        <#list modelFieldMap?values as field>
                <#if field.javaNullable >
                <if test="${field.name} != null">
                    ${field.column} = ${r'#{'}${field.name}},
                </if>
                <#else>
                    ${field.column} = ${r'#{'}${field.name}},
                </#if>
        </#list>
        </set>
        <where>
        <#if id.javaNullable>
                <if test="${id.name} != null">
                    ${id.column} = ${r'#{'}${id.name}}
                 </if>
        <#else >
                    ${id.column} = ${r'#{'}${id.name}}
        </#if>
        <#if dateFieldNum ==1>
                <if test="sdate != null">
                    and ${dateField.column} >= <#if dateField.clazzType =='Date'>${r'#{'}sdate}<#else>${r'#{'}sdate.time}</#if>
                </if>
                <if test="edate != null">
                    and ${dateField.column} &lt;= <#if dateField.clazzType =='Date'>${r'#{'}edate}<#else>${r'#{'}edate.time}</#if>
                </if>
        </#if>
        <#if version??>
            <#if version.javaNullable >
                <if test="${version.name} != null">
                    and ${version.column} =  ${r'#{'}${version.name}}
                </if>
            <#else >
                    and ${version.column} =  ${r'#{'}${version.name}}
            </#if>
        </#if>
        </where>
     </update>

    <select id="count" resultType="int">
        select count(*) from ${tableName}
    </select>

    <select id="findAll" resultMap="${name?uncap_first}ResultMap" parameterType="${id.clazzType}">
        select <include refid="${name?uncap_first}AllColumns"/> from ${tableName}
    </select>

    <!--主键会无效化,不会进行条件对比-->
    <select id="countByCriteria" resultType="int" parameterType="${criteriaPackage}.${name}Criteria">
        select count(*) from ${tableName}
        <where>
        <#list criteriaFieldMap?values as field>
            <#if field.criteriaEquals >
                <#if field.javaNullable >
            <if test="${field.name} != null">
                and ${field.column} = ${r'#{'}${field.name}}
            </if>
                <#else>
                and ${field.column} = ${r'#{'}${field.name}}
                </#if>
            <#elseif dateField.name==field.name><#--时间比较-->
            <if test="sdate != null">
               and ${field.column} >= <#if field.clazzType =='Date'>${r'#{'}sdate}<#else>${r'#{'}sdate.time}</#if>
            </if>
            <if test="edate != null">
                 and ${field.column} &lt;= <#if field.clazzType =='Date'>${r'#{'}edate}<#else>${r'#{'}edate.time}</#if>
            </if>
            </#if>
        </#list>
        </where>
    </select>

    <!--主键会无效化,不会进行条件对比-->
    <select id="findByCriteria" parameterType="${criteriaPackage}.${name}Criteria" resultMap="${name?uncap_first}ResultMap">
        select <include refid="${name?uncap_first}AllColumns"/>
        from ${tableName}
        <where>
        <#list criteriaFieldMap?values as field>
            <#if field.criteriaEquals >
                <#if field.javaNullable >
            <if test="${field.name} != null">
                and ${field.column} = ${r'#{'}${field.name}}
            </if>
                <#else >
                and ${field.column} = ${r'#{'}${field.name}}
                </#if>
            <#elseif dateField.name==field.name><#--时间比较-->
            <if test="sdate != null">
                and ${field.column} >= <#if field.clazzType =='Date'>${r'#{'}sdate}<#else>${r'#{'}sdate.time}</#if>
            </if>
            <if test="edate != null">
                and ${field.column} &lt;= <#if field.clazzType =='Date'>${r'#{'}edate}<#else>${r'#{'}edate.time}</#if>
            </if>
            </#if>
        </#list>
        </where>
        <if test="hasOrder">
            ORDER BY
            <foreach collection="order" index="key" item="item" separator=",">
                <if test="item == 'DESC'">
                    ${r'${'}key} DESC
                </if>
                <if test="item == 'ASC'">
                    ${r'${'}key} ASC
                </if>
            </foreach>
        </if>
        <if test="usePage">
            <choose>
                <when test="firstResult == 0">
                    limit ${r'#{'}maxResults}
                </when>
                <otherwise>
                    limit ${r'#{'}firstResult}, ${r'#{'}maxResults}
                </otherwise>
            </choose>
        </if>
    </select>
</mapper>
