package com.senpure.demo.service;

import com.senpure.demo.model.Proxy;
import com.senpure.demo.criteria.ProxyCriteria;
import com.senpure.demo.mapper.ProxyMapper;
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
 * @version 2018-3-22 20:18:02
 */
@Service
@CacheConfig(cacheNames = "proxy")
public class ProxyService extends BaseService {

    @Autowired
    private ProxyMapper proxyMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Proxy find(Long id) {
        return proxyMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Proxy findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Proxy findSkipCache(Long id) {
        return proxyMapper.find(id);
    }

    public int count() {
        return proxyMapper.count();
    }

    public List<Proxy> findAll() {
        return proxyMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = proxyMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(ProxyCriteria criteria) {
        return proxyMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Proxy proxy) {
        proxy.setId(idGenerator.nextId());
        int result = proxyMapper.save(proxy);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Proxy> proxies) {
        if (proxies == null || proxies.size() == 0) {
            return 0;
        }
        for (Proxy proxy : proxies) {
            proxy.setId(idGenerator.nextId());
        }
        return proxyMapper.saveBatch(proxies);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(ProxyCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = proxyMapper.save(criteria.toProxy());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#proxy.id")
    public boolean update(Proxy proxy) {
        int updateCount = proxyMapper.update(proxy);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(proxy.getClass() + ",[" + proxy.getId() + "],版本号冲突,版本号[" + proxy.getVersion() + "]");
        }
        return true;
    }

    /**
     * 当版本号，和主键不为空时，更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int update(ProxyCriteria criteria) {
        int updateCount = proxyMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public ResultMap findPage(ProxyCriteria criteria) {
        ResultMap resultMap = ResultMap.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Proxy proxy = proxyMapper.find(criteria.getId());
            if (proxy != null) {
                List<Proxy> proxies = new ArrayList<>(16);
                proxies.add(proxy);
                resultMap.putTotal(1);
                resultMap.putItems(proxies);
            } else {
                resultMap.putTotal(0);
            }
            return resultMap;
        }
        int total = proxyMapper.countByCriteria(criteria);
        resultMap.putTotal(total);
        if (total == 0) {
            return resultMap;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Proxy> proxies = proxyMapper.findByCriteria(criteria);
        resultMap.putItems(proxies);
        return resultMap;
    }

    public List<Proxy> find(ProxyCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Proxy> proxies = new ArrayList<>(16);
            Proxy proxy = proxyMapper.find(criteria.getId());
            if (proxy != null) {
                proxies.add(proxy);
            }
            return proxies;
        }
        return proxyMapper.findByCriteria(criteria);
    }

    public Proxy findOne(ProxyCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return proxyMapper.find(criteria.getId());
        }
        List<Proxy> proxies = proxyMapper.findByCriteria(criteria);
        if (proxies.size() == 0) {
            return null;
        }
        return proxies.get(0);
    }

}