package com.mindmotion.svd.utils;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
    public static int hex2Int(String hexValue){
        if (StringUtils.left(hexValue.trim(), 2).equalsIgnoreCase("0X")){
            hexValue = hexValue.substring(2, hexValue.length());
        }

        if (StringUtils.right(hexValue.trim(), 1).equalsIgnoreCase("H")){
            hexValue = hexValue.substring(0, hexValue.length() - 1);
        }

        return (int) Long.parseLong(hexValue, 16);
    }
}
