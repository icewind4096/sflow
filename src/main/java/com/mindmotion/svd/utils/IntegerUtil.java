package com.mindmotion.svd.utils;

public class IntegerUtil {
    public static boolean isBitOn(int value, int index){
        return ((value >> index) & 1) == 1;
    }

    public static boolean isBitOff(int value, int index){
        return ((value >> index) & 1) == 0;
    }

    public static int setBitOn(int value, int index){
        return value | (1 << index);
    }

    public static int setBitOff(int value, int index){
        return value & (~(1 << index));
    }
}
