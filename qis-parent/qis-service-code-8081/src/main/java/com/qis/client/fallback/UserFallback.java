package com.qis.client.fallback;

import com.qis.client.IUserClient;
import org.springframework.stereotype.Component;

/**
 * @author qishuo
 * @date 2021/5/30 7:35 下午
 */
@Component
public class UserFallback implements IUserClient {
    @Override
    public boolean isRegistered(String email) {
        return false;
    }
}
