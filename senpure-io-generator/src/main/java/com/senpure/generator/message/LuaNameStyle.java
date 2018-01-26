package com.senpure.generator.message;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by 罗中正 on 2017/8/18.
 */
public class LuaNameStyle implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        SimpleScalar simpleScalar= (SimpleScalar) list.get(0);
        return MessageUtil.luaNameStyle(simpleScalar.getAsString());
    }
}
