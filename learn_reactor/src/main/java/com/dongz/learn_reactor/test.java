package com.dongz.learn_reactor;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * @author dong
 * @date 2020/3/28 13:23
 * @desc
 */
public class test {

    /**
     * 订阅
     */
    @Test
    public void test01() {
        Flux<String> flux = Flux.just("hello", "work");

        flux.subscribe(
                System.out::println,
                System.out::println,
                () -> System.out.println("finish"),
                subscription -> subscription.request(1)
        );
    }

    /**
     * 异常
     */
    @Test
    public void test02() {
        Flux.range(-3, 6).map(e-> {
            int i = e/e;
            return e;
        }).onErrorContinue((err,val)-> System.out.println(err +": " + val))
                .subscribe(System.out::println);

        Flux.range(-2, 5).map(e -> {
            int i = e / e;
            return e;
        }).onErrorResume(err -> Flux.range(3, 6))//返回新的流
        .subscribe(System.out::println);
    }
}
