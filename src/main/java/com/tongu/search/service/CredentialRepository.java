package com.tongu.search.service;

import com.tongu.search.util.SpringUtil;
import com.warrenstrange.googleauth.ICredentialRepository;

import java.util.List;

/**
 * @author ：wangjf
 * @date ：2020/11/18 10:23
 * @description：provider-tennis
 * @version: v1.1.0
 */
public class CredentialRepository implements ICredentialRepository {

    @Override
    public String getSecretKey(String s) {
        return getUserService().getUserCredentials(s);
    }

    @Override
    public void saveUserCredentials(String s, String s1, int i, List<Integer> list) {
        getUserService().saveUserCredentials(s, s1);
    }

    private UserService getUserService() {
        return SpringUtil.getBean(UserService.class);
    }
}
