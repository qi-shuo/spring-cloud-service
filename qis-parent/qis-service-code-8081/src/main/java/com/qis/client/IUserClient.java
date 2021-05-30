package com.qis.client;

import com.qis.client.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qishuo
 * @date 2021/5/30 7:34 下午
 */
@FeignClient(value = "qis-server-user", fallback = UserFallback.class, path = "/user")
public interface IUserClient {
    /**
     * 是否注册过
     * @param email
     * @return
     */
    @GetMapping("/isRegistered/{email}")
    boolean isRegistered(@PathVariable("email") String email);
}
