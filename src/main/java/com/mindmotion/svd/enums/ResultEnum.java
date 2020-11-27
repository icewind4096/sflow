package com.mindmotion.svd.enums;

public enum ResultEnum {
    SUCCESS(0, "SUCCESS"),
    SVD_NOTEXIST(100, "SVD 文件不存在"),
    SVD_GETREGISTERs_FAIL(200, "获取寄存器列表失败"),
    CONVERT2REGGRAPHICJSON_FAIL(300, "转换寄存器信息到图形JSON列表失败"),
    SAVE2REGGRAPHICJSONFILE_FAIL(400, "转换寄存器信息到图形JSON列表失败")
    ;

    private int code;
    private String descript;

    ResultEnum(int code, String descript) {
        this.code = code;
        this.descript = descript;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }
}
