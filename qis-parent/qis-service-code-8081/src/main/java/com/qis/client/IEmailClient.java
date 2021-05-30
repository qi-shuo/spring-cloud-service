package com.qis.client;

import com.qis.client.fallback.EmailFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qishuo
 * @date 2021/5/30 7:01 下午
 */
@FeignClient(value = "qis-server-email", fallback = EmailFallback.class, path = "/email")
public interface IEmailClient {
    /**
     * 发送验证码
     *
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/{email}/{code}")
    boolean semEmail(@PathVariable("email") String email, @PathVariable("code") String code);
}
