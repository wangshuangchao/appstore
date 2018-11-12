package com.mugua.up_down.utils;

import com.mugua.up_down.context.UpAndDownContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SaveMaxnumVersion {

    @Autowired
    private  UpAndDownContext context;

    /**
     * 使得上下文变量中存储的是最高版本
     * @param fileName 新上传的文件
     * @param typeName
     */
    public void saveMaximumVersionFileName(String fileName, String typeName){
        //如果context的androidFileName为空,把当前上传的设置为最高版本
        if ("android".equals(typeName) && context.getAndroidFileName() == null){
            context.setAndroidFileName(fileName);
        }

        //如果context的iosFileName为空,把当前上传的设置为最高版本
        if ("ios".equals(typeName) && context.getIosPlistName() == null){
            context.setIosPlistName(fileName);
        }

        String fileNameContext = "";
        //上传安卓,取出安卓最高版本文件名
        if("android".equals(typeName)){
            fileNameContext = context.getAndroidFileName();
        }
        //上传苹果,取出苹果最高版本文件名
        if("ios".equals(typeName)){
            fileNameContext = context.getIosPlistName();
        }

        //比较当前上传和当前最高版本
        int isBig = fileName.compareTo(fileNameContext);
        //如果当前上传比当前最高版本的版本高,更新最高版本
        if(isBig > 0 && "android".equals(typeName)){
            context.setAndroidFileName(fileName);
        }
        if(isBig > 0 && "ios".equals(typeName)){
            context.setIosPlistName(fileName);
        }

    }


}
