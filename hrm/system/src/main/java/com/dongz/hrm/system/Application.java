package com.dongz.hrm.system;

import com.dongz.hrm.common.utils.IdWorker;
import com.dongz.hrm.common.utils.JwtUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

/**
 * @author dong
 * @date 2020/2/6 15:29
 * @desc
 */
@SpringBootApplication(scanBasePackages = "com.dongz")
@EntityScan(value = "com.dongz.hrm.domain.system")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker();
    }

    @Bean
    public JwtUtils jwtUtils() {
        return new JwtUtils();
    }
}
