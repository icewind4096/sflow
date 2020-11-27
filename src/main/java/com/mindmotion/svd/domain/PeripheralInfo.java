package com.mindmotion.svd.domain;

import java.util.ArrayList;
import java.util.List;

public class PeripheralInfo {
    private String name;
    private String descript;
    private String groupName;
    private Integer baseAddress;
    private String access;

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    private List<RegisterInfo> registerInfoList;

    public PeripheralInfo() {
        registerInfoList = new ArrayList<RegisterInfo>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(Integer baseAddress) {
        this.baseAddress = baseAddress;
    }

    public List<RegisterInfo> getRegisterInfoList() {
        return registerInfoList;
    }

    public void setRegisterInfoList(List<RegisterInfo> registerInfoList) {
        this.registerInfoList = registerInfoList;
    }
}
