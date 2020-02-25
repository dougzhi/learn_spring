package com.dongz.hrm.system.controllers.clients;

import com.dongz.hrm.common.entities.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author dong
 * @date 2020/2/24 22:42
 * @desc
 */
@FeignClient(value = "company")
public interface CompanyFeignClient {

    @GetMapping("/api/company/getApiList")
    Result getApiList();

    @GetMapping("/api/company/getChildrenApiMap")
    Result getChildrenApiMap();

    @GetMapping("/api/company/getApiMaps")
    Result getApiMaps();
}
