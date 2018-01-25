package com.senpure.demo.service;

import com.senpure.demo.model.Student;
import com.senpure.demo.criteria.StudentCriteria;
import com.senpure.demo.mapper.StudentMapper;
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
@CacheConfig(cacheNames = "student")
public class StudentService extends BaseService {

    @Autowired
    private StudentMapper studentMapper;

    @CacheEvict(key = "#id")
    public void clearCache(Long id) {
    }

    @CacheEvict(allEntries = true)
    public void clearCache() {
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Student find(Long id) {
        return studentMapper.find(id);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    public Student findOnlyCache(Long id) {
        return null;
    }

    @CachePut(key = "#id", unless = "#result == null")
    public Student findSkipCache(Long id) {
        return studentMapper.find(id);
    }

    public int count() {
        return studentMapper.count();
    }

    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#id")
    public boolean delete(Long id) {
        int result = studentMapper.delete(id);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#criteria.id", allEntries = true)
    public int delete(StudentCriteria criteria) {
        return studentMapper.deleteByCriteria(criteria);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(Student student) {
        student.setId(idGenerator.nextId());
        int result = studentMapper.save(student);
        return result == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public int save(List<Student> students) {
        if (students == null || students.size() == 0) {
            return 0;
        }
        for (Student student : students) {
            student.setId(idGenerator.nextId());
        }
        return studentMapper.saveBatch(students);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean save(StudentCriteria criteria) {
        criteria.setId(idGenerator.nextId());
        int result = studentMapper.save(criteria.toStudent());
        return result == 1;
    }

    /**
     * 更新失败会抛出OptimisticLockingFailureException
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(key = "#student.id")
    public boolean update(Student student) {
        int updateCount = studentMapper.update(student);
        if (updateCount == 0) {
            throw new OptimisticLockingFailureException(student.getClass() + ",[" + student.getId() + "],版本号冲突,版本号[" + student.getVersion() + "]");
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
    public int update(StudentCriteria criteria) {
        int updateCount = studentMapper.updateByCriteria(criteria);
        if (updateCount == 0 && criteria.getVersion() != null
                && criteria.getId() != null) {
            throw new OptimisticLockingFailureException(criteria.getClass() + ",[" + criteria.getId() + "],版本号冲突,版本号[" + criteria.getVersion() + "]");
        }
        return updateCount;
    }

    @Transactional(readOnly = true)
    public ResultMap findPage(StudentCriteria criteria) {
        ResultMap resultMap = ResultMap.success();
        //是否是主键查找
        if (criteria.getId() != null) {
            Student student = studentMapper.find(criteria.getId());
            if (student != null) {
                List<Student> students = new ArrayList<>(16);
                students.add(student);
                resultMap.putTotal(1);
                resultMap.putItems(students);
            } else {
                resultMap.putTotal(0);
            }
            return resultMap;
        }
        int total = studentMapper.countByCriteria(criteria);
        resultMap.putTotal(total);
        if (total == 0) {
            return resultMap;
        }
        //检查页数是否合法
        checkPage(criteria, total);
        List<Student> students = studentMapper.findByCriteria(criteria);
        resultMap.putItems(students);
        return resultMap;
    }

    public List<Student> find(StudentCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            List<Student> students = new ArrayList<>(16);
            Student student = studentMapper.find(criteria.getId());
            if (student != null) {
                students.add(student);
            }
            return students;
        }
        return studentMapper.findByCriteria(criteria);
    }

    public Student findOne(StudentCriteria criteria) {
        //是否是主键查找
        if (criteria.getId() != null) {
            return studentMapper.find(criteria.getId());
        }
        List<Student> students = studentMapper.findByCriteria(criteria);
        if (students.size() == 0) {
            return null;
        }
        return students.get(0);
    }

    public List<Student> findByClazzId(Long clazzId) {
        StudentCriteria criteria = new StudentCriteria();
        criteria.setUsePage(false);
        criteria.setClazzId(clazzId);
        List<Student> students = studentMapper.findByCriteria(criteria);
        return students;
    }

    public Student findByName(String name) {
        StudentCriteria criteria = new StudentCriteria();
        criteria.setUsePage(false);
        criteria.setName(name);
        List<Student> students = studentMapper.findByCriteria(criteria);
        if (students.size() == 0) {
            return null;
        }
        return students.get(0);
    }

    public List<Student> findByNick(String nick) {
        StudentCriteria criteria = new StudentCriteria();
        criteria.setUsePage(false);
        criteria.setNick(nick);
        List<Student> students = studentMapper.findByCriteria(criteria);
        return students;
    }

}