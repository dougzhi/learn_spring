package com.dongz.hrm.company;

import com.dongz.hrm.common.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * @author dong
 * @date 2019/12/26 13:45
 * @desc EntityScan  jps 包扫描
 */
@SpringBootApplication(scanBasePackages = "com.dongz")
@EntityScan(value = "com.dongz.hrm.domain.company")
@EnableEurekaClient
public class CompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(CompanyApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }
}
