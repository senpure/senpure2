package com.senpure.generator.controller;

import com.senpure.base.util.StringUtil;
import freemarker.template.Template;
import javafx.util.StringConverter;

/**
 * Created by 罗中正 on 2017/6/12.
 */
public class TemplateConverter extends StringConverter<Template> {
    @Override
    public String toString(Template object) {
        String str=object.getSourceName();
       int index= StringUtil.indexOf(str, "/",1,true);
        if(index>0)
        {


            return str.substring(index+1);
        }
        return str;
    }

    @Override
    public Template fromString(String string) {
        return null;
    }
}
