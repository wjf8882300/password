package com.tongu.search.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.tongu.search.model.entity.PasswordEntity;

public interface PasswordRepository extends Repository<PasswordEntity, Long>, JpaRepository<PasswordEntity,Long>{

	PasswordEntity findByUserIdAndProjectTypeAndProjectName(Long userId, String projectType, String projectName);

}
