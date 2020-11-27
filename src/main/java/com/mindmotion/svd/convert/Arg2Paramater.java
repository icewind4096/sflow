package com.mindmotion.svd.convert;

import com.mindmotion.svd.domain.ParamaterInfo;

public class Arg2Paramater {
    public static ParamaterInfo arg2Paramater(String[] args) {
        ParamaterInfo paramaterInfo = new ParamaterInfo();
        paramaterInfo.setSrcFileName(args[0]);
        paramaterInfo.setDestDirectory(args[1]);
        return paramaterInfo;
    }
}
