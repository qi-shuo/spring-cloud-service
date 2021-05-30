package com.qis.service;

/**
 * @author qishuo
 * @date 2021/5/30 6:27 下午
 */
public interface IAuthCodeService {

    /**
     * 验证code
     *
     * @param email
     * @param code
     * @return
     */
    Integer validate(String email, String code);

    /**
     * 创建code
     *
     * @param email
     * @return
     */
    boolean createCode(String email);
}
