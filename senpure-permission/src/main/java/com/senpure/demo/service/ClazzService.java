package com.senpure.demo.service;

import com.senpure.demo.model.Clazz;
import com.senpure.demo.criteria.ClazzCriteria;
import com.senpure.demo.mapper.ClazzMapper;
import com.senpure.base.exception.OptimisticLockingFailureException;
import com.senpure.base.service.BaseService;
import com.senpure.base.result.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

/**
 * @author senpure-generator
 * @version 2018-1-16 16:01:17
 */
@Service
@CacheConfig(cacheNames = "clazz")
public class ClazzService extends BaseService {

    @Autowired
    private ClazzMapper clazzMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Clazz find(Long id) {
        return clazzMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Clazz findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Clazz findSkipCache(Long id) {
        return clazzMapper.find(id);
    }

    public int count() {
        return clazzMapper.count();
    }

    public List<Clazz> findAll() {
        return clazzMapper.findAll();
    }

    @Transactional
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = clazzMapper.delete(id);
        return result == 1;
    }

    @Transactional
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(ClazzCriteria criteria) {
        return clazzMapper.deleteByCriteria(criteria);
    }

    @Transactional
    public boolean save(Clazz clazz) {
        clazz.setId(idGenerator.nextId());
        int result = clazzMapper.save(clazz);
        return result == 1;
    }

    @Transactional
    public int save(List<Clazz> clazzs) {
        if (clazzs == null || clazzs.size() == 0) {
            return 0;
        }
        for (Clazz clazz : clazzs) {
            clazz.setId(idGenerator.nextId());
        }
        return clazzMapper.saveBatch(clazzs);
    }

    @Transactional
    public boolean save(ClazzCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = clazzMapper.save(criteria.toClazz());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional
    @CacheEvict(key = "#clazz.id")
    public boolean update(Clazz clazz) {
        int updateCount = clazzMapper.update(clazz);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(clazz.getClass() + ",[" + clazz.getId() + "],版本号冲突,版本号[" + clazz.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int update(ClazzCriteria criteria) {
        int updateCount = clazzMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public ResultMap findPage(ClazzCriteria criteria) {
        ResultMap resultMap = ResultMap.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Clazz clazz = clazzMapper.find(criteria.getId());
            if (clazz != null) {
                List<Clazz> clazzs = new ArrayList<>(16);
                clazzs.add(clazz);
                resultMap.putTotal(1);
                resultMap.putItems(clazzs);
            } else {
                resultMap.putTotal(0);
            }
            return resultMap;
        }
        int total = clazzMapper.countByCriteria(criteria);
        resultMap.putTotal(total);
        if (total == 0) {
            return resultMap;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Clazz> clazzs = clazzMapper.findByCriteria(criteria);
        resultMap.putItems(clazzs);
        return resultMap;
    }

    public List<Clazz> find(ClazzCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Clazz> clazzs = new ArrayList<>(16);
            Clazz clazz = clazzMapper.find(criteria.getId());
            if (clazz != null) {
                clazzs.add(clazz);
            }
            return clazzs;
        }
        return clazzMapper.findByCriteria(criteria);
    }

    public Clazz findOne(ClazzCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return clazzMapper.find(criteria.getId());
        }
        List<Clazz> clazzs = clazzMapper.findByCriteria(criteria);
        if (clazzs.size() == 0) {
            return null;
        }
        return clazzs.get(0);
    }

}