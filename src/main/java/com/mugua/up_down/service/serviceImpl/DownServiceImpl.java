package com.mugua.up_down.service.serviceImpl;

import com.mugua.up_down.context.UpAndDownContext;
import com.mugua.up_down.service.DownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownServiceImpl implements DownService {
    @Autowired
    private UpAndDownContext context;
    //安卓存储路径
    @Value("${mugua.appDownload.android.savePath}")
    private String androidSavePath;

    //苹果存储路径
    @Value("${mugua.appDownload.ios.savePath}")
    private String iOSSavePath;

    @Override
    public void toDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        InputStream is = null;
        String fileName = "";
        String zipFilePath = "";
        if ("android".equals("android")){
            zipFilePath = androidSavePath;
            fileName =context.getAndroidFileName();
        }
//        if ("ios".equals(type)){
//            zipFilePath = iOSSavePath;
//            fileName =context.getIosPlistName();
//            System.out.println("fileName:" + fileName);
//        }

        File file = new File(zipFilePath + fileName);
        try {
            is = new FileInputStream(file);
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader(
                    "Content-disposition",
                    "attachment; filename="
                            + new String(fileName.getBytes("GBK"),
                            "ISO8859-1"));
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/html");
            response.getWriter().print("download failed");
        } finally {
            try {
                if (is != null)
                    is.close();
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
