package com.mindmotion.sflow.service.impl;

import com.mindmotion.sflow.service.IFileService;
import com.mindmotion.sflow.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
public class FileService implements IFileService {
    @Override
    public String saveUploadFileToWebServer(MultipartFile file, String directory) {
        //把原始文件名修改为随机文件名，防止与已在服务器上文件重名
        String uploadFileName = getUploadFileName(directory, getFileExtName(file.getOriginalFilename()));
        log.info("开始上传文件： 源文件名->{} 上传目录->{} 新文件名->{}", file.getOriginalFilename(), directory, uploadFileName);

        //检查WEB服务器存放目录是否存在, 不存在，就建立目录
        if (FileUtil.MakeDirectory(directory)){
            log.info("开始上传文件: 建立目录成功：{} ", directory);

            //把上传的临时文件，保存到web服务器的上传目录中
            log.info("开始上传文件: 保存文件到web服务器的上传目录：{}", directory);

            if (saveToFile(file, uploadFileName)){
                log.info("上传文件成功 文件保存在：{}", uploadFileName);
                return uploadFileName;
            } else {
                log.error("上传文件失败 ");
            }
        } else {
            log.error("开始上传文件: 建立目录失败：{} ", directory);
        }

        return "";
    }

    private boolean saveToFile(MultipartFile file, String uploadFileName) {
        File uploadFile = new File(uploadFileName);
        try {
            file.transferTo(uploadFile);
            log.info("开始上传文件 保存文件到web服务器的上传目录->成功");
            return true;
        } catch (IOException e) {
            log.info("开始上传文件 保存文件到web服务器的上传目录->失败", e);
        }
        return false;
    }

    private String getUploadFileName(String directory, String fileExtName) {
        return String.format("%s\\%s.%s", directory, UUID.randomUUID().toString(), fileExtName);
    }

    private String getFileExtName(String fileName) {
        String[] lists = fileName.split("\\.");
        if (lists.length > 0){
            return lists[lists.length - 1];
        }
        return "";
    }
}
