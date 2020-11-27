package com.mindmotion.svd.files;

import com.mindmotion.svd.domain.PeripheralInfo;
import com.mindmotion.svd.domain.RegisterFieldInfo;
import com.mindmotion.svd.domain.RegisterInfo;
import com.mindmotion.svd.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

public class SVDFile {
    private Document document;

    public SVDFile(String fileName) throws FileNotFoundException, DocumentException {
        this(new FileInputStream(new File(fileName)));
    }

    public SVDFile(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();

        document = reader.read(inputStream);
    }

    public List<Node> getPeripherslNodes() {
        return document.selectNodes("//peripheral");
    }

    public void getPeripheralInfoByNode(Node peripheralNode, PeripheralInfo peripheralInfo) {
        setPeripheralBaseByNode(peripheralNode, peripheralInfo);

        List<Node> registerNodes = getRegisterNodesByPeripheralNode(peripheralNode);

        setRegisterByNodes(registerNodes, peripheralInfo.getRegisterInfoList());
    }

    private void setRegisterByNodes(List<Node> registerNodes, List<RegisterInfo> registerInfoList) {
        for (Node node: registerNodes){
            RegisterInfo registerInfo = new RegisterInfo();

            Element element = (Element) node;
            if (element.attribute("derivedFrom") != null) {
                registerInfo = getRegisterInfoFromListByName(registerInfoList, element.attribute("derivedFrom").getText().trim());
            }
            setRegisterBaseByNode(node, registerInfo);

            List<Node> fieldNodes = getFieldNodesByRegisterNode(node);
            setfieldByNodes(fieldNodes, registerInfo.getFieldInfoList());

            adjustFieldAccess(registerInfo.getAccess(), registerInfo.getReadAction(), registerInfo.getFieldInfoList());

            registerInfoList.add(registerInfo);
        }
    }

    private void adjustFieldAccess(String registerAccess, String readAction, List<RegisterFieldInfo> registerFieldInfoList) {
        for (RegisterFieldInfo registerFieldInfo: registerFieldInfoList){
            if ((registerFieldInfo.getAccess() == null) || (registerFieldInfo.getAccess().trim().equalsIgnoreCase(""))){
                registerFieldInfo.setAccess(registerAccess);
            }

            if (registerFieldInfo.getReadAction() == null){
                if (readAction != null){
                    registerFieldInfo.setReadAction(readAction);
                }
            }
        }
    }

    private RegisterInfo getRegisterInfoFromListByName(List<RegisterInfo> registerInfoList, String registerName) {
        for (RegisterInfo registerInfo: registerInfoList){
            if (registerInfo.getName().equalsIgnoreCase(registerName)){
                RegisterInfo retRegisterInfo = new RegisterInfo();
                BeanUtils.copyProperties(retRegisterInfo, registerInfo);
                return retRegisterInfo;
            }
        }
        return null;
    }

    private void setfieldByNodes(List<Node> fieldNodes, List<RegisterFieldInfo> fieldInfoList) {
        for (Node node: fieldNodes){
            RegisterFieldInfo registerFieldInfo = new RegisterFieldInfo();
            setRegisterFieldBaseByNode(node, registerFieldInfo);
            fieldInfoList.add(registerFieldInfo);
        }
    }

    private void setRegisterFieldBaseByNode(Node node, RegisterFieldInfo registerFieldInfo) {
        Element element = (Element) node;
        registerFieldInfo.setName(element.elementText("name").trim());
        registerFieldInfo.setDescript(element.elementText("description").trim());
        registerFieldInfo.setBitOffset(Integer.parseInt(element.elementText("bitOffset").trim()));
        registerFieldInfo.setBitWidth(Integer.parseInt(element.elementText("bitWidth").trim()));

        if (element.elementText("access") != null){
            registerFieldInfo.setAccess(element.elementText("access").trim());
        }

        if (element.elementText("readAction") != null) {
            registerFieldInfo.setReadAction(element.elementText("readAction").trim());
        }
        if (element.elementText("modifiedWriteValues") != null) {
            registerFieldInfo.setModifiedWriteValues(element.elementText("modifiedWriteValues").trim());
        }
    }

    private List<Node> getFieldNodesByRegisterNode(Node node) {
        return node.selectNodes("./fields/field");
    }

    private void setRegisterBaseByNode(Node node, RegisterInfo registerInfo) {
        Element element = (Element) node;
        registerInfo.setName(element.elementText("name").trim());
        registerInfo.setDisplayName(element.elementText("displayName").trim());
        registerInfo.setAddressOffset(StringUtil.hex2Int(element.elementText("addressOffset").trim()));
        if (element.elementText("description") != null){
            registerInfo.setDescript(element.elementText("description").trim());
        }
        if (element.elementText("size") != null) {
            registerInfo.setSize(StringUtil.hex2Int(element.elementText("size").trim()));
        }
        if (element.elementText("access") != null) {
            registerInfo.setAccess(element.elementText("access").trim());
        }
        if (element.elementText("resetValue") != null) {
            registerInfo.setResetValue(StringUtil.hex2Int(element.elementText("resetValue").trim()));
        }
        if (element.elementText("readAction") != null) {
            registerInfo.setReadAction(element.elementText("readAction").trim());
        }
        if (element.elementText("modifiedWriteValues") != null) {
            registerInfo.setModifiedWriteValues(element.elementText("modifiedWriteValues").trim());
        }
    }

    private List<Node> getRegisterNodesByPeripheralNode(Node node) {
        return node.selectNodes("./registers/register");
    }

    private void setPeripheralBaseByNode(Node node, PeripheralInfo peripheralInfo) {
        Element element = (Element) node;
        peripheralInfo.setName(element.elementText("name").trim());
        peripheralInfo.setBaseAddress(StringUtil.hex2Int(element.elementText("baseAddress").trim()));
        if (element.attribute("derivedFrom") == null) {
            peripheralInfo.setDescript(element.elementText("description").trim());
            peripheralInfo.setGroupName(element.elementText("groupName").trim());
        }
    }

    public String getDerivedFromNode(Node node) {
        Element element = (Element) node;
        if (element.attribute("derivedFrom") != null){
            return element.attribute("derivedFrom").getText().trim();
        }
        return null;
    }
}
