package com.tongu.search.service.impl;

import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import com.tongu.search.model.entity.BaseEntity;
import com.tongu.search.repository.BaseRepository;
import com.tongu.search.service.BaseService;
import com.tongu.search.util.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 基础服务实现类
 * @param <R> Repository
 * @param <T> Entity
 * @param <ID> ID
 */
public class BaseServiceImpl<R extends BaseRepository<T, ID>, T extends BaseEntity,ID extends Serializable> implements BaseService<T, ID> {

    @Autowired
    protected R rep;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T save(T t) {
        if(!Objects.isNull(t.getId())) {
            T exists = findById((ID)t.getId());
            if(exists == null) {
                throw new RbacException(RbacErrorCodeEnum.DATA_NOT_EXISTS);
            }
            BeanUtil.copyPropertiesIgnoreNull(t, exists);
            t = exists;
        }
        return rep.save(t);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAll(Iterable<T> var1) {
        rep.saveAll(var1);
    }

    @Override
    public T findById(ID id) {
        return rep.findById(id).get();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(ID id) {
        rep.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(Iterable<ID> ids) {
        rep.deleteAll(rep.findAllById(ids));
    }

    @Override
    public boolean exists(ID id) {
        return rep.existsById(id);
    }

    @Override
    public long count() {
        return rep.count();
    }

    @Override
    public List<T> findAll() {
        return rep.findAll();
    }

    @Override
    public List<T> findAll(T t) {
        return rep.findAll(Example.of(t));
    }

    @Override
    public List<T> findAll(T t, Sort sort) {
        return rep.findAll(Example.of(t), sort);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return rep.findAll(example);
    }

    @Override
    public List<T> findAll(Example<T> example, Sort sort) {
        return rep.findAll(example, sort);
    }

    @Override
    public List<T> findByIds(Iterable<ID> ids) {
        return rep.findAllById(ids);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return rep.findAll(specification);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return rep.findAll(pageable);
    }

    @Override
    public Page<T> findAll(T t, Pageable pageable) {
        return rep.findAll(Example.of(t), pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return rep.findAll(example, pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return rep.findAll(specification, pageable);
    }

    @Override
    public List<T> findAll(T t, String... trimNullProperties) {
        BeanUtil.trimNullProperties(t, trimNullProperties);
        return findAll(t);
    }

    @Override
    public Page<T> findAll(T t, Pageable pageable, String... trimNullProperties) {
        BeanUtil.trimNullProperties(t, trimNullProperties);
        return findAll(t, pageable);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T save(T t, String... checkExistsProperties) {
        if(Objects.isNull(t.getId())) {
            List<String> propertiesList = checkExistsProperties != null ? Arrays.asList(checkExistsProperties) : null;
            if(!CollectionUtils.isEmpty(propertiesList)) {
                try {
                    T old = (T)t.getClass().newInstance();
                    BeanUtil.copyProperties(t, old, checkExistsProperties);
                    List<T> existsList = findAll(old);
                    if(!CollectionUtils.isEmpty(existsList)) {
                        throw new RbacException(RbacErrorCodeEnum.DATA_EXISTS);
                    }
                } catch (Exception e) {
                    throw new RbacException(e.getMessage());
                }
            }
        } else {
            T exists = findById((ID)t.getId());
            if(exists == null) {
                throw new RbacException(RbacErrorCodeEnum.DATA_NOT_EXISTS);
            }
            BeanUtil.copyPropertiesIgnoreNull(t, exists);
            t = exists;
        }
        return rep.save(t);
    }
}
