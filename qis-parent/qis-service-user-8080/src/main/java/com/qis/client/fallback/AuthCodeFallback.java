package com.qis.client.fallback;

import com.qis.client.IAuthCodeClient;
import org.springframework.stereotype.Component;

/**
 * @author qishuo
 * @date 2021/5/30 7:18 下午
 */
@Component
public class AuthCodeFallback implements IAuthCodeClient {
    @Override
    public Integer validate(String email, String code) {
        return 2;
    }
}
