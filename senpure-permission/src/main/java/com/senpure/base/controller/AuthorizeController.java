package com.senpure.base.controller;

import com.senpure.base.spring.BaseController;
import com.senpure.base.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 罗中正 on 2018/1/13 0013.
 */
@Controller
@RequestMapping("/authorize")
public class AuthorizeController extends BaseController {

    @RequestMapping(value = "/forbidden")
    public void notPermission() {

        Assert.error("权限不足。");
    }

}
