package com.senpure.demo.mapper;

import com.senpure.demo.model.Clazz;
import com.senpure.demo.criteria.ClazzCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author senpure-generator
 * @version 2018-3-22 20:18:02
 */
@Mapper
public interface ClazzMapper {

    Clazz find(Long id);

    /**
     * 根据主键删除
     *
     * @return 影响的行数
     */
    int delete(Long id);

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 影响的行数
     */
    int deleteByCriteria(ClazzCriteria criteria);

    /**
     * 取对象的值，直接插入数据库(包括空值)
     * version字段(版本控制)，被初始化为1
     *
     * @return 影响的行数
     */
    int save(Clazz clazz);

    /**
     * 取对象的值，直接插入数据库(包括空值)
     * version字段(版本控制)，被初始化为1
     *
     * @return 影响的行数
     */
    int saveBatch(List<Clazz> clazzs);

    /**
     * 会进行对象的空值判断，不为空才更新，以主键进行where判断
     * version字段(版本控制)，必须有有效值
     *
     * @return 影响的行数
     */
    int update(Clazz clazz);

    /**
     * 直接将值覆盖到数据库，不会做为空判断，以主键进行where判断
     * version字段(版本控制)，必须有有效值
     *
     * @return 影响的行数
     */
    int cover(Clazz clazz);

    /**
     * 会进行对象的空值判断，不为空才更新，主键无值时，可以进行批量更新
     *
     * @return 影响的行数
     */
    int updateByCriteria(ClazzCriteria criteria);

    int count();

    List<Clazz> findAll();

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 满足条件的总行数
     */
    int countByCriteria(ClazzCriteria criteria);

    /**
     * <b>主键会无效化,不会进行条件对比</b>
     *
     * @return 满足条件的记录
     */
    List<Clazz> findByCriteria(ClazzCriteria criteria);
}
