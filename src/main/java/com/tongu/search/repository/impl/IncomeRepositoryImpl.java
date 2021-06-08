package com.tongu.search.repository.impl;

import com.tongu.search.repository.IncomeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class IncomeRepositoryImpl implements IncomeRepositoryCustom {

	@PersistenceContext
	private EntityManager em;
}
