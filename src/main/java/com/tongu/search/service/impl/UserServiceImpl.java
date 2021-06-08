package com.tongu.search.service.impl;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.google.common.collect.Lists;
import com.tongu.search.exception.RbacErrorCodeEnum;
import com.tongu.search.exception.RbacException;
import com.tongu.search.model.bo.UserBO;
import com.tongu.search.model.entity.UserEntity;
import com.tongu.search.repository.UserRepository;
import com.tongu.search.service.UserService;
import com.tongu.search.util.MD5Util;
import com.tongu.search.util.RandomUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private final LoadingCache<String, Long> recordCache = Caffeine.newBuilder().expireAfterWrite(30, TimeUnit.MINUTES).build(k->null);

	private static List<String> NOT_REQUIRED_AUTH_URLS = Lists.newArrayList(
			"/user/login",
						"/user/qr",
						"/user/check",
						"/score/**"
	);

	private static final PathMatcher pathMatcher = new AntPathMatcher();

	/**
	 * 30分钟内可以访问的次数
	 */
	private static long REFRESH_INTERVAL = 60*30L;
	
	@Override
	public synchronized String login(UserBO userBO) {
		if(StringUtils.isEmpty(userBO.getUserName()) 
				|| StringUtils.isEmpty(userBO.getUserPassword())) {
			throw new RbacException(RbacErrorCodeEnum.USER_LOGIN_FAILUER);
		}
		UserEntity user = userRepository.findByUserName(userBO.getUserName());
		if(user == null) {
			throw new RbacException(RbacErrorCodeEnum.USER_LOGIN_FAILUER);
		}
		if(!MD5Util.MD5Encode(userBO.getUserPassword()).equalsIgnoreCase(user.getUserPassword())) {
			throw new RbacException(RbacErrorCodeEnum.USER_LOGIN_FAILUER);
		}

		String random = RandomUtil.generateCode(32);
		recordCache.put(random, user.getId());
		return random;
	}

	@Override
	public UserEntity check(String random) {
		Long userId = recordCache.get(random);
		if(userId == null) {
			return null;
		}
		return userRepository.getOne(userId);
	}

	@Override
	public String getToken(Long userId) {
		String token = RandomUtil.generateCode(64);
		recordCache.put(token, userId);
		return token;
	}

	@Override
	public Long getUserId(String token) {
		return recordCache.get(token);
	}

	@Transactional
	@Override
	public void saveUserCredentials(String userName, String key) {
		UserEntity user = userRepository.findByUserName(userName);
		if(user == null) {
			throw new RbacException(RbacErrorCodeEnum.USER_LOGIN_FAILUER);
		}
		user.setCredential(key);
	}

	@Override
	public String getUserCredentials(String userName) {
		UserEntity user = userRepository.findByUserName(userName);
		if(user == null) {
			throw new RbacException(RbacErrorCodeEnum.USER_LOGIN_FAILUER);
		}
		return user.getCredential();
	}

	@Override
	public String findUserNameById(Long userId) {
		UserEntity userEntity = userRepository.findById(userId).orElse(null);
		return userEntity == null ? null : userEntity.getUserName();
	}

    @Override
    public Boolean canAccess(String url, String ip, String token) {
		// 限制访问次数，30分钟内同一个ip同一个url，1秒钟一次
		String key = url + ip + token;
		Long accessTimes = Optional.ofNullable(recordCache.get(key)).orElse(0L);
		if(accessTimes.compareTo(REFRESH_INTERVAL) > 0) {
			return false;
		}
		recordCache.put(key, ++accessTimes);

		// 无需鉴权的直接通过
		for(String noLimitUrl : NOT_REQUIRED_AUTH_URLS) {
			if(pathMatcher.match(noLimitUrl, url)){
				return true;
			}
		}

		if(StringUtils.isEmpty(token)) {
			return false;
		}
		Long userId = getUserId(token);
		if(userId == null) {
			return false;
		}

		ServletRequestAttributes req = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		req.getRequest().setAttribute("userId", userId);
		return true;
    }

}
