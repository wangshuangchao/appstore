package com.mugua.up_down.utils;

public class FileNamaFormat {

    /**
     * 把文件名格式化为20180714-000.xxx
     * @param fileName 需要格式化的文件名
     * @return 格式化后的文件名
     */
    public static String formatFileName(String fileName){
        System.out.println("fileName:" + fileName);
        int i = fileName.indexOf("-");
        //文件名没有"-xxx",加上-000
        if (i<0){
            String fileNamePre = fileName.substring(0,fileName.lastIndexOf("."));
            System.out.println("fileNamePre:" + fileNamePre);
            String fileNameFix = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println("fileNameFix:" + fileNameFix);
            fileNamePre = fileNamePre + "-000";
            System.out.println("fileNamePre:" + fileNamePre);
            fileName = fileNamePre + "." + fileNameFix;
            System.out.println("fileName:" + fileName);
        }
        //文件名有"-x"或"-xx",补齐为-xxx
        if (i>0){
            String fileNamePre = fileName.substring(0,fileName.lastIndexOf("-"));
            System.out.println("fileNamePre:" + fileNamePre);
            String fileNameZhong = fileName.substring(fileName.lastIndexOf("-") + 1, fileName.lastIndexOf("."));
            System.out.println("fileNameZhong:" + fileNameZhong);
            String fileNameFix = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println("fileNameFix:" + fileNameFix);
            int n = fileNameZhong.length();
            if (n==1){
                fileNameZhong = "00" + fileNameZhong;
                System.out.println("fileNameZhong1:" + fileNameZhong);
            }
            if (n==2){
                fileNameZhong = "0" + fileNameZhong;
                System.out.println("fileNameZhong2:" + fileNameZhong);
            }
            fileName = fileNamePre + "-" + fileNameZhong + "." + fileNameFix;
            System.out.println("fileName:" + fileName);
        }
        return fileName;
    }

}
