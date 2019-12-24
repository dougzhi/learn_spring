package com.dongz.javalearn.boot.services;

import com.dongz.javalearn.boot.annotation.Log;
import org.springframework.stereotype.Service;

/**
 * @author dong
 * @date 2019/12/23 17:46
 * @desc
 */
@Service
public class TestService {

    @Log(id = "id")
    public Long test(Long id) {
        return id;
    }
}
