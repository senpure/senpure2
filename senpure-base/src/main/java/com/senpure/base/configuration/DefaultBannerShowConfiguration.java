package com.senpure.base.configuration;

import com.senpure.base.util.BannerShow;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by 罗中正 on 2017/12/20 0020.
 */
@Configuration
public class DefaultBannerShowConfiguration {



    @Value("${default.banner:true}")
    private String banner;

    @Bean
    @Order
    public DefaultBannerShow defaultBannerShow() {
        return new DefaultBannerShow();
    }

    class DefaultBannerShow implements ApplicationRunner {

        @Override
        public void run(ApplicationArguments applicationArguments) throws Exception {

            boolean show = true;
            if (banner != null && !Boolean.parseBoolean(banner)) {
                show = false;
            }
            if (show) {
                BannerShow.show();
                Thread.sleep(500);
            }

        }
    }
}
