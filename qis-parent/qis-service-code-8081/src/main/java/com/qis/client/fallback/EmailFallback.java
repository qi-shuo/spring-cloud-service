package com.qis.client.fallback;

import com.qis.client.IEmailClient;
import org.springframework.stereotype.Component;

/**
 * @author qishuo
 * @date 2021/5/30 7:04 下午
 */
@Component
public class EmailFallback implements IEmailClient {

    @Override
    public boolean semEmail(String email, String code) {
        return false;
    }
}
