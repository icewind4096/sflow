package com.mindmotion.svd.domain;

import java.util.ArrayList;
import java.util.List;

public class JSONRegInfo {
    private String name;
    private String access;
    private int bitWidth;
    private List<JSONBitInfo> bitInfoList;

    public JSONRegInfo() {
        bitInfoList = new ArrayList<JSONBitInfo>();
    }

    public int getBitWidth() {
        return bitWidth;
    }

    public void setBitWidth(int bitWidth) {
        this.bitWidth = bitWidth;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public List<JSONBitInfo> getBitInfoList() {
        return bitInfoList;
    }

    public void setBitInfoList(List<JSONBitInfo> bitInfoList) {
        this.bitInfoList = bitInfoList;
    }
}
