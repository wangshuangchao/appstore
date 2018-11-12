package com.mugua.up_down.utils;

import java.io.File;
import java.io.FilenameFilter;

public class FilenameFilterImpl implements FilenameFilter {

    //后缀名
    private String suffixName;
    public FilenameFilterImpl() {
    }
    public FilenameFilterImpl(String suffixName) {
        this.suffixName = suffixName;
    }

    /**
     *  过滤掉文件名后缀不是 suffixName的文件
     * @param dir
     * @param name  文件名字
     * @return
     */
    @Override
    public boolean accept(File dir, String name) {
        if (name.endsWith(suffixName)){
            return true;
        }
        return false;

    }
}
