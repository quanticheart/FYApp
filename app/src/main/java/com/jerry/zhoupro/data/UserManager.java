package com.jerry.zhoupro.data;

import com.jerry.zhoupro.command.Key;
import com.jerry.zhoupro.util.PreferenceUtil;

import android.text.TextUtils;

/**
 * Created by wzl-pc on 2017/5/16.
 */

public class UserManager {

    private static UserManager instance;
    private static User user;

    private UserManager() {}

    public static UserManager getInstance() {
        if (null == instance) {
            synchronized (UserManager.class) {
                if (null == instance) {
                    instance = new UserManager();
                    if (null == user) {
                        user = new User();
                        user.setMobilePhoneNumber(PreferenceUtil.getPreference(Key.USER_MOBLIE));
                        user.setNickname(PreferenceUtil.getPreference(Key.USER_NICKNAME));
                        user.setSessionToken(PreferenceUtil.getPreference(Key.USER_SESSIONTOKEN));
                    }
                }
            }
        }
        return instance;
    }

    public User getUser() {
        return user;
    }

    public String getMobilePhoneNumber() {
        return user.getMobilePhoneNumber();
    }

    public void setMobilePhoneNumber(String phone) {
        user.setMobilePhoneNumber(phone);
    }

    public String getNickname() {
        return user.getNickname();
    }

    public void setNickname(String nickname) {
        user.setNickname(nickname);
    }

    public String getSessionToken() {
        return user.getSessionToken();
    }

    public void setSessionToken(String sessionToken) {
        user.setSessionToken(sessionToken);
    }

    public void saveToLocal(User user) {
        UserManager.user = user;
        PreferenceUtil.setPreference(Key.USER, user.getObjectId());
        PreferenceUtil.setPreference(Key.USER_MOBLIE, user.getMobilePhoneNumber());
        PreferenceUtil.setPreference(Key.USER_NICKNAME, user.getUsername());
        PreferenceUtil.setPreference(Key.USER_SESSIONTOKEN, user.getSessionToken());
    }

    public static boolean hasLogin() {
        return !TextUtils.isEmpty(PreferenceUtil.getPreference(Key.USER));
    }

    public static void clearLoginInfo() {
        PreferenceUtil.setPreference(Key.USER, Key.NIL);
        PreferenceUtil.setPreference(Key.USER_MOBLIE, Key.NIL);
        PreferenceUtil.setPreference(Key.USER_NICKNAME, Key.NIL);
        PreferenceUtil.setPreference(Key.USER_SESSIONTOKEN, Key.NIL);
    }
}