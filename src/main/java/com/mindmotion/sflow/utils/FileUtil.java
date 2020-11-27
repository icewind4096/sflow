package com.mindmotion.sflow.utils;


import java.io.File;

public class FileUtil {
    /**
     * 目录是否存在
     */
    public static boolean DirectoryExists(String directory) {
        return new File(directory).exists();
    }

    /**
     * 文件是否存在
     */
    public static boolean FileExists(String path) {
        File file = new File(path);
        return file.exists() && file.isFile();
    }

    /**
     * 建立目录
     */
    public static boolean MakeDirectory(String directory) {
        if (!DirectoryExists(directory)){
            return new File(directory).mkdirs();
        }
        return true;
    }

    /**
     * 得到文件名含扩展名,不包含路径
     */
    public static String GetSimpleFileName(String fileName){
        if (fileName.indexOf("\\") >= 0 ){
            return fileName.substring(fileName.lastIndexOf("\\") + 1);
        }
        return fileName;
    }

    /**
     * 得到文件名不含扩展名,不包含路径
     */
    public static String GetSimpleFileNameNoExtend(String fileName){
        fileName = GetSimpleFileName(fileName);
        if (fileName.indexOf(".") >= 0){
            return fileName.substring(0, fileName.lastIndexOf("."));
        }
        return fileName;
    }
}
