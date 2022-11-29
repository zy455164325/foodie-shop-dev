package com.imooc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


//@Controller
//@ApiIgnore
@ApiIgnore //swagger2文档中不显示该controller
@RestController
public class HelloController {

    final private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {
        logger.info("info: hello");
        logger.warn("warn: hello");
        logger.error("error: hello");
        return "Hello World~";
    }

    @GetMapping("/setSession")
    public Object setSession(HttpServletRequest request) {
        HttpSession session= request.getSession();
        session.setAttribute("userInfo","new user");
        session.setMaxInactiveInterval(3600); //设置会话超时时间
        session.getAttribute("userInfo"); //获取session中的key值
//        session.removeAttribute("userInfo"); //移除设置的key信息
        return "ok";
    }




}
