package com.mugua.up_down.utils;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.MessageFormat;

public class PlistTemplate {

    /**
     * 创建苹果安装包的对应的.plist文件
     * @param software_package
     * @param display_image
     * @param full_size_image
     * @param bundle_identifier
     * @param bundle_version
     * @param kind
     * @param title
     * @param fileName
     * @throws IOException
     */
    public static void createPlist(String software_package, String  display_image,
                                   String full_size_image, String bundle_identifier,
                                   String bundle_version, String kind,
                                   String title, String fileName) throws IOException {
        //得到VelocityEngine
        VelocityEngine ve = new VelocityEngine();
        //得到模板文件
        Template template = ve.getTemplate("/src/upDwonTemplates/iosTemplate.plist", "UTF-8");
        VelocityContext context = new VelocityContext();
        //传入参数
        context.put("software-package", software_package);
        context.put("display-image", display_image);
        context.put("full-size-image", full_size_image);
        context.put("bundle-identifier", bundle_identifier);
        context.put("bundle-version", bundle_version);
        context.put("kind", kind);
        context.put("title", title);

        //生成xml
        FileWriter fileWriter = getFileWriter(fileName);
        //调用merge方法传入context
        template.merge(context, fileWriter);
        fileWriter.flush();
        fileWriter.close();
    }

    private static FileWriter getFileWriter(String fileName) throws IOException {
        String fullPath = MessageFormat.format("{1}{0}{2}",
                File.separator,
                "",
                fileName);
        File outputFile = new File(fullPath);
        return new FileWriter(outputFile);
    }


}
