package com.mindmotion.svd.domain;

import java.util.ArrayList;
import java.util.List;

public class RegisterInfo {
    private String name;
    private String displayName;
    private String descript;
    private int addressOffset;
    private int size;
    private String access;
    private int resetValue;
    private String readAction;
    private String modifiedWriteValues;
    List<RegisterFieldInfo> fieldInfoList;

    public RegisterInfo() {
        fieldInfoList = new ArrayList<RegisterFieldInfo>();
    }

    public String getModifiedWriteValues() {
        return modifiedWriteValues;
    }

    public void setModifiedWriteValues(String modifiedWriteValues) {
        this.modifiedWriteValues = modifiedWriteValues;
    }

    public String getReadAction() {
        return readAction;
    }

    public void setReadAction(String readAction) {
        this.readAction = readAction;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public int getAddressOffset() {
        return addressOffset;
    }

    public void setAddressOffset(int addressOffset) {
        this.addressOffset = addressOffset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getResetValue() {
        return resetValue;
    }

    public void setResetValue(int resetValue) {
        this.resetValue = resetValue;
    }

    public List<RegisterFieldInfo> getFieldInfoList() {
        return fieldInfoList;
    }

    public void setFieldInfoList(List<RegisterFieldInfo> fieldInfoList) {
        this.fieldInfoList = fieldInfoList;
    }
}
