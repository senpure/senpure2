package com.senpure.generator.message;

import freemarker.template.SimpleNumber;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.List;

/**
 * Created by 罗中正 on 2017/8/18.
 */
public class Var32Size implements TemplateMethodModelEx {
    @Override
    public Object exec(List list) throws TemplateModelException {
        SimpleNumber simpleNumber= (SimpleNumber) list.get(0);
        return computeVar32Size(simpleNumber.getAsNumber().intValue());
    }

    public static int computeVar32Size(int value) {
        if ((value & -128) == 0) {
            return 1;
        } else if ((value & -16384) == 0) {
            return 2;
        } else if ((value & -2097152) == 0) {
            return 3;
        } else {
            return (value & -268435456) == 0 ? 4 : 5;
        }
    }
}
