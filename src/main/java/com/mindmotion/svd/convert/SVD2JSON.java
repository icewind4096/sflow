package com.mindmotion.svd.convert;

import com.mindmotion.svd.domain.*;
import com.mindmotion.svd.enums.ResultEnum;
import com.mindmotion.svd.files.SVDFile;
import com.mindmotion.svd.utils.FileUtil;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.springframework.beans.BeanUtils;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class SVD2JSON {
    public static int translate(String[] args) throws DocumentException, IllegalAccessException, InvocationTargetException {
        ParamaterInfo paramaterInfo = Arg2Paramater.arg2Paramater(args);

        if (!svdFileExist(paramaterInfo.getSrcFileName())){
            return ResultEnum.SVD_NOTEXIST.getCode();
        }

        List<PeripheralInfo> peripheralInfos = new ArrayList<PeripheralInfo>();
        if (!readPeripheralListFromFile(paramaterInfo.getSrcFileName(), peripheralInfos)){
            return ResultEnum.SVD_GETREGISTERs_FAIL.getCode();
        }

        List<JSONPeripheralInfo> jsonPeripheralInfos = new ArrayList<JSONPeripheralInfo>();
        if (!convert2RegGraphicJSON(peripheralInfos, jsonPeripheralInfos)){
            return ResultEnum.CONVERT2REGGRAPHICJSON_FAIL.getCode();
        }

        if (!saveRegGraphicInfos2Directory(paramaterInfo.getDestDirectory(), jsonPeripheralInfos)){
            return ResultEnum.SAVE2REGGRAPHICJSONFILE_FAIL.getCode();
        }

        return ResultEnum.SUCCESS.getCode();
    }

    private static boolean saveRegGraphicInfos2Directory(String directory, List<JSONPeripheralInfo> jsonPeripheralInfos) {
        for (JSONPeripheralInfo jsonPeripheralInfo: jsonPeripheralInfos){
            String peripheralName = jsonPeripheralInfo.getName();
            String groupName = jsonPeripheralInfo.getGroupName();
            for (JSONRegInfo jsonRegInfo: jsonPeripheralInfo.getRegInfoList()){
                String registerName = jsonRegInfo.getName();
                if (!FileUtil.saveToFileByList(getJSONFileName(directory, groupName, peripheralName, registerName), Reg2JSON.getJSONFileData(jsonRegInfo.getBitWidth(), jsonRegInfo.getBitInfoList()))){
                    System.out.println(String.format("产生文件%s失败", peripheralName));
                    return false;
                }
            }
        }
        return true;
    }

    private static String getJSONFileName(String rootDirectory, String groupName, String peripheralName, String registerName) {
        return String.format("%s\\%s\\%s\\%s.json", rootDirectory, groupName, peripheralName, registerName);
    }

    private static boolean convert2RegGraphicJSON(List<PeripheralInfo> source, List<JSONPeripheralInfo> dest) {
        return Reg2JSON.convert(source, dest);
    }

    private static boolean readPeripheralListFromFile(String fileName, List<PeripheralInfo> peripheralInfos) throws DocumentException, InvocationTargetException, IllegalAccessException {
        try {
            SVDFile svdFile = new SVDFile(fileName);

            for (Node node: svdFile.getPeripherslNodes()){
                PeripheralInfo peripheralInfo = new PeripheralInfo();

                String derivedFromName = svdFile.getDerivedFromNode(node);
                if (derivedFromName != null){
                    getPeripheralInfoByDerivedFromList(derivedFromName, peripheralInfos, peripheralInfo);
                }

                svdFile.getPeripheralInfoByNode(node, peripheralInfo);
                peripheralInfos.add(peripheralInfo);
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static void getPeripheralInfoByDerivedFromList(String derivedFromName, List<PeripheralInfo> peripheralInfos, PeripheralInfo peripheralInfo) {
        for (PeripheralInfo info: peripheralInfos){
            if (info.getName().equalsIgnoreCase(derivedFromName)){
                store2PeripheralInfo(info, peripheralInfo);
            }
        }
    }

    private static void store2PeripheralInfo(PeripheralInfo source, PeripheralInfo dest) {
        BeanUtils.copyProperties(source, dest);
        dest.setRegisterInfoList(new ArrayList<RegisterInfo>());
        store2RegisterList(source, dest);
    }

    private static void store2RegisterList(PeripheralInfo source, PeripheralInfo dest) {
        for (RegisterInfo info: source.getRegisterInfoList()){
            RegisterInfo registerInfo = new RegisterInfo();
            store2RegisterInfo(info, registerInfo);
            dest.getRegisterInfoList().add(registerInfo);
        }
    }

    private static void store2RegisterInfo(RegisterInfo source, RegisterInfo dest) {
        BeanUtils.copyProperties(source, dest);
        dest.setFieldInfoList(new ArrayList<RegisterFieldInfo>());
        store2RegisterFieldList(source, dest);
    }

    private static void store2RegisterFieldList(RegisterInfo source, RegisterInfo dest) {
        for (RegisterFieldInfo info: source.getFieldInfoList()){
            RegisterFieldInfo registerFieldInfo = new RegisterFieldInfo();
            store2RegisterFieldInfo(info, registerFieldInfo);
            dest.getFieldInfoList().add(registerFieldInfo);
        }
    }

    private static void store2RegisterFieldInfo(RegisterFieldInfo source, RegisterFieldInfo dest) {
        BeanUtils.copyProperties(source, dest);
    }

    private static boolean svdFileExist(String fileName) {
        return FileUtil.FileExists(fileName);
    }
}
