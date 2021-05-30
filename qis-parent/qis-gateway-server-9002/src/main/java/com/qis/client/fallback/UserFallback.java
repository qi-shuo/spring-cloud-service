package com.qis.client.fallback;

import com.qis.client.IUserClient;
import org.springframework.stereotype.Component;

/**
 * @author qishuo
 * @date 2021/5/30 8:34 下午
 */
@Component
public class UserFallback implements IUserClient {
    @Override
    public String userInfo(String token) {
        return "";
    }
}
