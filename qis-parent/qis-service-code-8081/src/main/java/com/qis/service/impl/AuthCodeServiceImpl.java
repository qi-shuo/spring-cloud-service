package com.qis.service.impl;

import com.qis.client.IEmailClient;
import com.qis.client.IUserClient;
import com.qis.mapper.ILaGouAuthCodeMapper;
import com.qis.service.IAuthCodeService;
import com.qis.vo.LaGouAuthCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.Random;


/**
 * @author qishuo
 * @date 2021/5/30 6:27 下午
 */
@Service
@Slf4j
public class AuthCodeServiceImpl implements IAuthCodeService {

    @Resource
    private ILaGouAuthCodeMapper laGouAuthCodeMapper;
    @Resource
    private IEmailClient emailClient;
    @Resource
    private IUserClient userClient;

    @Override
    public Integer validate(String email, String code) {
        LaGouAuthCode lastLaGouAuthCode = laGouAuthCodeMapper.getLastLaGouAuthCode(email, new Date());
        if (Objects.isNull(lastLaGouAuthCode) || !code.equals(lastLaGouAuthCode.getCode())) {
            return 2;
        }
        return 0;
    }

    @Override
    public boolean createCode(String email) {
        boolean registered = userClient.isRegistered(email);
        if (registered) {
            //已经注册过了
            return false;
        }
        LaGouAuthCode lastLaGouAuthCode = laGouAuthCodeMapper.getLastLaGouAuthCode(email, new Date());
        if (Objects.nonNull(lastLaGouAuthCode)) {
            return true;
        }
        Integer authCode = getAuthCode();
        log.info("authCode:{}", authCode);
        boolean sendResult = emailClient.semEmail(email, String.valueOf(authCode));
        if (sendResult) {
            long time = System.currentTimeMillis();
            laGouAuthCodeMapper.save(LaGouAuthCode.builder().email(email)
                    .code(String.valueOf(authCode))
                    .createtime(new Date(time))
                    .expiretime(new Date(time + 60000))
                    .build());
            return true;
        }
        return false;

    }

    private Integer getAuthCode() {
        return new Random().nextInt(1000000);
    }
}
