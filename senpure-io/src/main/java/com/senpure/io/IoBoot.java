package com.senpure.io;

import com.senpure.base.util.BannerShow;
import org.fusesource.jansi.AnsiConsole;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by 罗中正 on 2017/5/26.
 */
@SpringBootApplication
public class IoBoot {
    public static void main(String[] args) {

        AnsiConsole.systemInstall();
        AnsiConsole.systemUninstall();
        SpringApplication application = new SpringApplication(IoBoot.class);
        application.setBannerMode(Banner.Mode.LOG);
        application.run(args);
        BannerShow.show();
    }
}
