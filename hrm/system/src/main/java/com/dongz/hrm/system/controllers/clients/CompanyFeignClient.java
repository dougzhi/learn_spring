package com.dongz.hrm.system.controllers.clients;

import com.dongz.hrm.common.entities.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dong
 * @date 2020/2/24 22:42
 * @desc
 */
@FeignClient("company")
public interface CompanyFeignClient {

    @GetMapping("getApiList")
    Result getApiList();

    @GetMapping("getApiMap")
    Result getApiMap();
}
