package com.tongu.search.controller;

import com.tongu.search.model.RespData;
import com.tongu.search.model.bo.IncomeBO;
import com.tongu.search.model.entity.Income;
import com.tongu.search.service.IncomeService;
import com.tongu.search.util.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/income")
public class IncomeController extends BaseController {
	
	@Autowired
	private IncomeService incomeService;

	@PostMapping("/list")
	public RespData<List<Income>> list(@RequestBody IncomeBO incomeBO) {
		incomeBO.setUserId(WebUtil.getCurrentUserId());
		return success(incomeService.query(incomeBO));
	}
	
	@PostMapping("/save")
	public RespData<Void> save(@RequestBody Income income) {
		income.setUserId(WebUtil.getCurrentUserId());
		if(StringUtils.isEmpty(income.getId())) {
			income.setId(null);
			income.setCreateDate(new Date());
			income.setLastUpdateDate(new Date());
			income.setBasicModelProperty(WebUtil.getCurrentUserId(), true);
		} else {
			income.setBasicModelProperty(WebUtil.getCurrentUserId(), false);
		}
		incomeService.save(income);
		return success();
	}

	@GetMapping("/query/one")
	public RespData<Income> queryOne(@RequestParam("id") String id) {
		return success(incomeService.findById(id));
	}

	@PostMapping("/delete")
	public RespData<Void> delete(@RequestBody IncomeBO incomeBO) {
		incomeService.delete(incomeBO.getId());
		return success();
	}
}
