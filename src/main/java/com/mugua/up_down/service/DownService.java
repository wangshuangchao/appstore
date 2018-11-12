package com.mugua.up_down.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DownService {

    void toDown(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
