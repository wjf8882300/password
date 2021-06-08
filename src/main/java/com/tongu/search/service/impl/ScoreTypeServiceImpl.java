package com.tongu.search.service.impl;

import com.tongu.search.model.entity.ScoreType;
import com.tongu.search.repository.ScoreTypeRepository;
import com.tongu.search.service.ScoreTypeService;
import org.springframework.stereotype.Service;

@Service
public class ScoreTypeServiceImpl extends BaseServiceImpl<ScoreTypeRepository, ScoreType, String> implements ScoreTypeService {

}
