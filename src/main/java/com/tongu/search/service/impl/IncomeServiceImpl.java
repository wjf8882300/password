package com.tongu.search.service.impl;

import com.tongu.search.model.bo.IncomeBO;
import com.tongu.search.model.entity.Income;
import com.tongu.search.repository.IncomeRepository;
import com.tongu.search.service.IncomeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Service
public class IncomeServiceImpl extends BaseServiceImpl<IncomeRepository, Income, String>  implements IncomeService  {

    @Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Income> query(IncomeBO incomeBO) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> root = query.from(Income.class);
        // query.multiselect(root.get("id"), root.get("incomeType"), root.get("incomeDate"), root.get("amount"), root.get("remark"));
        List<Predicate> predicateList = new ArrayList<Predicate>();
        predicateList.add(builder.equal(root.get("userId"), incomeBO.getUserId()));
        if(!StringUtils.isEmpty(incomeBO.getIncomeType())) {
            predicateList.add(builder.equal(root.get("incomeType"), incomeBO.getIncomeType()));
        }
        if(incomeBO.getStartDate() != null) {
            predicateList.add(builder.greaterThanOrEqualTo(root.get("incomeDate"), incomeBO.getStartDate()));
        }
        if(incomeBO.getEndDate() != null) {
            predicateList.add(builder.lessThan(root.get("incomeDate"), incomeBO.getEndDate()));
        }
        Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);
        query.where(predicates);
        query.orderBy(builder.desc(root.get("incomeDate")));
        List<Income> list = entityManager.createQuery(query).getResultList();
        return list;
    }
}
