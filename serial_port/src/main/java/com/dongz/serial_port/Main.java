package com.dongz.serial_port;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/**
 * @author dong
 * @date 2020/4/14 11:18
 * @desc
 */
public class Main {

    public static void main(String[] args) {
        Enumeration<CommPortIdentifier> em = CommPortIdentifier.getPortIdentifiers();
        while (em.hasMoreElements()) {
            System.out.println("-----------");
            System.out.println(em.nextElement().getName());
            System.out.println(em.nextElement().getPortType());
            System.out.println("-----------");
        }
    }
}
