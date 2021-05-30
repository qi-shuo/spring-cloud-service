package com.qis.client;

import com.qis.client.fallback.AuthCodeFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author qishuo
 * @date 2021/5/30 7:17 下午
 */
@FeignClient(value = "qis-server-code", fallback = AuthCodeFallback.class, path = "/code")
public interface IAuthCodeClient {

    /**
     * 验证code是否正确
     *
     * @param email
     * @param code
     * @return
     */
    @GetMapping("/validate/{email}/{code}")
    Integer validate(@PathVariable("email") String email, @PathVariable("code") String code);
}
