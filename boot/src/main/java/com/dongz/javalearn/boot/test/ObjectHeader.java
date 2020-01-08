package com.dongz.javalearn.boot.test;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author dong
 * @date 2020/1/7 15:51
 * @desc
 */
public class ObjectHeader {
    public static class Lock {}

    public static void main(String[] args) {
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());
    }
}
