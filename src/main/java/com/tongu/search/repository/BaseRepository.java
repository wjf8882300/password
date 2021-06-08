package com.tongu.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * 基础继承类
 * @author ：wangjf
 * @date ：2020/4/3 20:50
 * @description：provider-com
 * @version: v1.1.0
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID>,  JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
