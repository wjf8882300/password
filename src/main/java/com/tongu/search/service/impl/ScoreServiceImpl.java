package com.tongu.search.service.impl;

import com.tongu.search.model.entity.Score;
import com.tongu.search.repository.ScoreRepository;
import com.tongu.search.service.ScoreService;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl extends BaseServiceImpl<ScoreRepository, Score, String> implements ScoreService {

}
