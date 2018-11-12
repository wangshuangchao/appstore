package com.mugua.up_down.service.serviceImpl;

import com.mugua.up_down.context.UpAndDownContext;
import com.mugua.up_down.service.UpService;
import com.mugua.up_down.utils.FileNamaFormat;
import com.mugua.up_down.utils.PlistTemplate;
import com.mugua.up_down.utils.SaveMaxnumVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
@Slf4j
public class UpServiceImpl implements UpService {

    //安卓存储路径
    @Value("${mugua.appDownload.android.savePath}")
    private String androidSavePath;

    //苹果存储路径
    @Value("${mugua.appDownload.ios.savePath}")
    private String iOSSavePath;

    @Value("${mugua.appDownload.ios.plist.display-image}")
    private String display_image;

    @Value("${mugua.appDownload.ios.plist.full-size-image}")
    private String full_size_image;

    @Value("${mugua.appDownload.ios.downloadUrlPrefix}")
    private String downloadUrlPrefix;

    @Autowired
    private UpAndDownContext context;

    @Autowired
    private SaveMaxnumVersion saveMaxnumVersion;

    /**
     * 上传安卓
     * @return
     */
    public String saveApk(InputStream inputStream, String fileName){
        //格式化文件名为20180101-xxx.xxx
        fileName = FileNamaFormat.formatFileName(fileName);
        //保存文件到指定地址
        String result = save(inputStream,androidSavePath, fileName);
        //只有上传成功才更换最高版本
        if ("上传成功".equals(result)){
            //获取文件名
            String typeName = "android";
            //存储最高版本文件名到上下文中
            saveMaxnumVersion.saveMaximumVersionFileName(fileName, typeName);
        }

        return result;
    }

    /**
     * 上传苹果
     * @return
     */
    public String saveIpa(InputStream inputStream, String fileName, String identifier, String version, String kind, String title){
        //格式化文件名20180101-xxx.xxx
        fileName = FileNamaFormat.formatFileName(fileName);
        System.out.println("filename:" + fileName);
        //上传Ipa
        String result1 = save(inputStream, iOSSavePath, fileName);

        //Ipa上传成功,创建对应.plist文件
        if("上传成功".equals(result1)){
            //获取文件名
            String plistName = fileName.substring(0, fileName.lastIndexOf(".")) + ".plist";
            System.out.println("filename:" + fileName);
            String result2 = savePlist(identifier,version,kind,title,fileName,plistName);
            //创建.plist成功,更换最高版本
            if ("创建.plist成功".equals(result2)){
                String typeName = "ios";
                //存储最高版本文件名到上下文中
                saveMaxnumVersion.saveMaximumVersionFileName(plistName, typeName);
                return "上传成功";
            }else if ("创建.plist失败".equals(result2)){
                return "上传失败";
            }
        }

        return "上传失败";
    }

    /**
     * 把安装包存储到本地
     * @param inputStream 文件输入流
     * @param savePath 文件保存地址
     * @param filename 保存的文件名
     * @return 保存结果
     */
    private String save(InputStream inputStream, String savePath, String filename){
        System.out.println("fileName:" + filename);
        //获取项目路径
        String mdrPath = savePath;
        System.out.println("mdrPath:" + mdrPath);

        //路径不存在就创建
        File path = new File(savePath);
        if(!path.exists()){
            path.mkdir();
        }
        savePath = path.toString();
        System.out.println("savePath:" + savePath);
        // 保存文件
        OutputStream outputStream = null;
        try {
            File file = new File(savePath + "/" + filename);
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int len = -1;
            while ((len = inputStream.read(bytes)) != -1){
                outputStream.write(bytes, 0, len);
            }
            outputStream.close();
            inputStream.close();
            return "上传成功";
        } catch (IllegalStateException e) {
            log.error("保存文件失败(UpServiceImpl.save.IllegalStateException)");
            log.error(e.getMessage());
            return "上传失败";
        } catch (IOException e) {
            log.error("保存文件失败(UpServiceImpl.save.IOException)");
            log.error(e.getMessage());
            return "上传失败";
        } catch (Exception e){
            log.error("保存文件失败(UpServiceImpl.save.Exception)");
            log.error(e.getMessage());
            return "上传失败";
        }finally {
            try {
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                log.error("UpServiceImpl.save关闭流失败");
                log.error(e.getMessage());
            }

        }
    }

    /**
     * 存储苹果文件同名的Plist文件
     * @param identifier 苹果相关参数
     * @param version 苹果相关参数
     * @param kind 苹果相关参数
     * @param title 苹果相关参数
     * @param filename 存储的文件名
     * @return 存储结果
     */
    private String savePlist(String identifier, String version, String kind, String title, String filename, String plistname){
        System.out.println("savePlist.begin");
        //格式化文件名
        String software_package = iOSSavePath + filename;

        //路径不存在就创建
        File path = new File(iOSSavePath);
        if(!path.exists()){
            path.mkdir();
        }
        String savePath = path.toString();

        //创建文件,后缀为plist
        plistname = savePath + "\\" +plistname;

        try {
            PlistTemplate.createPlist(software_package, display_image, full_size_image, identifier, version, kind, title, plistname);
            return "创建.plist成功";
        } catch (IOException e) {
            log.error("创建.plist失败(UpServiceImpl.IOException)");
            log.error(e.getMessage());
            return "创建.plist失败";
        } catch (Exception e){
            log.error("创建.plist失败(UpServiceImpl.Exception)");
            log.error(e.getMessage());
            return "创建.plist失败";
        }

    }



}
