package com.tongu.search.controller;

import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import com.tongu.search.model.RespData;
import com.tongu.search.model.bo.ScoreBO;
import com.tongu.search.model.entity.Score;
import com.tongu.search.model.entity.ScoreType;
import com.tongu.search.service.ScoreService;
import com.tongu.search.service.ScoreTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreController extends BaseController {
	
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private ScoreTypeService scoreTypeService;

	@PostMapping("/type/list")
	public RespData<List<ScoreType>> listType(@RequestBody ScoreBO scoreBO) {
		ScoreType scoreType = new ScoreType();
		scoreType.setScoreType(StringUtils.trimToNull(scoreBO.getScoreType()));
		return success(scoreTypeService.findAll(Example.of(scoreType)));
	}

	@PostMapping("/type/save")
	public RespData<Void> saveType(@RequestBody ScoreType income) {
		if(StringUtils.isEmpty(income.getId())) {
			ScoreType scoreType = new ScoreType();
			scoreType.setScoreType(StringUtils.trimToNull(income.getScoreType()));
			List<ScoreType> scoreTypeList = scoreTypeService.findAll(Example.of(scoreType));
			if(!CollectionUtils.isEmpty(scoreTypeList)) {
				throw new RbacException(RbacErrorCodeEnum.DATA_EXISTS);
			}
			income.setId(null);
			income.setCreateDate(new Date());
			income.setLastUpdateDate(new Date());
			income.setBasicModelProperty(null, true);
		} else {
			income.setBasicModelProperty(null, false);
		}
		scoreTypeService.save(income);
		return success();
	}

	@GetMapping("/type/query/one")
	public RespData<ScoreType> queryOneType(@RequestParam("id") String id) {
		return success(scoreTypeService.findById(id));
	}

	@PostMapping("/type/delete")
	public RespData<Void> deleteType(@RequestBody ScoreBO scoreBO) {
		scoreTypeService.delete(scoreBO.getId());
		return success();
	}

	@PostMapping("/list")
	public RespData<List<Score>> list(@RequestBody ScoreBO scoreBO) {
		Score score = new Score();
		score.setScoreType(StringUtils.trimToNull(scoreBO.getScoreType()));
		return success(scoreService.findAll(score));
	}
	
	@PostMapping("/save")
	public RespData<Void> save(@RequestBody ScoreBO scoreBO) {
		Score score = new Score();
		score.setId(scoreBO.getId());
		if(StringUtils.isEmpty(score.getId())) {
			score.setId(null);
			score.setCreateDate(new Date());
			score.setLastUpdateDate(new Date());
			score.setBasicModelProperty(null, true);
		} else {
			score.setBasicModelProperty(null, false);
		}
		ScoreType scoreType = scoreTypeService.findById(scoreBO.getScoreTypeId());
		if(scoreType == null) {
			throw new RbacException(RbacErrorCodeEnum.DATA_NOT_EXISTS);
		}
		score.setScoreTypeId(scoreBO.getScoreTypeId());
		score.setScoreType(scoreType.getScoreType());
		score.setScoreValue(scoreType.getScoreValue());
		score.setScoreDate(scoreBO.getScoreDate());
		scoreService.save(score);
		return success();
	}

	@GetMapping("/query/one")
	public RespData<Score> queryOne(@RequestParam("id") String id) {
		return success(scoreService.findById(id));
	}

	@PostMapping("/delete")
	public RespData<Void> delete(@RequestBody ScoreBO scoreBO) {
		scoreService.delete(scoreBO.getId());
		return success();
	}
}
