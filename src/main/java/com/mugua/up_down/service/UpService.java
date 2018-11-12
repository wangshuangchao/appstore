package com.mugua.up_down.service;

import java.io.InputStream;


public interface UpService {

    /**
     * 上传安卓
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @return 上传结果
     */
    String saveApk(InputStream inputStream, String fileName);

    /**
     * 上传苹果
     * @param inputStream 文件输入流
     * @param fileName 文件名
     * @param identifier 苹果相关数据
     * @param version 苹果相关数据
     * @param kind 苹果相关数据
     * @param title 苹果相关数据
     * @return 上传结果
     */
    String saveIpa(InputStream inputStream, String fileName, String identifier, String version, String kind, String title);

}
