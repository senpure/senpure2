package com.senpure.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 罗中正 on 2017/8/24.
 */
@SpringBootApplication
//@Controller
public class WebBoot {

    @RequestMapping("/home")
    @ResponseBody
    public String home() {
        return "Hello World!";
    }

    public static void main(String[] args) {

        SpringApplication.run(WebBoot.class, args);

    }
}
