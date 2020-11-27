package com.mindmotion.sflow.controller.back;

import com.mindmotion.sflow.common.ServerResponse;
import com.mindmotion.sflow.enums.ResponseCode;
import com.mindmotion.sflow.service.IFileService;
import com.mindmotion.sflow.utils.FileUtil;
import com.mindmotion.sflow.utils.PropertiesUtil;
import com.mindmotion.svd.convert.SVD2JSON;
import com.mindmotion.svd.enums.ResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/register")
@Slf4j
public class RegisterController {
    @Autowired
    private IFileService fileServe;

    @RequestMapping("uploadsvd.do")
    @ResponseBody
    public ServerResponse uploadSVD(MultipartFile file, HttpServletRequest request){
        String uploadDirectory = request.getSession().getServletContext().getRealPath(getWebAppUploadDirectory());
        String SVDGraphicJSONDirectory = request.getSession().getServletContext().getRealPath(getWebSVDGraphicJSONDirectory(FileUtil.GetSimpleFileNameNoExtend(file.getOriginalFilename())));

        String SVDFileName = fileServe.saveUploadFileToWebServer(file, uploadDirectory);
        if (!SVDFileName.isEmpty()){
            if (generalSVDGraphicJSON(SVDFileName, SVDGraphicJSONDirectory)){
                return ServerResponse.createBySuccess();
            }
        }
        return ServerResponse.createByError(ResponseCode.ERROR.getDescript());
    }

    private boolean generalSVDGraphicJSON(String svdFileName, String exportPath) {
        try {
            return SVD2JSON.translate(new String[]{svdFileName, exportPath}) == ResultEnum.SUCCESS.getCode();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getWebSVDGraphicJSONDirectory(String SVDFileName) {
        return String.format("%s\\%s", PropertiesUtil.getProperty("webapp.svdgraphic.dir"), SVDFileName);
    }

    private String getWebAppUploadDirectory() {
        return PropertiesUtil.getProperty("webapp.upload.dir");
    }
}
