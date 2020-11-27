package com.mindmotion.svd.enums;

public enum BitAttribEnum {
    RESERVED(0, "reserved"),
    RES(0, "res."),
    RC_R(1, "rc_r"),
    RC(1, "rc"),
    RC_W1(2, "rc_w1"),
    W1C(2, "w1c"),
    RC_W0(4, "rc_w0"),
    W0C(4, "w0c"),
    R(5, "r"),
    RO(5, "ro"),
    W(6, "w"),
    WO(6, "wo"),
    RW(7, "rw"),
    RW_WT(7,"rw_wt"),
    RT_W(7, "rt_w"),
    ;

    private int code;
    private String descript;

    BitAttribEnum(int code, String descript) {
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