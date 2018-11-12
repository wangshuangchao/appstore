package com.mugua.up_down;

import com.mugua.up_down.listener.UpDownApplicationListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class UpDownApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UpDownApplication.class);
        springApplication.addListeners(new UpDownApplicationListener());

        springApplication.run(args);
    }


    /**
     * 文件上传配置
     *
     * @return
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //  单个数据大小
        int n = 1024*10*10*6;
        factory.setMaxFileSize(n + "KB"); // KB,MB
        /// 总上传数据大小
        int m = 1024*10*10*6;
        factory.setMaxRequestSize(m + "KB");
        return factory.createMultipartConfig();
    }
}
