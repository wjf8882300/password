package com.tongu.search.service.impl;

import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import com.tongu.search.model.bo.PasswordBO;
import com.tongu.search.model.entity.PasswordEntity;
import com.tongu.search.model.vo.PasswordVO;
import com.tongu.search.model.vo.ProjectTypeVO;
import com.tongu.search.repository.PasswordRepository;
import com.tongu.search.service.PasswordService;
import com.tongu.search.util.AESUtil;
import com.tongu.search.util.IDUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class PasswordServiceImpl implements PasswordService {

	@Autowired
	private PasswordRepository passwordRepository;

	@Autowired
    @PersistenceContext
    private EntityManager entityManager;
	
	@Override
	public Integer save(PasswordBO passwordBO) {
		if(StringUtils.isEmpty(passwordBO.getProjectType())
				|| StringUtils.isEmpty(passwordBO.getProjectName())
				|| StringUtils.isEmpty(passwordBO.getUserPassword())) {
			throw new RbacException(RbacErrorCodeEnum.VALID_ERROR);
		}
		
		PasswordEntity password = new PasswordEntity();
		password.setUserId(passwordBO.getUserId());
		password.setProjectName(passwordBO.getProjectName());
		password.setProjectType(passwordBO.getProjectType());
		password.setRemark(passwordBO.getRemark());
		if(passwordBO.getId() == null) {
			password.setId(IDUtil.nextId());
			PasswordEntity exists = passwordRepository.findByUserIdAndProjectTypeAndProjectName(password.getUserId(), password.getProjectName(), password.getProjectType());
			if(exists != null) {
				throw new RbacException(RbacErrorCodeEnum.DATA_EXISTS);
			}
		} else {
			password.setId(passwordBO.getId());
		}
		password.setUserPassword(encrypt(password.getId(), passwordBO.getUserPassword()));
		passwordRepository.save(password);
		return 1;
	}

	@Override
	public List<PasswordVO> queryList(PasswordBO passwordBO) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PasswordVO> query = builder.createQuery(PasswordVO.class);
		Root<PasswordEntity> root = query.from(PasswordEntity.class);
		query.multiselect(root.get("id"), root.get("projectType"), root.get("projectName"), root.get("userPassword"), root.get("remark"));
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.equal(root.get("userId"), passwordBO.getUserId()));
		if(!StringUtils.isEmpty(passwordBO.getProjectType())) {
			List<Predicate> projectTypePredicateList = new ArrayList<Predicate>();
			projectTypePredicateList.add(builder.like(root.get("projectType"), new StringBuilder("%").append(passwordBO.getProjectType()).append("%").toString()));
			projectTypePredicateList.add(builder.like(root.get("projectName"), new StringBuilder("%").append(passwordBO.getProjectType()).append("%").toString()));
			predicateList.add(or(builder, projectTypePredicateList));
		}
		if(!StringUtils.isEmpty(passwordBO.getProjectName())) {
			List<Predicate> projectNamePredicateList = new ArrayList<Predicate>();
			projectNamePredicateList.add(builder.like(root.get("projectType"), new StringBuilder("%").append(passwordBO.getProjectName()).append("%").toString()));
			projectNamePredicateList.add(builder.like(root.get("projectName"), new StringBuilder("%").append(passwordBO.getProjectName()).append("%").toString()));
			predicateList.add(or(builder, projectNamePredicateList));
		}
		Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);
		query.where(predicates);
		List<PasswordVO> list = entityManager.createQuery(query).getResultList();
		if(!CollectionUtils.isEmpty(list)) {
			list.forEach(s->{s.setUserPassword(decrypt(s.getId(), s.getUserPassword()));});
		}
		return list;
	}

	private Predicate or(CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
		Predicate[] array = new Predicate[predicates.size()];
		return criteriaBuilder.or(predicates.toArray(array));
	}

	@Override
	public List<ProjectTypeVO> queryProjectTypeList(String projectType, Long userId) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProjectTypeVO> query = builder.createQuery(ProjectTypeVO.class);
		Root<PasswordEntity> root = query.from(PasswordEntity.class);
		query.multiselect(root.get("projectType")).distinct(true);
		List<Predicate> predicateList = new ArrayList<Predicate>();
		predicateList.add(builder.equal(root.get("userId"), userId));
		if(!StringUtils.isEmpty(projectType)) {
			predicateList.add(builder.like(root.get("projectType"), new StringBuilder("%").append(projectType).append("%").toString()));
		}
		Predicate[] predicates = new Predicate[predicateList.size()];
        predicates = predicateList.toArray(predicates);
		query.where(predicates);
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public PasswordVO queryOne(Long id) {
		PasswordEntity password = passwordRepository.findById(id).orElse(null);
		if(password !=null) {
			return new PasswordVO(password.getId(), password.getProjectType(), password.getProjectName(), decrypt(password.getId(), password.getUserPassword()), password.getRemark());
		}
		return null;
	}

	@Override
	public Integer delete(Long id) {
		passwordRepository.deleteById(id);
		return 1;
	}

	private String encrypt(Long id, String password) {
		byte[] bytes = id.toString().substring(0, 16).getBytes();
		return AESUtil.encrypt(password, Base64.encodeBase64String(bytes), Base64.encodeBase64String(bytes));
	}

	private String decrypt(Long id, String password) {
		byte[] bytes = id.toString().substring(0, 16).getBytes();
		return AESUtil.decrypt(password, Base64.encodeBase64String(bytes), Base64.encodeBase64String(bytes));
	}
}
