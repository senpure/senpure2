package com.senpure.del;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 罗中正 on 2017/9/4.
 */
@Configuration
@ConditionalOnClass(javax.servlet.http.HttpServlet.class)
public class DelSelfServletConfiguration {
    @Bean
    public ServletRegistrationBean delSelfServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new DelSelfServlet(), "/senpure");
        servletRegistrationBean.setName("senpure");
        return servletRegistrationBean;
    }
}
