package com.mugua.up_down.context;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UpAndDownContext {

    //安卓最高版本文件名
    private String androidFileName;
    //苹果最高版本文件名
    private  String iosFileName;
    //苹果最高版本plist文件名
    private String iosPlistName;


}
