package com.senpure.demo.configuration;

import com.senpure.base.annotation.ExtPermission;
import org.springframework.stereotype.Controller;

/**
 * Created by 罗中正 on 2018/1/15 0015.
 */
@Controller
@ExtPermission
public class SwaggerPermission {

    @ExtPermission(uri = "/v2/api-docs",method = "getDocumentation")
    public void apiDoc()
    {}
}
