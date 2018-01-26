package com.senpure.demo.service;

import com.senpure.demo.model.Notice;
import com.senpure.demo.criteria.NoticeCriteria;
import com.senpure.demo.mapper.NoticeMapper;
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
 * @version 2018-1-25 18:24:57
 */
@Service
@CacheConfig(cacheNames = "notice")
public class NoticeService extends BaseService {

    @Autowired
    private NoticeMapper noticeMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Notice find(Long id) {
        return noticeMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Notice findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Notice findSkipCache(Long id) {
        return noticeMapper.find(id);
    }

    public int count() {
        return noticeMapper.count();
    }

    public List<Notice> findAll() {
        return noticeMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = noticeMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(NoticeCriteria criteria) {
        return noticeMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Notice notice) {
        notice.setId(idGenerator.nextId());
        int result = noticeMapper.save(notice);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Notice> notices) {
        if (notices == null || notices.size() == 0) {
            return 0;
        }
        for (Notice notice : notices) {
            notice.setId(idGenerator.nextId());
        }
        return noticeMapper.saveBatch(notices);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(NoticeCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = noticeMapper.save(criteria.toNotice());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#notice.id")
    public boolean update(Notice notice) {
        int updateCount = noticeMapper.update(notice);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(notice.getClass() + ",[" + notice.getId() + "],版本号冲突,版本号[" + notice.getVersion() + "]");
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
    public int update(NoticeCriteria criteria) {
        int updateCount = noticeMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public ResultMap findPage(NoticeCriteria criteria) {
        ResultMap resultMap = ResultMap.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Notice notice = noticeMapper.find(criteria.getId());
            if (notice != null) {
                List<Notice> notices = new ArrayList<>(16);
                notices.add(notice);
                resultMap.putTotal(1);
                resultMap.putItems(notices);
            } else {
                resultMap.putTotal(0);
            }
            return resultMap;
        }
        int total = noticeMapper.countByCriteria(criteria);
        resultMap.putTotal(total);
        if (total == 0) {
            return resultMap;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Notice> notices = noticeMapper.findByCriteria(criteria);
        resultMap.putItems(notices);
        return resultMap;
    }

    public List<Notice> find(NoticeCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Notice> notices = new ArrayList<>(16);
            Notice notice = noticeMapper.find(criteria.getId());
            if (notice != null) {
                notices.add(notice);
            }
            return notices;
        }
        return noticeMapper.findByCriteria(criteria);
    }

    public Notice findOne(NoticeCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return noticeMapper.find(criteria.getId());
        }
        List<Notice> notices = noticeMapper.findByCriteria(criteria);
        if (notices.size() == 0) {
            return null;
        }
        return notices.get(0);
    }

}