package com.mindmotion.svd.domain;

public class RegisterFieldInfo {
    private String name;
    private String descript;
    private int bitOffset;
    private int bitWidth;
    private String access;
    private String readAction;
    private String modifiedWriteValues;

    public String getModifiedWriteValues() {
        return modifiedWriteValues;
    }

    public void setModifiedWriteValues(String modifiedWriteValues) {
        this.modifiedWriteValues = modifiedWriteValues;
    }

    public String getAccess() {
        return access;
    }

    public String getReadAction() {
        return readAction;
    }

    public void setReadAction(String readAction) {
        this.readAction = readAction;
    }

    public void setAccess(String access) {
        this.access = access;
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

    public int getBitOffset() {
        return bitOffset;
    }

    public void setBitOffset(int bitOffset) {
        this.bitOffset = bitOffset;
    }

    public int getBitWidth() {
        return bitWidth;
    }

    public void setBitWidth(int bitWidth) {
        this.bitWidth = bitWidth;
    }
}
