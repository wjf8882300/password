package com.tongu.search.util;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

/**
 * @author ：wangjf
 * @date ：2020/11/18 9:32
 * @description：provider-tennis
 * @version: v1.1.0
 */
public class GoogleAuthUtil {

    /**
     * 生成二维码地址
     * @return
     */
    public static String getQr(String userName) {
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        final GoogleAuthenticatorKey key = googleAuthenticator.createCredentials(userName);
        String otpAuthTotpURL = GoogleAuthenticatorQRGenerator.getOtpAuthURL("password", userName, key);
        return otpAuthTotpURL;
    }

    /**
     * 校验
     * @param code
     * @return
     */
    public static Boolean check(String userName, int code) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorizeUser(userName, code);
        return isCodeValid;
    }
}
