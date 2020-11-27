package com.mindmotion.svd.domain;

import java.util.ArrayList;
import java.util.List;

public class JSONPeripheralInfo {
    private String name;
    private String groupName;
    private List<JSONRegInfo> regInfoList;

    public JSONPeripheralInfo() {
        regInfoList = new ArrayList<JSONRegInfo>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<JSONRegInfo> getRegInfoList() {
        return regInfoList;
    }

    public void setRegInfoList(List<JSONRegInfo> regInfoList) {
        this.regInfoList = regInfoList;
    }
}
