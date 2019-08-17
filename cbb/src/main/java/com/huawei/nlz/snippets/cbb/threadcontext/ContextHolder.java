package com.huawei.nlz.snippets.cbb.threadcontext;

import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

/**
 * 线程级上下文
 */
@Slf4j
public class ContextHolder {

    /**
     * 线程上下文，登录Token信息
     */
    private static ThreadLocal<Token> loginToken = new ThreadLocal<>();

    /**
     * 隐藏构造方法，只能通过静态方法访问
     */
    private ContextHolder() {
    }


    /**
     * 设置线程上下文中的token信息
     *
     * @param token token信息
     */
    public static void setLoginToken(@NonNull Token token) {
        ContextHolder.loginToken.set(token);
    }

    /**
     * 获取上下文中的用户Id
     *
     * @return 用户Id，未登录返回null
     */
    public static String getUserId() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getUserId)
                .orElse("");
    }

    /**
     * 设置上下文中的用户Id
     *
     * @param userId 用户id
     */
    public static void setUserId(String userId) {
        if (!Optional.ofNullable(ContextHolder.loginToken.get()).isPresent()) {
            ContextHolder.loginToken.set(new Token());
        }
        ContextHolder.loginToken.get().setUserId(userId);
    }

    /**
     * 获取上下文中的用户TenantId
     *
     * @return TenantId，未登录返回null
     */
    public static String getTenantId() {
        String tenantId;
        tenantId = Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getTenantId)
                .orElse(null);

        return tenantId;
    }

    /**
     * 设置上下文中的租户Id
     *
     * @param tenantId 租间Id
     */
    public static void setTenantId(String tenantId) {
        if (!Optional.ofNullable(ContextHolder.loginToken.get()).isPresent()) {
            ContextHolder.loginToken.set(new Token());
        }
        ContextHolder.loginToken.get().setTenantId(tenantId);
    }

    /**
     * 获取上下文中的用户账户
     *
     * @return 用户账户，未登录返回null
     */
    public static String getUserAccount() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getUserAccount)
                .orElse(null);
    }

    /**
     * 获取上下文中的TenantSpaceId
     *
     * @return TenantSpaceId，未登录返回null
     */
    public static String getTenantSpaceId() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getTenantSpaceId)
                .orElse(null);
    }

    /**
     * 设置上下文中的租间Id
     *
     * @param tenantSpaceId 租间Id
     */
    public static void setTenantSpaceId(String tenantSpaceId) {
        if (!Optional.ofNullable(ContextHolder.loginToken.get()).isPresent()) {
            ContextHolder.loginToken.set(new Token());
        }
        ContextHolder.loginToken.get().setTenantSpaceId(tenantSpaceId);
    }

    /**
     * 获取Session Token
     *
     * @return SessionToken，未登录返回null
     */
    public static String getSessionToken() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getSessionToken)
                .orElse(null);
    }

    /**
     * 获取用户Roles
     *
     * @return Roles，未登录返回空的List
     */
    public static List<String> getRoles() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getRoles)
                .orElse(Collections.EMPTY_LIST);
    }

    /**
     * 获取Token失效时间
     *
     * @return Token失效时间
     */
    public static long getExp() {
        return Optional.ofNullable(ContextHolder.loginToken.get())
                .map(Token::getExp)
                .orElse(-1L);
    }

    /**
     * 删除登陆Token
     */
    public static void removeLoginToken() {
        ContextHolder.loginToken.remove();
    }

    /**
     * 获取用户时区
     *
     * @return 用户的时区
     */
    public static TimeZone getUserTimeZone() {
        return TimeZone.getTimeZone("GMT+8");
    }

    /**
     * Token类，存储了用户的登陆信息
     */
    @Data
    public static class Token {
        private long exp;
        private String tenantId;
        private String userId;
        private String userAccount;
        private String tenantSpaceId;
        private String locale;
        private String sessionToken;
        private List<String> roles;
    }
}