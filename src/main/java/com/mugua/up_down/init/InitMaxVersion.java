package com.mugua.up_down.init;

import com.mugua.up_down.utils.FilenameFilterImpl;
import com.mugua.up_down.context.UpAndDownContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 工具类
 */
@Component
public class InitMaxVersion {
    @Autowired
    UpAndDownContext context;
    //安卓存储路径
    @Value("${mugua.appDownload.android.savePath}")
    private String androidSavePath;

    @Value("${mugua.appDownload.android.fileFilterType}")
    private String androidFileFilterType;

    //苹果存储路径
    @Value("${mugua.appDownload.ios.savePath}")
    private String iOSSavePath;

    @Value("${mugua.appDownload.ios.fileFilterType}")
    private String iosFileFilterType;


    public void initStart(){
        if (androidSavePath != null && iOSSavePath != null && context != null){
            String type = "android";
            initialLoadFileName(type, androidSavePath, context);
            type = "ios";
            initialLoadFileName(type, iOSSavePath, context);
        }

    }

    /**
     * 启动服务器时,给context初始化
     * @param typeName 被初始化的类型,安卓或苹果
     * @param savePath 文件的存储位置
     */
    public void initialLoadFileName(String typeName, String savePath, UpAndDownContext context){
        File path = new File(savePath);
        //路径不存在,无法初始化context
        if (!path.exists()){
            return;
        }
        if (path.isDirectory()){
            //获取文件中符合要求的子项
            String[] childrens = null;
            if ("android".equals(typeName)){
                childrens = path.list(new FilenameFilterImpl(androidFileFilterType));
            }
            if ("ios".equals(typeName)){
                childrens = path.list(new FilenameFilterImpl(iosFileFilterType));
            }

            //文件夹中没有任何子文件,无法初始化context
            if (childrens == null || childrens.length == 0){
                return;
            }

            //初始化
            String fileContextName = "0";
            for (String fileName: childrens) {
                int n = fileContextName.compareTo(fileName);
                if (n<0){
                    fileContextName = fileName;
                }
            }

            if ("android".equals(typeName)){
                context.setAndroidFileName(fileContextName);
            }
            if ("ios".equals(typeName)){
                context.setIosPlistName(fileContextName);
            }

        }
    }


}
