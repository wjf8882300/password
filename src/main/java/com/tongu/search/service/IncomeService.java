package com.tongu.search.service;

import com.tongu.search.model.bo.IncomeBO;
import com.tongu.search.model.entity.Income;

import java.util.List;

public interface IncomeService extends BaseService<Income, String>  {

    /**
     * 查询
     * @param incomeBO
     * @return
     */
    List<Income> query(IncomeBO incomeBO);

}
