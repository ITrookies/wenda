package com.example.demo.controller;


import com.example.demo.aspect.LogAspect;
import com.example.demo.model.User;
import com.example.demo.service.WendaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    @Autowired
    WendaService wendaService;

    @RequestMapping(path={"/","/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession){

        logger.info("VISIT HOME123");
        return "Hello World " + httpSession.getAttribute("msg")+wendaService.getMessage(2);

    }

    @RequestMapping(path={"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                          @PathVariable("groupId") String groupId,
                          @RequestParam(value = "type",defaultValue = "1") int type,
                          @RequestParam(value = "key",required = false) String key){
        return String.format("Profile Page of %s / %d , t:%d ,k:%s",groupId,userId,type,key);

    }

    @RequestMapping(path={"/vm"}, method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("value1","sdfsfsfse");
        List<String> colors = Arrays.asList(new String[]{"red","green","blue"});
        model.addAttribute("colors",colors);
        Map<String,String> map = new HashMap<String,String>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        model.addAttribute("map",map);
        model.addAttribute("user",new User("LEE"));
        return "home";
    }
    @RequestMapping(path = {"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request,
                          HttpSession httpsession,
                          @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("Cookie: "+sessionId);
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String name = headerNames.nextElement();
            sb.append(name + ":" + request.getHeader(name) +"<br>");
        }
        if(request.getCookies()!=null){
            for(Cookie cookie : request.getCookies()){
                sb.append("Cookie:" + cookie.getName() + "value:" + cookie.getValue());

            }
        }
        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");
        return sb.toString();
    }
    @RequestMapping(path={"/redirect/{code}"}, method = {RequestMethod.GET})
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession){
        httpSession.setAttribute("msg","jump from redirect");
        return "redirect:/";
    }
}
