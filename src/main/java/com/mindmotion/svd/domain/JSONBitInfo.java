package com.mindmotion.svd.domain;

public class JSONBitInfo {
    private int bits;
    private String name;
    private String attrib;
    private int type;

    public JSONBitInfo() {
    }

    public JSONBitInfo(String name, int bitWidth) {
        this.name = name;
        this.bits = bitWidth;
    }

    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttrib() {
        return attrib;
    }

    public void setAttrib(String attrib) {
        this.attrib = attrib;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
