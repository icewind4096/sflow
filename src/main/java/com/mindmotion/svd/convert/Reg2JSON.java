package com.mindmotion.svd.convert;

import com.mindmotion.svd.domain.*;
import com.mindmotion.svd.enums.BitAttribEnum;
import com.mindmotion.svd.utils.IntegerUtil;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Reg2JSON {
    public static boolean convert(List<PeripheralInfo> source, List<JSONPeripheralInfo> dest) {
        for (PeripheralInfo peripheralInfo: source){
            JSONPeripheralInfo jsonPeripheralInfo = new JSONPeripheralInfo();
            jsonPeripheralInfo.setName(peripheralInfo.getName());
            jsonPeripheralInfo.setGroupName(peripheralInfo.getGroupName());
            if (!convertRegList(peripheralInfo.getRegisterInfoList(), jsonPeripheralInfo.getRegInfoList())){
                return false;
            }
            dest.add(jsonPeripheralInfo);
        }
        return true;
    }

    private static boolean convertRegList(List<RegisterInfo> registerInfoList, List<JSONRegInfo> jsonRegInfoList) {
        for (RegisterInfo registerInfo: registerInfoList){
            JSONRegInfo jsonRegInfo = new JSONRegInfo();
            jsonRegInfo.setName(registerInfo.getName());
            jsonRegInfo.setAccess(registerInfo.getAccess());
            jsonRegInfo.setBitWidth(registerInfo.getSize());
            if (!convertBitList(registerInfo.getSize(), registerInfo.getFieldInfoList(), jsonRegInfo.getBitInfoList())){
                System.out.println(String.format("转换寄存器%s位标记错误，请检查", registerInfo.getName()));
                return false;
            }
            jsonRegInfoList.add(jsonRegInfo);
        }
        return true;
    }

    private static boolean convertBitList(int bitSize, List<RegisterFieldInfo> registerFieldInfoList, List<JSONBitInfo> bitInfoList) {
        RegisterFieldInfo registerFieldInfo;
        int map = getRegisterBitMap(registerFieldInfoList);
        int index = 0;
        int bResCount = 0;
        while (index < bitSize){
            if (IntegerUtil.isBitOn(map, index)){
                if (bResCount == 0){
                    registerFieldInfo = getRegisterFieldInfoByIndex(index, registerFieldInfoList);
                    if (registerFieldInfo == null){
                        return false;
                    }
                    index = index + registerFieldInfo.getBitWidth();
                    bitInfoList.add(convertBitField2JSON(registerFieldInfo.getName(), registerFieldInfo.getBitWidth(), registerFieldInfo.getAccess(), registerFieldInfo.getReadAction(), registerFieldInfo.getModifiedWriteValues()));
                } else {
                    bitInfoList.add(new JSONBitInfo("RES.", bResCount));
                    bResCount = 0;
                }
            } else {
                bResCount = bResCount + 1;
                index = index + 1;
            }
        }
        if (bResCount > 0){
            bitInfoList.add(new JSONBitInfo("RES.", bResCount));
        }
        return true;
    }

    private static RegisterFieldInfo getRegisterFieldInfoByIndex(int index, List<RegisterFieldInfo> registerFieldInfoList) {
        for (RegisterFieldInfo registerFieldInfo: registerFieldInfoList){
            if (registerFieldInfo.getBitOffset() == index){
                return registerFieldInfo;
            }
        }
        return null;
    }

    private static int getRegisterBitMap(List<RegisterFieldInfo> registerFieldInfoList) {
        int value = 0;
        for (RegisterFieldInfo registerFieldInfo: registerFieldInfoList) {
            for (int i = 0 ; i < registerFieldInfo.getBitWidth(); i ++){
                value = IntegerUtil.setBitOn(value, registerFieldInfo.getBitOffset() + i);
            }
        }
        return value;
    }

    public static JSONBitInfo convertBitField2JSON(String name, int width, String attrib, String readAction, String modifiedWriteValues){
        JSONBitInfo jsonBitInfo = new JSONBitInfo();
        jsonBitInfo.setName(name.trim());
        jsonBitInfo.setBits(width);
        jsonBitInfo.setAttrib(access2AttribName(attrib, readAction, modifiedWriteValues));
        jsonBitInfo.setType(access2AttribType(jsonBitInfo.getAttrib()));
        return jsonBitInfo;
    }

    private static int access2AttribType(String attrib) {
        for (BitAttribEnum bitAttribEnum: BitAttribEnum.values()){
            if (bitAttribEnum.getDescript().equalsIgnoreCase(attrib)){
                return bitAttribEnum.getCode();
            }
        }
        return 0;
    }

    private static String access2AttribName(String attrib, String readAction, String modifiedWriteValues){
        if (modifiedWriteValues != null) {
            if (modifiedWriteValues.equalsIgnoreCase("oneToClear")) {
                return BitAttribEnum.W1C.getDescript();
            }

            if (modifiedWriteValues.equalsIgnoreCase("zeroToClear")) {
                return BitAttribEnum.W0C.getDescript();
            }
        }

        if (readAction != null){
            if (readAction.equalsIgnoreCase("clear")){
                return BitAttribEnum.RC.getDescript();
            }
        }

        if ("read-only".equalsIgnoreCase(attrib.trim())){
            return BitAttribEnum.R.getDescript();
        }

        if ("write-only".equalsIgnoreCase(attrib.trim())){
            return BitAttribEnum.W.getDescript();
        }

        return BitAttribEnum.RW.getDescript();
    }

    public static List<String> getJSONFileData(int regBitWidth, List<JSONBitInfo> bitInfoList) {
        List<String> data = new ArrayList<String>();

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("reg", getJSONByBitList(bitInfoList));

        jsonObject.put("config", getJSONStyle(regBitWidth));

        data.add(jsonObject.toString());

        return data;
    }

    private static JSONObject getJSONStyle(int regBitWidth) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("vspace", 80);
        jsonObject.put("hspace", 1000);
        jsonObject.put("lanes", 2);
        jsonObject.put("fontsize", 12);
        jsonObject.put("fontfamily", "Arial");
        jsonObject.put("bits", regBitWidth);
        jsonObject.put("bigendian", false);
        jsonObject.put("fontweight", "normal");
        jsonObject.put("compact", false);

        return jsonObject;
    }

    private static JSONArray getJSONByBitList(List<JSONBitInfo> bitInfoList) {
        JSONArray jsonArray = new JSONArray();
        for (JSONBitInfo bitInfo: bitInfoList){
            JSONObject jsonBitInfo = new JSONObject();
            jsonBitInfo.put("bits", bitInfo.getBits());
            jsonBitInfo.put("name", bitInfo.getName());
            jsonBitInfo.put("attr", bitInfo.getAttrib());
            jsonBitInfo.put("type", bitInfo.getType());
            jsonArray.put(jsonBitInfo);
        }
        return jsonArray;
    }
}