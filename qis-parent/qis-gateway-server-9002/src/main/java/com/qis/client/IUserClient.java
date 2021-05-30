package com.qis.client;

import com.qis.client.fallback.UserFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qishuo
 * @date 2021/5/30 8:32 下午
 */
@FeignClient(value = "qis-server-user", fallback = UserFallback.class, path = "/user")
public interface IUserClient {
    /**
     * 根据token获取邮箱
     * @param token
     * @return
     */
    @GetMapping("/info/{token}")
    String userInfo(@PathVariable("token") String token);
}
