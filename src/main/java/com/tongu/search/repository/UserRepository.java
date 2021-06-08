package com.tongu.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.tongu.search.model.entity.UserEntity;

public interface UserRepository extends Repository<UserEntity, Long>, JpaRepository<UserEntity,Long>{

	UserEntity findByUserName(String userName);
}
