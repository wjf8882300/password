package com.tongu.search.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * 基础服务接口
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID extends Serializable> {
    T save(T t);

    void saveAll(Iterable<T> var1);

    T findById(ID id);

    void delete(ID id);

    void deleteByIds(Iterable<ID> ids);

    boolean exists(ID id);

    long count();

    List<T> findAll();

    List<T> findAll(T t);

    List<T> findAll(T t, Sort sort);

    List<T> findAll(Example<T> example);

    List<T> findAll(Example<T> example, Sort sort);

    List<T> findByIds(Iterable<ID> ids);

    List<T> findAll(Specification<T> specification);

    Page<T> findAll(Pageable pageable);

    Page<T> findAll(T t, Pageable pageable);

    Page<T> findAll(Example<T> example, Pageable pageable);

    Page<T> findAll(Specification<T> specification, Pageable pageable);

    /**
     * 查询
     * @param t
     * @param trimNullProperties 某些属性做trimNull操作
     * @return
     */
    List<T> findAll(T t, String... trimNullProperties);

    /**
     * 查询
     * @param t
     * @param trimNullProperties 某些属性做trimNull操作
     * @return
     */
    Page<T> findAll(T t, Pageable pageable, String... trimNullProperties);

    /**
     * 保存
     * @param t
     * @param checkExistsProperties 检查条件为某些属性的记录是否存在
     */
    T save(T t, String... checkExistsProperties);
}
