package com.dongz.hrm.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author dong
 * @date 2019/12/26 13:45
 * @desc EntityScan  jps 包扫描
 */
@SpringBootApplication(scanBasePackages = "com.dongz")
@EntityScan(value = "com.dongz.hrm.domain.company")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
