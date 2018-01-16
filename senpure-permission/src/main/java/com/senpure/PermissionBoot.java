package com.senpure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Created by 罗中正 on 2017/10/11.
 */
@SpringBootApplication
@EnableCaching
public class PermissionBoot {
    public static void main(String[] args) {
        SpringApplication.run(PermissionBoot.class, args);
    }
}
