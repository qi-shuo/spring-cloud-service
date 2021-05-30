package com.qis.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @author qishuo
 * @date 2021/5/30 4:37 下午
 */
public interface IUserService {
    /**
     * 是否注册
     *
     * @param email
     * @return
     */
    boolean isRegistered(String email);

    /**
     * 注册
     *
     * @param email
     * @param password
     * @param code
     * @param response
     * @return
     */
    boolean register(String email, String password, String code,HttpServletResponse response);

    /**
     * 登录
     *
     * @param email
     * @param password
     * @return
     */
    String login(String email, String password);

    /**
     * 获取用户信息
     *
     * @param token
     * @return
     */
    String userInfo(String token);
}
