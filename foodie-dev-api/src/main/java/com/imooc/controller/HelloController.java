package com.imooc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


//@Controller
//@ApiIgnore
@ApiIgnore //swagger2文档中不显示该controller
@RestController
public class HelloController {

    //final private static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public Object hello() {

        return "Hello World~";

    }


}
