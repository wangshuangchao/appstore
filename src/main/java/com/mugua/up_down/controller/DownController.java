package com.mugua.up_down.controller;

import com.mugua.up_down.service.DownService;
import com.mugua.up_down.context.UpAndDownContext;
import com.mugua.up_down.utils.PhoneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping()
public class DownController {
    @Autowired
    private PhoneUtil phoneUtil;

    @Autowired
    private DownService downService;

    //安卓下载路径前缀
    @Value("${mugua.appDownload.android.downloadUrlPrefix}")
    private String androidUrlPrefix;

    //苹果下载路径前缀
    @Value("${mugua.appDownload.ios.downloadUrlPrefix}")
    private String iosUrlPrefix;

    @Autowired
    private UpAndDownContext context;

    //果的APP store Url
    @Value("${mugua.appDownload.ios.appStoreUrl}")
    private String appStoreUrl;

    //是否使用 APP store Url
    @Value("${mugua.appDownload.ios.downloadFromAppStore}")
    private String downloadFromAppStore;

    @RequestMapping()
    public ModelAndView toDownIndex(HttpServletRequest request, HttpServletResponse response, Model model){
        ModelAndView view = new ModelAndView();
       try {
           String ua = request.getHeader("User-Agent");
           if(phoneUtil.checkAgentIsMobile(ua)){
               System.out.println("来自移动端");
               Map<String, String> urls = getUrls();
               model.addAttribute("androidUrl", urls.get("androidUrl"));
               model.addAttribute("iosUrl", urls.get("iosUrl"));

               view.setViewName("mobile");
               return view;
           }else {
               System.out.println("pc端");
               view.setViewName("pc");
               return view;
           }
       }catch (Exception e){
           return view;
       }

    }

    @RequestMapping("/down/android")
    public void toDownaAndroid(HttpServletRequest request, HttpServletResponse response){
        StringBuffer str = request.getRequestURL();
        System.out.println("androidUrl:" + str.toString());

        try {
            downService.toDown(request, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/down/ios")
    public void toDownIos(HttpServletRequest request, HttpServletResponse response) throws IOException {
        downService.toDown(request, response);
    }




    /**
     * 安卓和苹果的下载路径
     * @return
     */
    public Map<String, String> getUrls(){
        Map<String,String > urls = new HashMap<String ,String >();

        String androidUrl = androidUrlPrefix + context.getAndroidFileName();
        String iosUrl = "itms-services://?action=download-manifest&url=" + iosUrlPrefix + context.getIosPlistName();

        urls.put("androidUrl",androidUrl);
        if ("true".equals(downloadFromAppStore)){
            urls.put("iosUrl",appStoreUrl);
        }else if ("false".equals(downloadFromAppStore)){
            urls.put("iosUrl",iosUrl);
        }

        return urls;
    }
}
