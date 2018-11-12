package com.mugua.up_down.utils;

import lombok.Data;

/**
 * 返回json数据的类
 * @param <T>
 */

@Data
public class UpDwonResult<T> {

    private String  status;
    private String msg;
    private T data;

}
