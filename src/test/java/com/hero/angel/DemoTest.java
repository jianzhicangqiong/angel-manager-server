package com.hero.angel;

import org.junit.Test;

import java.lang.ref.SoftReference;
import java.util.Random;

public class DemoTest {


    @Test
    public void fun() {
        String username = "";
        for (int k = 0; k < 6; k++) {
            username = username + (char) (Math.random() * 26 + 'A');
        }
        System.out.println(username);
    }

    @Test
    public void fun1() {
        String phone = "1811133";
        System.out.println(Math.random());
        for (int k = 0; k < 4; k++) {
            phone = phone + (int) (Math.random() * 10);
        }
        System.out.println(phone);
    }

    @Test
    public void fun2() {
        String lastLoginIp = "";
        for (int k = 1; k <= 15; k++) {
            if (k % 4 == 0) {
                lastLoginIp = lastLoginIp + ".";
            } else {
                lastLoginIp = lastLoginIp + (int) (Math.random() * 10);
            }
        }
        System.out.println(lastLoginIp);
    }


    @Test
    public void fun3() {
        Random ran = new Random();
        int delta = 0x9fa5 - 0x4e00 + 1;
        char han = (char) (0x4e00 + ran.nextInt(delta));
        System.out.println(han);
    }

}
