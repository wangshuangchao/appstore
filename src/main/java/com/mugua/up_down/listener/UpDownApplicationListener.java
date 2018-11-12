package com.mugua.up_down.listener;

import com.mugua.up_down.init.InitMaxVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

/**
 *
 * 监听ApplicationContextEvent事件的监听器
 * 在ApplicationContextEvent事件触发时初始化UpAndDownContext
 */
@Component
public class UpDownApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    @Autowired
    private InitMaxVersion upDownUtil;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        if (upDownUtil != null){
            upDownUtil.initStart();
        }
    }
}
