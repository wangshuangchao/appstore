package com.mugua.up_down.controller;

import com.mugua.up_down.service.UpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Slf4j
@Controller
@RequestMapping("/up")
@ResponseBody
public class UpController {
    @Autowired
    private UpService upService;

    @PostMapping("/android")
    public String upAndorid(@RequestParam("android") MultipartFile file) {
        //判断文件是否为空
        boolean isEmpty = file.isEmpty();
        if(isEmpty){
            log.info("啊啊啊文件为空");
            log.debug("文件为空啊");
            return "请选择文件";
        }
        InputStream inputStream = getInputStream(file);
        String fileName = getFileName(file);
        String result = upService.saveApk(inputStream, fileName);
        return result;
    }

    @PostMapping("/ios")
    public String upIos(@RequestParam("ios") MultipartFile file, String identifier, String version, String kind, String title) {
        //判断文件是否为空
        boolean isEmpty = file.isEmpty();
        if(isEmpty){
            return "请选择文件";
        }
        InputStream inputStream = getInputStream(file);
        String fileName = getFileName(file);
        String result = upService.saveIpa(inputStream, fileName, identifier,version,kind,title);
        return result;
    }

    /**
     * 文件输入流
      * @param multipartFile
     * @return
     */
    private InputStream getInputStream(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            log.error("控制层得到输入流异常(UpController.getInputStream)");
            log.error(e.getMessage());
        }
        return inputStream;
    }

    /**
     * 文件名
     * @param multipartFile
     * @return
     */
    private String getFileName(MultipartFile multipartFile){
        String fileName = multipartFile.getOriginalFilename();
        return fileName;
    }


}
