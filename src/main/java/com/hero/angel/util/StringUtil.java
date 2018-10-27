package com.hero.angel.util;

import com.alibaba.druid.sql.visitor.functions.Char;

import java.util.Random;

public class StringUtil {


    public static char getHan() {
        Random ran = new Random();
        int delta = 0x9fa5 - 0x4e00 + 1;
        char han = (char) (0x4e00 + ran.nextInt(delta));
        return han;
    }
}
